package cl.servimet.regional.maritimo.service;

import cl.servimet.regional.maritimo.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RedisStorageService {

    private static final Logger logger = LoggerFactory.getLogger(RedisStorageService.class);

    private final StringRedisTemplate redisTemplate;
    private final ExcelProcessorService excelProcessorService;

    public RedisStorageService(StringRedisTemplate redisTemplate, ExcelProcessorService excelProcessorService) {
        this.redisTemplate = redisTemplate;
        this.excelProcessorService = excelProcessorService;
    }

    public void saveToRedis(String key, Object value) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, json);
    }

//    public String getFromRedis(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }

public <T> void mergeAndSaveToRedis(String key, List<T> newData, Class<T> clazz) throws Exception {
    List<T> existingData = getListFromRedis(key, clazz); // Leer datos existentes
    for (T newItem : newData) {
        if (!existingData.contains(newItem)) { // Evitar duplicados
            existingData.add(newItem);
        }
    }
    saveToRedis(key, existingData); // Guardar datos combinados
}

    public <T> List<T> getListFromRedis(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }
        } catch (Exception e) {
            logger.error("Error al rescatar lista", e);
        }
        return new ArrayList<>(); // Retorna lista vacía si no hay datos
    }

    public Map<String, Object> generateOrderedJson() {
        Map<String, Object> compiledJson = new LinkedHashMap<>(); // Cambiar a LinkedHashMap
        compiledJson.put("titulo", "Pronóstico Meteorológico Marítimo para Bahías");

        List<RegionalMaritimoDTO.Identificadores> identificadores = getListFromRedis("identificador", RegionalMaritimoDTO.Identificadores.class);
        List<RegionalMaritimoDTO.MeteoAM> meteoAMList = getListFromRedis("meteoAM", RegionalMaritimoDTO.MeteoAM.class);
        List<RegionalMaritimoDTO.MeteoPM> meteoPMList = getListFromRedis("meteoPM", RegionalMaritimoDTO.MeteoPM.class);
        List<RegionalMaritimoDTO.ProximosPronosticos> proximosList = getListFromRedis("proximosPronosticos", RegionalMaritimoDTO.ProximosPronosticos.class);

        // Agrupar centros meteorológicos
        List<Map<String, Object>> centros = new ArrayList<>();
        for (RegionalMaritimoDTO.Identificadores identificador : identificadores) {
            int idCentro = identificador.getIdCentro();
            int idBahia = identificador.getIdBahia();

            // Buscar o crear el centro
            Map<String, Object> centro = centros.stream()
                    .filter(c -> c.get("id-centro").equals(idCentro))
                    .findFirst()
                    .orElseGet(() -> {
                        Map<String, Object> newCentro = new LinkedHashMap<>(); // Mantener el orden
                        newCentro.put("nombre", identificador.getNombreCentro());
                        newCentro.put("id-centro", idCentro);
                        newCentro.put("bahias", new ArrayList<>());
                        centros.add(newCentro);
                        return newCentro;
                    });

            // Agregar bahías al centro
            List<Map<String, Object>> bahias = (List<Map<String, Object>>) centro.get("bahias");
            Map<String, Object> bahia = new LinkedHashMap<>();
            bahia.put("nombre", identificador.getNombreBahia());
            bahia.put("id-bahia", idBahia);

            // Agregar pronósticos a la bahía
            List<Object> pronosticos = new ArrayList<>();
            meteoAMList.stream()
                    .filter(am -> am.getIdBahia() == idBahia)
                    .forEach(am -> pronosticos.add(createPronosticoData(am)));
            meteoPMList.stream()
                    .filter(pm -> pm.getIdBahia() == idBahia)
                    .forEach(pm -> pronosticos.add(createPronosticoData(pm)));
            proximosList.stream()
                    .filter(pr -> pr.getIdBahia() == idBahia)
                    .forEach(pr -> pronosticos.add(createPronosticoData(pr)));

            bahia.put("pronostico_actual", pronosticos);
            bahias.add(bahia);
        }

        compiledJson.put("centros_meteorologicos", centros);
        return compiledJson;
    }

    private Map<String, Object> createPronosticoData(Object pronostico) {
        Map<String, Object> data = new LinkedHashMap<>();

        if (pronostico instanceof RegionalMaritimoDTO.MeteoAM am) {
            data.put("id-pronostico", am.getIdPronostico());
            Map<String, Object> periodoValidez = new LinkedHashMap<>();
            periodoValidez.put("dia", am.getDia());
            periodoValidez.put("numeral", am.getNumeral());
            periodoValidez.put("mes", am.getMes());
            periodoValidez.put("inicio", am.getInicio());
            periodoValidez.put("fin", am.getFin());
            periodoValidez.put("zona_horaria", am.getZonaHoraria());
            periodoValidez.put("temperatura_minima", am.getTemperaturaMinima());
            periodoValidez.put("temperatura_maxima", am.getTemperaturaMaxima());
            periodoValidez.put("indice_radiacion_uv", am.getIndiceRadiacionUV());
            data.put("periodo_validez", periodoValidez);

            data.put("situacion_sinoptica", am.getSituacionSinoptica());

            Map<String, Object> pronosticoData = new LinkedHashMap<>();
            pronosticoData.put("cobertura_nubosa", am.getCoberturaNubosa());
            pronosticoData.put("visibilidad", am.getVisibilidad());
            pronosticoData.put("fenomeno_atmosferico", am.getFenomenoAtmosferico());

            Map<String, Object> viento = new LinkedHashMap<>();
            viento.put("direccion", am.getDireccionViento1());
            viento.put("velocidad", am.getVelocidad1());
            viento.put("direccion2", am.getDireccionViento2());
            viento.put("velocidad2", am.getVelocidad2());
            pronosticoData.put("viento", viento);

            Map<String, Object> estadoMar = new LinkedHashMap<>();
            estadoMar.put("altura_olas", am.getAlturaOlas());
            estadoMar.put("descripcion", am.getDescripcion());
            estadoMar.put("mar_bahia", am.getEstadoMarBahia());
            pronosticoData.put("estado_mar", estadoMar);

            data.put("pronostico", pronosticoData);
        } else if (pronostico instanceof RegionalMaritimoDTO.MeteoPM pm) {
            data.put("id-pronostico", pm.getIdPronostico());
            Map<String, Object> periodoValidez = new LinkedHashMap<>();
            periodoValidez.put("inicio", pm.getInicio());
            periodoValidez.put("fin", pm.getFin());
            periodoValidez.put("zona_horaria", pm.getZonaHoraria());
            data.put("periodo_validez", periodoValidez);

            data.put("situacion_sinoptica", pm.getSituacionSinoptica());

            Map<String, Object> pronosticoData = new LinkedHashMap<>();
            pronosticoData.put("cobertura_nubosa", pm.getCoberturaNubosa());
            pronosticoData.put("visibilidad", pm.getVisibilidad());
            pronosticoData.put("fenomeno_atmosferico", pm.getFenomenoAtmosferico());

            Map<String, Object> viento = new LinkedHashMap<>();
            viento.put("direccion", pm.getDireccionViento1());
            viento.put("velocidad", pm.getVelocidad1());
            viento.put("direccion2", pm.getDireccionViento2());
            viento.put("velocidad2", pm.getVelocidad2());
            pronosticoData.put("viento", viento);

            Map<String, Object> estadoMar = new LinkedHashMap<>();
            estadoMar.put("altura_olas", pm.getAlturaOlas());
            estadoMar.put("descripcion", pm.getDescripcion());
            estadoMar.put("mar_bahia", pm.getEstadoMarBahia());
            pronosticoData.put("estado_mar", estadoMar);

            data.put("pronostico", pronosticoData);
        } else if (pronostico instanceof RegionalMaritimoDTO.ProximosPronosticos proximos) {
            data.put("id", proximos.getIdPronostico());
            data.put("dia", proximos.getDia());
            data.put("numeral", proximos.getNumeral());
            data.put("mes", proximos.getMes());
            data.put("situacion_sinoptica", proximos.getSituacionSinoptica());

            Map<String, Object> pronosticoData = new LinkedHashMap<>();
            pronosticoData.put("cobertura_nubosa", proximos.getCoberturaNubosa());
            pronosticoData.put("visibilidad", proximos.getVisibilidad());
            pronosticoData.put("fenomeno_atmosferico", proximos.getFenomenoAtmosferico());

            Map<String, Object> viento = new LinkedHashMap<>();
            viento.put("direccion", proximos.getDireccionViento1());
            viento.put("velocidad", proximos.getVelocidad1());
            viento.put("direccion2", proximos.getDireccionViento2());
            viento.put("velocidad2", proximos.getVelocidad2());
            pronosticoData.put("viento", viento);

            Map<String, Object> estadoMar = new LinkedHashMap<>();
            estadoMar.put("altura_olas", proximos.getAlturaOlas());
            estadoMar.put("descripcion", proximos.getDescripcion());
            estadoMar.put("mar_bahia", proximos.getEstadoMarBahia());
            pronosticoData.put("estado_mar", estadoMar);

            pronosticoData.put("temperatura_minima", proximos.getTemperaturaMinima());
            pronosticoData.put("temperatura_maxima", proximos.getTemperaturaMaxima());
            data.put("pronostico", pronosticoData);
        } else {
            throw new IllegalArgumentException("Tipo de pronóstico no reconocido: " + pronostico.getClass().getName());
        }

        return data;
    }

    public void saveCompiledJson(Map<String, Object> compiledJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(compiledJson); // Convertir el JSON a String
        redisTemplate.opsForValue().set("compiledJson", json); // Guardar en Redis bajo una clave específica
    }

    public Map<String, Object> getCompiledJsonFromRedis() throws Exception {
        String json = redisTemplate.opsForValue().get("compiledJson");
        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Map.class); // Convertir de String a Map
        }
        return new HashMap<>(); // Retornar un mapa vacío si no existe
    }


    /////Inicio canales australes y canales puerto montt

    private void saveJsonToRedis(String key, Map<String, Object> jsonToSave) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(jsonToSave);
            redisTemplate.opsForValue().set(key, jsonString);
        } catch (Exception e) {
            logger.error("Error al guardar el JSON", e);
        }
    }

    public void saveCanalesAustralesToRedis(List<CanalesAustralesDTO> canalesAustralesList) {
        Map<String, Object> jsonToSave = new LinkedHashMap<>();
        jsonToSave.put("informes", new ArrayList<>());

        // Rellenar títulos vacíos con el último válido
        String lastTitle = null;
        for (CanalesAustralesDTO dto : canalesAustralesList) {
            if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
                dto.setTitulo(lastTitle);
            } else {
                lastTitle = dto.getTitulo();
            }
        }

        // Agrupar por título
        Map<String, List<CanalesAustralesDTO>> groupedByTitle = canalesAustralesList.stream()
                .filter(dto -> dto.getTitulo() != null && !dto.getTitulo().trim().isEmpty())
                .collect(Collectors.groupingBy(CanalesAustralesDTO::getTitulo, LinkedHashMap::new, Collectors.toList()));

        groupedByTitle.forEach((titulo, list) -> {
            Map<String, Object> informe = new LinkedHashMap<>();
            informe.put("titulo", titulo);

            // Crear sección de validez
            Map<String, Object> validez = new LinkedHashMap<>();
            validez.put("inicio", list.get(0).getInicio() != null ? list.get(0).getInicio() : "No especificado");
            validez.put("fin", list.get(0).getFin() != null ? list.get(0).getFin() : "No especificado");
            informe.put("validez", validez);

            // Crear lista de pronósticos
            List<Map<String, Object>> pronosticos = list.stream()
                    .map(dto -> {
                        Map<String, Object> pronostico = new LinkedHashMap<>();

                        if (dto.getId() != null) pronostico.put("id-zona", dto.getId());
                        if (dto.getZona() != null && !dto.getZona().trim().isEmpty()) pronostico.put("zona", dto.getZona());
                        if (dto.getNubosidad() != null && !dto.getNubosidad().trim().isEmpty()) pronostico.put("nubosidad", dto.getNubosidad());
                        if (dto.getVisibilidad() != null && !dto.getVisibilidad().trim().isEmpty()) pronostico.put("visibilidad", dto.getVisibilidad());
                        if (dto.getViento() != null && !dto.getViento().trim().isEmpty()) pronostico.put("viento", dto.getViento());
                        if (dto.getEstado_mar() != null && !dto.getEstado_mar().trim().isEmpty()) pronostico.put("estado_mar", dto.getEstado_mar());

                        return pronostico.isEmpty() ? null : pronostico;
                    })
                    .filter(Objects::nonNull) // Excluir pronósticos completamente vacíos
                    .collect(Collectors.toList());

            // Agregar pronósticos si existen
            if (!pronosticos.isEmpty()) {
                informe.put("pronosticos", pronosticos);
            }

            ((List<Object>) jsonToSave.get("informes")).add(informe);
        });

        // Guardar en Redis
        saveJsonToRedis("canalesAustralesJson", jsonToSave);
    }


    public void saveCanalesMonttToRedis(List<CanalesMonttDTO> canalesMonttList) {
        Map<String, Object> jsonToSave = new LinkedHashMap<>();
        jsonToSave.put("informes", new ArrayList<>());

        // Rellenar títulos vacíos con el último válido
        String lastTitle = null;
        for (CanalesMonttDTO dto : canalesMonttList) {
            if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
                dto.setTitulo(lastTitle);
            } else {
                lastTitle = dto.getTitulo();
            }
        }

        // Agrupar por título
        Map<String, List<CanalesMonttDTO>> groupedByTitle = canalesMonttList.stream()
                .filter(dto -> dto.getTitulo() != null && !dto.getTitulo().trim().isEmpty())
                .collect(Collectors.groupingBy(CanalesMonttDTO::getTitulo, LinkedHashMap::new, Collectors.toList()));

        groupedByTitle.forEach((titulo, list) -> {
            Map<String, Object> informe = new LinkedHashMap<>();
            informe.put("titulo", titulo);

            // Crear sección de validez
            Map<String, Object> validez = new LinkedHashMap<>();
            validez.put("inicio", list.get(0).getInicio() != null ? list.get(0).getInicio() : "No especificado");
            validez.put("fin", list.get(0).getFin() != null ? list.get(0).getFin() : "No especificado");
            informe.put("validez", validez);

            // Agregar situación sinóptica si existe
            if (list.get(0).getSituacion_sinoptica() != null && !list.get(0).getSituacion_sinoptica().trim().isEmpty()) {
                informe.put("situacion_sinoptica", list.get(0).getSituacion_sinoptica());
            }

            // Crear lista de pronósticos
            List<Map<String, Object>> pronosticos = list.stream()
                    .map(dto -> {
                        Map<String, Object> pronostico = new LinkedHashMap<>();
                        if (dto.getId() != null) pronostico.put("id-zona", dto.getId());
                        if (dto.getZona() != null && !dto.getZona().trim().isEmpty()) pronostico.put("zona", dto.getZona());
                        if (dto.getNubosidad() != null && !dto.getNubosidad().trim().isEmpty()) pronostico.put("nubosidad", dto.getNubosidad());
                        if (dto.getVisibilidad() != null && !dto.getVisibilidad().trim().isEmpty()) pronostico.put("visibilidad", dto.getVisibilidad());
                        if (dto.getViento() != null && !dto.getViento().trim().isEmpty()) pronostico.put("viento", dto.getViento());
                        if (dto.getEstado_mar() != null && !dto.getEstado_mar().trim().isEmpty()) pronostico.put("estado_mar", dto.getEstado_mar());

                        return pronostico.isEmpty() ? null : pronostico;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Agregar pronósticos si existen
            if (!pronosticos.isEmpty()) {
                informe.put("pronosticos", pronosticos);
            }

            // Crear sección de apreciación
            Map<String, Object> apreciacion = new LinkedHashMap<>();

            // Buscar la fila que contiene los datos de apreciacion
            CanalesMonttDTO apreciacionDTO = list.stream()
                    .filter(dto -> dto.getValida_desde() != null && !dto.getValida_desde().trim().isEmpty())
                    .findFirst()
                    .orElse(null);

            // Verificar si se encontró un DTO con datos de apreciación
            if (apreciacionDTO != null) {
                apreciacion.put("valida_desde", apreciacionDTO.getValida_desde());
                apreciacion.put("valida_hasta", apreciacionDTO.getValida_hasta());
                apreciacion.put("zona_horaria", apreciacionDTO.getZona_horaria());
                apreciacion.put("situacion", apreciacionDTO.getSituacion());
                apreciacion.put("cobertura_nubosa", apreciacionDTO.getCobertura_nubosa());
                apreciacion.put("visibilidad_apreciacion", apreciacionDTO.getVisibilidad_apreciacion());
                apreciacion.put("viento_apreciacion", apreciacionDTO.getViento_apreciacion());

                // Agregar la sección de apreciación al informe
                informe.put("apreciacion", apreciacion);
                System.out.println("Sección apreciación generada: " + apreciacion);
            } else {
                System.out.println("No se encontraron datos de apreciación.");
            }

            ((List<Object>) jsonToSave.get("informes")).add(informe);
        });

        // Guardar en Redis
        saveJsonToRedis("canalesMonttJson", jsonToSave);
    }

    public Map<String, Object> generateCanalesAguasJson() {
        Map<String, Object> jsonOutput = new LinkedHashMap<>();
        jsonOutput.put("informes", new ArrayList<>());

        try {
            ObjectMapper mapper = new ObjectMapper();

            // Combine Australes
            String australesJson = redisTemplate.opsForValue().get("canalesAustralesJson");
            if (australesJson != null) {
                Map<String, Object> australesData = mapper.readValue(australesJson, LinkedHashMap.class);
                ((List<Object>) jsonOutput.get("informes")).addAll((List<Object>) australesData.get("informes"));
            }

            // Combine Montt
            String monttJson = redisTemplate.opsForValue().get("canalesMonttJson");
            if (monttJson != null) {
                Map<String, Object> monttData = mapper.readValue(monttJson, LinkedHashMap.class);
                ((List<Object>) jsonOutput.get("informes")).addAll((List<Object>) monttData.get("informes"));
            }
        } catch (Exception e) {
            logger.error("Error al generar el Informe", e);
        }
        // Guardar en Redis
        saveJsonToRedis("canales_aguas", jsonOutput);
        return jsonOutput;
    }
    ////final canales australes y canales puerto montt

    ////inicio zona nueve

    public void saveZonaNueveToRedis(List<ZonaNueveDTO> zonaNueveList) {
        Map<String, Object> jsonToSave = new LinkedHashMap<>();
        jsonToSave.put("titulo", "Reporte Meteorológico Antártico Zona IX");

        // Crear la sección de validez
        Map<String, String> validez = new LinkedHashMap<>();
        validez.put("inicio", zonaNueveList.get(0).getInicio());
        validez.put("fin", zonaNueveList.get(0).getFin());
        jsonToSave.put("validez", validez);

        // Crear la sección de situación sinóptica (IDs 9-11)
        List<Map<String, Object>> situacionSinoptica = zonaNueveList.stream()
                .filter(dto -> dto.getId() != null && dto.getId() >= 9 && dto.getId() <= 11) // IDs entre 9 y 11
                .map(dto -> {
                    Map<String, Object> sector = new LinkedHashMap<>();
                    sector.put("id-sector", dto.getId());
                    sector.put("sector", dto.getCampo() != null ? dto.getCampo() : "");
                    sector.put("descripcion", dto.getDescripcion() != null ? dto.getDescripcion() : "");
                    return sector;
                })
                .collect(Collectors.toList());
        jsonToSave.put("situacion_sinoptica", situacionSinoptica);

        // Crear la sección de pronóstico (IDs 12-20)
        List<Map<String, Object>> pronosticos = zonaNueveList.stream()
                .filter(dto -> dto.getId() != null && dto.getId() >= 12 && dto.getId() <= 20) // IDs entre 12 y 20
                .map(dto -> {
                    Map<String, Object> pronostico = new LinkedHashMap<>();
                    pronostico.put("id-zona", dto.getId());
                    pronostico.put("zona", dto.getCampo() != null ? dto.getCampo() : "");
                    pronostico.put("cobertura_nubosa", dto.getCobertura_nubosa() != null ? dto.getCobertura_nubosa() : "");
                    pronostico.put("visibilidad", dto.getVisibilidad() != null ? dto.getVisibilidad() : "");
                    pronostico.put("viento", dto.getViento() != null ? dto.getViento() : "");
                    pronostico.put("estado_mar", dto.getEstado_mar() != null ? dto.getEstado_mar() : "");
                    return pronostico;
                })
                .collect(Collectors.toList());
        jsonToSave.put("pronostico", pronosticos);

        // Guardar el JSON en Redis
        saveJsonToRedis("zonaNueveJson", jsonToSave);
    }
    public Map<String, Object> getZonaNueveJsonFromRedis() {
        try {
            String json = redisTemplate.opsForValue().get("zonaNueveJson");
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, Map.class);
            }
        } catch (Exception e) {
            logger.error("Error al recuperar el JSON de Zona Nueve desde Redis.", e);
        }
        return new HashMap<>(); // Retornar un mapa vacío si no existe
    }
    ////final zona nueve

    /// inicio zona diez

    public void processAndSaveZonaDiezToRedis(InputStream inputStream) throws Exception {
        // Procesar el Excel
        ZonaDiezDTO zonaDiezDTO = excelProcessorService.processZonaDiez(inputStream);

        // Crear el JSON principal
        Map<String, Object> jsonData = new LinkedHashMap<>();
        jsonData.put("id", "zona-diez");
        jsonData.put("nombre", "BOLETÍN DE TIEMPO Y MAR ZONA DIEZ");

        // Validez
        Map<String, String> validez = new LinkedHashMap<>();
        validez.put("inicio", zonaDiezDTO.getValidez().getInicio());
        validez.put("fin", zonaDiezDTO.getValidez().getFin());
        jsonData.put("validez", validez);

        // Crear las partes
        List<Map<String, Object>> partes = new ArrayList<>();

        // PARTE I
        Map<String, Object> parteI = new LinkedHashMap<>();
        parteI.put("id", "parte-1");
        parteI.put("titulo", "PARTE I: AVISO DE TEMPORAL");

        List<Map<String, String>> contenidoParteI = zonaDiezDTO.getParteUno().stream()
                .filter(parteUno -> parteUno.getCondicion() != null && !parteUno.getCondicion().isEmpty()) // Validar sectores con condición
                .map(parteUno -> {
                    Map<String, String> contenido = new LinkedHashMap<>();
                    contenido.put("id", parteUno.getId());
                    contenido.put("sector", parteUno.getSector());
                    contenido.put("condicion", parteUno.getCondicion());
                    return contenido;
                })
                .collect(Collectors.toList());

        if (contenidoParteI.isEmpty()) {
            // Si no hay sectores con condición, agregar "SIN AVISOS"
            parteI.put("contenido", List.of("SIN AVISOS"));
        } else {
            parteI.put("contenido", contenidoParteI);
        }

        partes.add(parteI);


        // PARTE II
        Map<String, Object> parteII = new LinkedHashMap<>();
        parteII.put("id", "parte-2");
        parteII.put("titulo", "PARTE II: SITUACION SINOPTICA");

        ZonaDiezDTO.ParteDos parteDos = zonaDiezDTO.getParteDos().get(0); // Asumiendo que hay solo un registro para la Parte II
        if (parteDos != null && parteDos.getContenido() != null && !parteDos.getContenido().isEmpty()) {
            parteII.put("hora_analisis", parteDos.getHora_analisis());
            parteII.put("contenido", Arrays.asList(parteDos.getContenido().split(",\n"))); // Dividir contenido por comas y saltos de línea
        } else {
            parteII.put("hora_analisis", "");
            parteII.put("contenido", List.of("SIN AVISOS"));
        }

        partes.add(parteII);


        // PARTE III
        Map<String, Object> parteIII = new LinkedHashMap<>();
        parteIII.put("id", "parte-3");
        parteIII.put("titulo", "PARTE III: PRONOSTICO");

        List<Map<String, String>> contenidoParteIII = zonaDiezDTO.getParteTres().stream()
                .filter(parteTres -> parteTres.getCondicion() != null && !parteTres.getCondicion().isEmpty()) // Filtrar sectores con condición válida
                .map(parteTres -> {
                    Map<String, String> contenido = new LinkedHashMap<>();
                    contenido.put("id", parteTres.getId());
                    contenido.put("sector", parteTres.getSector());
                    contenido.put("condicion", parteTres.getCondicion());
                    return contenido;
                }).collect(Collectors.toList());

        parteIII.put("contenido", contenidoParteIII);
        partes.add(parteIII);

        // Agregar partes al JSON principal
        jsonData.put("partes", partes);

        // Guardar en Redis
        ObjectMapper mapper = new ObjectMapper();
        redisTemplate.opsForValue().set("zona-diez", mapper.writeValueAsString(jsonData));
    }

    public Map<String, Object> getZonaDiezFromRedis() {
        try {
            String json = redisTemplate.opsForValue().get("zona-diez");
            if (json != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, Map.class);
            }
        } catch (Exception e) {
            logger.error("Error al recuperar el JSON de Zona Diez desde Redis.", e);
        }
        return new HashMap<>();
    }

    /// final zona diez

    /// inicio zona 1 a 6 (cargado por valparaiso)
    public void saveZonaUnoASeisToRedis(List<ZonaUnoSeisDTO> zonaUnoSeisList) {
        try {
            if (zonaUnoSeisList == null || zonaUnoSeisList.isEmpty()) {
                throw new RuntimeException("La lista de zonas Uno a Seis está vacía o es nula.");
            }

            // Crear el mapa principal
            Map<String, Object> dataToSave = new LinkedHashMap<>();
            dataToSave.put("titulo", "Informe Meteorológico de Tiempo Marítimo desde Arica a Lat 60 GDS Sur");

            // Crear el mapa de validez asegurando el orden
            Map<String, String> validez = new LinkedHashMap<>();
            validez.put("inicio", zonaUnoSeisList.get(0).getInicio() != null ? zonaUnoSeisList.get(0).getInicio() : "No definido");
            validez.put("fin", zonaUnoSeisList.get(0).getFin() != null ? zonaUnoSeisList.get(0).getFin() : "No definido");
            dataToSave.put("validez", validez);

            // Crear la lista de pronósticos
            List<Map<String, Object>> pronosticos = new ArrayList<>();
            for (ZonaUnoSeisDTO dto : zonaUnoSeisList) {
                pronosticos.add(createCorrectedPronosticoMap(dto));
            }
            dataToSave.put("pronosticos", pronosticos);

            // Convertir a JSON y guardar en Redis
            String json = new ObjectMapper().writeValueAsString(dataToSave);
            redisTemplate.opsForValue().set("pronosticoUnoASeis", json);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar Zonas Uno a Seis en Redis", e);
        }
    }

    private Map<String, Object> createCorrectedPronosticoMap(ZonaUnoSeisDTO dto) {
        Map<String, Object> pronostico = new LinkedHashMap<>();
        pronostico.put("id-zona", dto.getId());
        pronostico.put("zona", getZonaUnoSeisName(dto.getId()));
        pronostico.put("situacion_sinoptica", Optional.ofNullable(dto.getSituacion_sinoptica()).orElse("Sin información"));
        pronostico.put("nubosidad", Optional.ofNullable(dto.getNubosidad()).orElse("Sin información"));
        pronostico.put("visibilidad", Optional.ofNullable(dto.getVisibilidad()).orElse("Sin información"));
        pronostico.put("viento", Optional.ofNullable(dto.getViento()).orElse("Sin información"));
        pronostico.put("mar_oceanico", Optional.ofNullable(dto.getMar_oceanico()).orElse("Sin información"));
        pronostico.put("mar_bahia", Optional.ofNullable(dto.getMar_bahia()).orElse("Sin información"));
        return pronostico;
    }

    private String getZonaUnoSeisName(Long id) {
        Map<Long, String> zonaUnoSeisNames = Map.of(
                1L, "Zona Uno (Arica a Coquimbo)",
                2L, "Zona Dos (Coquimbo a Valparaíso)",
                3L, "Zona Tres (Valparaíso a Constitución)",
                4L, "Zona Cuatro (Constitución a I.Mocha)",
                5L, "Zona Cinco (I.Mocha a P.Montt)",
                6L, "Zona Seis (P.Montt a F.San Pedro)"
        );
        return zonaUnoSeisNames.getOrDefault(id, "Zona no especificada");
    }

    public Map<String, Object> getZonaUnoASeisFromRedis() {
        try {
            String json = redisTemplate.opsForValue().get("pronosticoUnoASeis");
            if (json == null) {
                throw new RuntimeException("No se encontraron datos para Zonas Uno a Seis en Redis");
            }
            return new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener Zonas Uno a Seis desde Redis", e);
        }
    }
    /// final zona 1 a 6 (cargado por valparaiso)

    /// inicio 7 y 8 (cargado por punta arenas)
    public void saveZonaSieteYOchoToRedis(List<ZonaSieteOchoDTO> zonaSieteYOchoList) {
        try {
            if (zonaSieteYOchoList == null || zonaSieteYOchoList.isEmpty()) {
                throw new RuntimeException("La lista de zonas Siete y Ocho está vacía o es nula.");
            }

            // Validar que todos los IDs son válidos
            for (ZonaSieteOchoDTO dto : zonaSieteYOchoList) {
                if (dto.getId() == null) {
                    throw new RuntimeException("El ID de la zona es nulo para el DTO: " + dto);
                }
            }

            // Crear el mapa principal
            Map<String, Object> dataToSave = new LinkedHashMap<>();
            dataToSave.put("titulo", "Informe Meteorológico de Tiempo Marítimo Zonas Siete y Ocho");

            // Crear el mapa de validez asegurando el orden
            Map<String, String> validez = new LinkedHashMap<>();
            validez.put("inicio", zonaSieteYOchoList.get(0).getInicio() != null ? zonaSieteYOchoList.get(0).getInicio() : "No definido");
            validez.put("fin", zonaSieteYOchoList.get(0).getFin() != null ? zonaSieteYOchoList.get(0).getFin() : "No definido");
            dataToSave.put("validez", validez);

            // Crear la lista de pronósticos
            List<Map<String, Object>> pronosticos = new ArrayList<>();
            for (ZonaSieteOchoDTO dto : zonaSieteYOchoList) {
                pronosticos.add(createCorrectedPronosticoMap(dto));
            }
            dataToSave.put("pronosticos", pronosticos);

            // Convertir a JSON y guardar en Redis
            String json = new ObjectMapper().writeValueAsString(dataToSave);
            redisTemplate.opsForValue().set("pronosticoSieteYOcho", json);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar Zonas Siete y Ocho en Redis", e);
        }
    }

    private Map<String, Object> createCorrectedPronosticoMap(ZonaSieteOchoDTO dto) {
        Long rawId = dto.getId();
        String correctedId;

        // Normalizar el ID
        if (rawId == 7L) {
            correctedId = "7";
        } else if (rawId == 81L) {
            correctedId = "8.1";
        } else if (rawId == 82L) {
            correctedId = "8.2";
        } else {
            throw new RuntimeException("ID de zona no reconocido: " + rawId);
        }

        Map<String, Object> pronosticoMap = new LinkedHashMap<>(); // Garantiza el orden
        pronosticoMap.put("id-zona", correctedId);

        // Configurar la zona y los nombres de los tramos
        if ("8.1".equals(correctedId)) {
            pronosticoMap.put("zona", "Zona Ocho (F.Evangelistas a Lat 60 GDS Sur)");
            pronosticoMap.put("nombre_tramo", "(F.Evangelistas a Lat 56 GDS Sur)");
        } else if ("8.2".equals(correctedId)) {
            pronosticoMap.put("zona", "Zona Ocho (F.Evangelistas a Lat 60 GDS Sur)");
            pronosticoMap.put("nombre_tramo", "(Lat 56 GDS Sur a Lat 60 GDS Sur)");
        }

        pronosticoMap.put("situacion_sinoptica", dto.getSituacion_sinoptica());
        pronosticoMap.put("nubosidad", dto.getNubosidad());
        pronosticoMap.put("visibilidad", dto.getVisibilidad());
        pronosticoMap.put("viento", dto.getViento());
        pronosticoMap.put("mar_oceanico", dto.getMar_oceanico());

        return pronosticoMap;
    }

    public Map<String, Object> getZonaSieteYOchoFromRedis() {
        try {
            String json = redisTemplate.opsForValue().get("pronosticoSieteYOcho");
            if (json == null) {
                throw new RuntimeException("No se encontraron datos para Zonas Siete y Ocho en Redis");
            }
            return new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener Zonas Siete y Ocho desde Redis", e);
        }
    }
    /// final 7 y 8 (cargado por punta arenas)

    public Map<String, Object> consolidatePronosticos() {
        Map<String, Object> consolidatedOutput = new LinkedHashMap<>();
        consolidatedOutput.put("titulo", "Informe Meteorológico de Tiempo Marítimo desde Arica a Lat 60 GDS Sur");

        Map<String, String> periodoValidez = new LinkedHashMap<>();
        List<Map<String, Object>> pronosticoDetallado = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            // Recuperar JSON desde Redis
            String unoASeisJson = redisTemplate.opsForValue().get("pronosticoUnoASeis");
            String sieteYOchoJson = redisTemplate.opsForValue().get("pronosticoSieteYOcho");

            // Procesar el primer JSON
            if (unoASeisJson != null) {
                Map<String, Object> unoASeisData = mapper.readValue(unoASeisJson, LinkedHashMap.class);
                // Extraer el inicio del periodo de validez
                Map<String, String> validezUnoASeis = (Map<String, String>) unoASeisData.get("validez");
                if (validezUnoASeis != null) {
                    periodoValidez.put("inicio", validezUnoASeis.get("inicio"));
                }
                // Agregar pronósticos detallados
                List<Map<String, Object>> pronosticosUnoASeis = (List<Map<String, Object>>) unoASeisData.get("pronosticos");
                if (pronosticosUnoASeis != null) {
                    pronosticoDetallado.addAll(pronosticosUnoASeis);
                }
            }

            // Procesar el segundo JSON
            if (sieteYOchoJson != null) {
                Map<String, Object> sieteYOchoData = mapper.readValue(sieteYOchoJson, LinkedHashMap.class);
                // Extraer el fin del periodo de validez
                Map<String, String> validezSieteYOcho = (Map<String, String>) sieteYOchoData.get("validez");
                if (validezSieteYOcho != null) {
                    periodoValidez.put("fin", validezSieteYOcho.get("fin"));
                }
                // Agregar pronósticos detallados
                List<Map<String, Object>> pronosticosSieteYOcho = (List<Map<String, Object>>) sieteYOchoData.get("pronosticos");
                if (pronosticosSieteYOcho != null) {
                    pronosticoDetallado.addAll(pronosticosSieteYOcho);
                }
            }

            // Construir el JSON consolidado
            Map<String, Object> pronostico = new LinkedHashMap<>();
            pronostico.put("periodo_validez", periodoValidez);
            pronostico.put("pronostico_detallado", pronosticoDetallado);
            consolidatedOutput.put("pronostico", pronostico);

            // Guardar en Redis
            String consolidatedJson = mapper.writeValueAsString(consolidatedOutput);
            redisTemplate.opsForValue().set("pronosticoConsolidado", consolidatedJson);

        } catch (Exception e) {
            throw new RuntimeException("Error al consolidar los pronósticos: " + e.getMessage(), e);
        }

        return consolidatedOutput;
    }
}