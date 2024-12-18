package cl.servimet.regional.maritimo.controller;

import cl.servimet.regional.maritimo.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/regional")
public class RegionalController {

    public final RegionalService regionalService;

    public RegionalController(RegionalService regionalService) {
        this.regionalService = regionalService;
    }

    @GetMapping("/data/{tipoArchivo}")
    public List<?> obtenerDatos(@PathVariable String tipoArchivo) {
        ExcelConfig config = getConfig(tipoArchivo);
        Class<?> modelo;
        switch (tipoArchivo) {
            case "regional_maritimo" -> modelo = Regional.class;
            //case "bahias_met" -> modelo = BahiasMet.class;
            case "iden" -> modelo = Identificador.class;
            case "meteoam" -> modelo = MeteoAM.class;
            case "meteopm" -> modelo = MeteoPM.class;
            case "proximos" -> modelo = ProximosPronosticos.class;
            case "canales_australes" -> modelo = CanalesAustrales.class;
            case "canales" -> modelo = CanalesMontt.class;
            case "zonanueve" -> modelo = Zonanueve.class;
            case "zonadiez" -> modelo = Zonadiez.class;

            default -> throw new RuntimeException("Tipo de archivo no soportado");
        }
        return (List<?>) regionalService.leerArchivoExcel(config, modelo);
    }

    private ExcelConfig getConfig(String tipoArchivo) {
        switch (tipoArchivo) {
            case "regional_maritimo" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\regional_maritimo.xlsx", new String[]{"Maritimo"}, true);
            }
            case "bahias_met" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx", new String[]{"Identificador", "MeteoAM", "MeteoPM", "ProximosPronosticos"}, true);
            }
            case "iden" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx", new String[]{"Identificador"}, true);
            }
            case "meteoam" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx", new String[]{"MeteoAM"}, true);
            }
            case "meteopm" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx", new String[]{"MeteoPM"}, true);
            }
            case "proximos" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx", new String[]{"ProximosPronosticos"}, true);
            }

            case "canales_australes" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\canales_australes.xlsx", new String[]{"Canales"}, true);
            }
            case "canales" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\canales_australes.xlsx", new String[]{"Montt"}, true);
            }
            case "zonanueve" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\zonas.xlsx", new String[]{"zonanueve"}, true);
            }
            case "zonadiez" -> {
                return new ExcelConfig("C:\\Users\\Radeon-PC\\Desktop\\zonas.xlsx", new String[]{"zonadiez"}, true);
            }
            default -> throw new RuntimeException("Tipo de archivo no soportado");
        }
    }

//    @GetMapping("/data/consolidado")
//    public BahiasMet obtenerDatosConsolidados() {
//        ConsolidatedExcelConfig consolidatedConfig = ConsolidatedExcelConfigFactory.createConfig();
//
//        BahiasMet datosConsolidados = new BahiasMet();
//
//        datosConsolidados.setIdentificadores(
//                (List<Identificador>) regionalService.leerArchivoExcel(
//                        consolidatedConfig.getConfig("iden"), Identificador.class
//                )
//        );
//        datosConsolidados.setMeteoAMs(
//                (List<MeteoAM>) regionalService.leerArchivoExcel(
//                        consolidatedConfig.getConfig("meteoam"), MeteoAM.class
//                )
//        );
//        datosConsolidados.setMeteoPMs(
//                (List<MeteoPM>) regionalService.leerArchivoExcel(
//                        consolidatedConfig.getConfig("meteopm"), MeteoPM.class
//                )
//        );
//        datosConsolidados.setProximosPronosticos(
//                (List<ProximosPronosticos>) regionalService.leerArchivoExcel(
//                        consolidatedConfig.getConfig("proximos"), ProximosPronosticos.class
//                )
//        );
//
//        return datosConsolidados;
//    }
@GetMapping("/data/cenmeteoique")
public Map<String, Object> obtenerCenmeteoique() {
    // Leer los datos mapeados desde el servicio
    List<Identificador> identificadores = regionalService.leerArchivoExcel(getConfig("iden"), Identificador.class);
    List<MeteoAM> meteoAMs = regionalService.leerArchivoExcel(getConfig("meteoam"), MeteoAM.class);
    List<MeteoPM> meteoPMs = regionalService.leerArchivoExcel(getConfig("meteopm"), MeteoPM.class);
    List<ProximosPronosticos> proximosPronosticos = regionalService.leerArchivoExcel(getConfig("proximos"), ProximosPronosticos.class);

    // Crear la estructura de salida
    Map<String, Object> salida = new HashMap<>();
    salida.put("titulo", "Pronóstico Meteorológico Marítimo para Bahías");

    // Crear el centro meteorológico
    Map<String, Object> centroMeteorologico = new HashMap<>();
    centroMeteorologico.put("nombre", "Centro Meteorológico Marítimo de Iquique");
    centroMeteorologico.put("id-centro", 1);

    // Agrupar las bahías
    Map<Long, Map<String, Object>> bahiasMap = new HashMap<>();
    for (Identificador identificador : identificadores) {
        Map<String, Object> bahia = new HashMap<>();
        bahia.put("nombre", identificador.getNombreBahia());
        bahia.put("id-bahia", identificador.getIdBahia());
        bahia.put("pronostico_actual", new ArrayList<>()); // Lista para los pronósticos

        bahiasMap.put(identificador.getIdBahia(), bahia);
    }

    // Mapear pronósticos AM con detalles adicionales
    for (MeteoAM meteoAM : meteoAMs) {
        Map<String, Object> pronostico = new HashMap<>();
        pronostico.put("id-pronostico", meteoAM.getIdPronostico());
        pronostico.put("periodo_validez", Map.of(
                "dia", meteoAM.getDia(),
                "numeral", meteoAM.getNumeral(),
                "mes", meteoAM.getMes(),
                "inicio", meteoAM.getInicio(),
                "fin", meteoAM.getFin(),
                "zona_horaria", meteoAM.getZonaHoraria(),
                "temperatura_minima", meteoAM.getTemperaturaMinima(),
                "temperatura_maxima", meteoAM.getTemperaturaMaxima(),
                "indice_radiacion_uv", meteoAM.getIndiceRadiacionUV()
        ));
        pronostico.put("situacion_sinoptica", meteoAM.getSituacionSinoptica());

        // Agregar detalles del pronóstico
        Map<String, Object> detallesPronostico = new HashMap<>();
        detallesPronostico.put("cobertura_nubosa", meteoAM.getCoberturaNubosa());
        detallesPronostico.put("visibilidad", meteoAM.getVisibilidad());
        detallesPronostico.put("fenomeno_atmosferico", meteoAM.getFenomenoAtmosferico());

        // Agregar viento
        Map<String, Object> viento = new HashMap<>();
        viento.put("direccion", meteoAM.getDireccionViento1());
        viento.put("velocidad", meteoAM.getVelocidad1());
        viento.put("direccion2", meteoAM.getDireccionViento2());
        viento.put("velocidad2", meteoAM.getVelocidad2());
        detallesPronostico.put("viento", viento);

        // Agregar estado del mar
        Map<String, Object> estadoMar = new HashMap<>();
        estadoMar.put("altura_olas", meteoAM.getAlturaOlas());
        estadoMar.put("descripcion", meteoAM.getDescripcion());
        estadoMar.put("mar_bahia", meteoAM.getEstadoMarBahia());
        detallesPronostico.put("estado_mar", estadoMar);

        pronostico.put("pronostico", detallesPronostico);

        // Añadir a la bahía correspondiente
        if (bahiasMap.containsKey(meteoAM.getIdBahia())) {
            List<Object> pronosticos = (List<Object>) bahiasMap.get(meteoAM.getIdBahia()).get("pronostico_actual");
            pronosticos.add(pronostico);
        }
    }

    // Mapear pronósticos PM
    for (MeteoPM meteoPM : meteoPMs) {
        Map<String, Object> pronostico = new HashMap<>();
        pronostico.put("id-pronostico", meteoPM.getIdPronostico());
        pronostico.put("periodo_validez", Map.of(
                "inicio", meteoPM.getInicio(),
                "fin", meteoPM.getFin(),
                "zona_horaria", meteoPM.getZonaHoraria()
        ));
        pronostico.put("situacion_sinoptica", meteoPM.getSituacionSinoptica());

        // Añadir a la bahía correspondiente
        if (bahiasMap.containsKey(meteoPM.getIdBahia())) {
            List<Object> pronosticos = (List<Object>) bahiasMap.get(meteoPM.getIdBahia()).get("pronostico_actual");
            pronosticos.add(pronostico);
        }
    }

    // Mapear próximos pronósticos
    for (ProximosPronosticos proximo : proximosPronosticos) {
        Map<String, Object> proximoPronostico = new HashMap<>();
        proximoPronostico.put("id", proximo.getIdPronostico());
        proximoPronostico.put("dia", proximo.getDia());
        proximoPronostico.put("situacion_sinoptica", proximo.getSituacionSinoptica());

        if (bahiasMap.containsKey(proximo.getIdBahia())) {
            List<Object> pronosticos = (List<Object>) bahiasMap.get(proximo.getIdBahia()).get("pronostico_actual");
            pronosticos.add(proximoPronostico);
        }
    }

    // Añadir las bahías al centro meteorológico
    List<Object> bahiasList = Collections.singletonList(bahiasMap.values().stream().map(bahia -> Map.of("bahia", bahia)).toList());
    centroMeteorologico.put("bahias", bahiasList);

    // Añadir el centro meteorológico a la salida
    salida.put("centros_meteorologicos", List.of(centroMeteorologico));

    return salida;
}

}