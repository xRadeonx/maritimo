package cl.servimet.regional.maritimo.controller;

import cl.servimet.regional.maritimo.dto.*;
import cl.servimet.regional.maritimo.service.ExcelProcessorService;
import cl.servimet.regional.maritimo.service.RedisStorageService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

        private final ExcelProcessorService excelProcessorService;

        private final RedisStorageService redisStorageService;

    public ExcelController(ExcelProcessorService excelProcessorService, RedisStorageService redisStorageService) {
        this.excelProcessorService = excelProcessorService;
        this.redisStorageService = redisStorageService;
    }

    @PostMapping("/uploadMultiple")//este recibe los excel de las bahias e itera sobre cada uno para obtener los datos de las hojas.
    public String uploadMultipleExcel(@RequestParam("files") List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files) {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                // Iterar sobre todas las hojas del archivo
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    String sheetName = sheet.getSheetName(); // Obtener el nombre de la hoja

                    // Procesar según el nombre de la hoja
                    switch (sheetName) {
                        case "Identificador" -> processIdentificadorSheet(file);
                        case "MeteoAM" -> processMeteoAMSheet(file);
                        case "MeteoPM" -> processMeteoPMSheet(file);
                        case "ProximosPronosticos" -> processProximosPronosticosSheet(file);
                        default -> throw new RuntimeException("Hoja no reconocida: " + sheetName);
                    }
                }
            }
        }
        return "Todas las hojas de los archivos procesadas y almacenadas en Redis";
    }

    private void processIdentificadorSheet(MultipartFile file) throws Exception {
        List<RegionalMaritimoDTO.Identificadores> data = excelProcessorService.processIdentificadorSheet(file.getInputStream());
        redisStorageService.mergeAndSaveToRedis("identificador", data, RegionalMaritimoDTO.Identificadores.class);
    }

    private void processMeteoAMSheet(MultipartFile file) throws Exception {
        List<RegionalMaritimoDTO.MeteoAM> data = excelProcessorService.processMeteoAMSheet(file.getInputStream());
        redisStorageService.mergeAndSaveToRedis("meteoAM", data, RegionalMaritimoDTO.MeteoAM.class);
    }

    private void processMeteoPMSheet(MultipartFile file) throws Exception {
        List<RegionalMaritimoDTO.MeteoPM> data = excelProcessorService.processMeteoPMSheet(file.getInputStream());
        redisStorageService.mergeAndSaveToRedis("meteoPM", data, RegionalMaritimoDTO.MeteoPM.class);
    }

    private void processProximosPronosticosSheet(MultipartFile file) throws Exception {
        List<RegionalMaritimoDTO.ProximosPronosticos> data = excelProcessorService.processProximosPronosticosSheet(file.getInputStream());
        redisStorageService.mergeAndSaveToRedis("proximosPronosticos", data, RegionalMaritimoDTO.ProximosPronosticos.class);
    }

    @GetMapping("/bahias_met")
    public Map<String, Object> generateAndReturnOrderedJson() {
        try {
            // Intentar recuperar el JSON ya generado desde Redis
            Map<String, Object> cachedJson = redisStorageService.getCompiledJsonFromRedis();
            if (!cachedJson.isEmpty()) {
                // Si existe un JSON en Redis, retornarlo directamente
                return cachedJson;
            }

            // Si no está en Redis, generar el JSON ordenado
            Map<String, Object> orderedJson = redisStorageService.generateOrderedJson();

            // Guardar el JSON generado en Redis para futuros usos
            redisStorageService.saveCompiledJson(orderedJson);

            return orderedJson; // Retornar el JSON ordenado recién generado
        } catch (Exception e) {
            logger.error("Error al generar o recuperar el JSON ordenado", e);
            return Map.of("error", "No se pudo generar ni recuperar el JSON ordenado.");
        }
    }

    /////endpoint canales australes y canales puerto montt.
    @PostMapping("/uploadCanalesAustrales")
    public ResponseEntity<String> uploadCanalesAustrales(@RequestParam("file") MultipartFile file) {
        try {
            List<CanalesAustralesDTO> canalesAustralesList = excelProcessorService.processCanalesAustrales(file.getInputStream());
            redisStorageService.saveCanalesAustralesToRedis(canalesAustralesList);
            return ResponseEntity.ok("Datos de Canales Australes procesados y guardados en Redis.");
        } catch (Exception e) {
            logger.error("Error al procesar y guardar Canales Australes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar y guardar Canales Australes.");
        }
    }

    @PostMapping("/uploadCanalesMontt")
    public ResponseEntity<String> uploadCanalesMontt(@RequestParam("file") MultipartFile file) {
        try {
            List<CanalesMonttDTO> canalesMonttList = excelProcessorService.processCanalesMontt(file.getInputStream());
            redisStorageService.saveCanalesMonttToRedis(canalesMonttList);
            return ResponseEntity.ok("Datos de Canales Montt procesados y guardados en Redis.");
        } catch (Exception e) {
            logger.error("Error al procesar y guardar Canales Montt.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar y guardar Canales Montt.");
        }
    }

    @GetMapping("/canalesAguasJson")
    public ResponseEntity<Map<String, Object>> getCanalesAguasJson() {
        try {
            Map<String, Object> json = redisStorageService.generateCanalesAguasJson();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            logger.error("Error, No se pudo generar el JSON consolidado.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo generar el JSON consolidado."));
        }
    }

    ///inicio zona nueve
    @PostMapping("/uploadZonaNueve")
    public ResponseEntity<String> uploadZonaNueve(@RequestParam("file") MultipartFile file) {
        try {
            List<ZonaNueveDTO> zonaNueveList = excelProcessorService.processZonaNueve(file.getInputStream());
            redisStorageService.saveZonaNueveToRedis(zonaNueveList);
            return ResponseEntity.ok("Datos de Zona Nueve procesados y guardados en Redis.");
        } catch (Exception e) {
            logger.error("Error al procesar y guardar Zona Nueve.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar y guardar Zona Nueve.");
        }
    }

    @GetMapping("/zonaNueve")
    public ResponseEntity<Map<String, Object>> getZonaNueveJson() {
        try {
            Map<String, Object> json = redisStorageService.getZonaNueveJsonFromRedis();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            logger.error("Error al recuperar el JSON de Zona Nueve.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo recuperar el JSON de Zona Nueve."));
        }
    }
    ///final zona nueve

    ///inicio zona diez

    @PostMapping("/zona-diez/upload")
    public ResponseEntity<String> uploadZonaDiez(@RequestParam("file") MultipartFile file) {
        try {
            redisStorageService.processAndSaveZonaDiezToRedis(file.getInputStream());
            return ResponseEntity.ok("Zona X procesada y guardada en Redis.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/zonaDiez")
    public ResponseEntity<Map<String, Object>> getZonaDiez() {
        try {
            Map<String, Object> zonaDiezData = redisStorageService.getZonaDiezFromRedis();
            return ResponseEntity.ok(zonaDiezData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo recuperar el JSON de Zona Diez."));
        }
    }

    ///final zona diez

    ///inicio 1 a 6
    @PostMapping("/uploadUnoASeis")
    public ResponseEntity<String> uploadUnoASeis(@RequestParam("file") MultipartFile file) {
        try {
            List<ZonaUnoSeisDTO> pronosticoUnoASeis = excelProcessorService.processZonaUnoSeis(file.getInputStream());
            redisStorageService.saveZonaUnoASeisToRedis(pronosticoUnoASeis);
            return ResponseEntity.ok("Datos de Uno a Seis procesados y guardados en Redis.");
        } catch (Exception e) {
            logger.error("Error al procesar y guardar Uno a Seis.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar y guardar Uno a Seis.");
        }
    }

    @GetMapping("/unoASeis")
    public ResponseEntity<Object> getUnoASeis() {
        try {
            Map<String, Object> pronosticos = redisStorageService.getZonaUnoASeisFromRedis();
            return ResponseEntity.ok(pronosticos);
        } catch (Exception e) {
            logger.error("Error al recuperar los datos de Uno a Seis.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al recuperar los datos de Uno a Seis.");
        }
    }

    ///final 1 a 6

    @GetMapping("/regionalMaritimo")
    public ResponseEntity<?> getPronosticoConsolidado() {
        try {
            Map<String, Object> pronosticoConsolidado = redisStorageService.consolidatePronosticos();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(pronosticoConsolidado);
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo consolidar los pronósticos", "mensaje", e.getMessage()));
        }
    }

    ///inicio 7 y 8
    @PostMapping("/uploadSieteYOcho")
    public ResponseEntity<String> uploadSieteYOcho(@RequestParam("file") MultipartFile file) {
        try {
            List<ZonaSieteOchoDTO> pronosticoSieteYOcho = excelProcessorService.processZonaSieteOcho(file.getInputStream());
            redisStorageService.saveZonaSieteYOchoToRedis(pronosticoSieteYOcho);
            return ResponseEntity.ok("Datos de Siete y Ocho procesados y guardados en Redis.");
        } catch (Exception e) {
            logger.error("Error al procesar y guardar Siete y Ocho.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar y guardar Siete y Ocho.");
        }
    }

    @GetMapping("/sieteYOcho")
    public ResponseEntity<Object> getSieteYOcho() {
        try {
            Map<String, Object> pronosticos = redisStorageService.getZonaSieteYOchoFromRedis();
            return ResponseEntity.ok(pronosticos);
        } catch (Exception e) {
            logger.error("Error al recuperar los datos de Siete y Ocho.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al recuperar los datos de Siete y Ocho.");
        }
    }
    ///final 7 y 8
}