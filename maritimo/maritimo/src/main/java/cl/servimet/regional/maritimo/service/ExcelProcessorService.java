package cl.servimet.regional.maritimo.service;

import cl.servimet.regional.maritimo.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelProcessorService {

    public List<RegionalMaritimoDTO.Identificadores> processIdentificadorSheet(InputStream excelFile) throws Exception {
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("Identificador");

        List<RegionalMaritimoDTO.Identificadores> identificadorList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            RegionalMaritimoDTO.Identificadores identificador = new RegionalMaritimoDTO.Identificadores();
            identificador.setIdCentro(getCellIntValue(row.getCell(0)));
            identificador.setNombreCentro(getCellStringValue(row.getCell(1)));
            identificador.setIdBahia(getCellIntValue(row.getCell(2)));
            identificador.setNombreBahia(getCellStringValue(row.getCell(3)));

            identificadorList.add(identificador);
        }
        workbook.close();
        return identificadorList;
    }

    public List<RegionalMaritimoDTO.MeteoAM> processMeteoAMSheet(InputStream excelFile) throws Exception {
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("MeteoAM");

        List<RegionalMaritimoDTO.MeteoAM> meteoAMList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            RegionalMaritimoDTO.MeteoAM meteoAM = new RegionalMaritimoDTO.MeteoAM();
            meteoAM.setIdPronostico(getCellIntValue(row.getCell(0)));
            meteoAM.setIdCentro(getCellIntValue(row.getCell(1)));
            meteoAM.setIdBahia(getCellIntValue(row.getCell(2)));
            meteoAM.setDia(getCellStringValue(row.getCell(3)));
            meteoAM.setNumeral(getCellIntValue(row.getCell(4)));
            meteoAM.setMes(getCellStringValue(row.getCell(5)));
            meteoAM.setInicio(getCellStringValue(row.getCell(6)));
            meteoAM.setFin(getCellStringValue(row.getCell(7)));
            meteoAM.setZonaHoraria(getCellStringValue(row.getCell(8)));
            meteoAM.setTemperaturaMinima(getCellStringValue(row.getCell(9)));
            meteoAM.setTemperaturaMaxima(getCellStringValue(row.getCell(10)));
            meteoAM.setIndiceRadiacionUV(getCellStringValue(row.getCell(11)));
            meteoAM.setSituacionSinoptica(getCellStringValue(row.getCell(12)));
            meteoAM.setCoberturaNubosa(getCellStringValue(row.getCell(13)));
            meteoAM.setVisibilidad(getCellStringValue(row.getCell(14)));
            meteoAM.setFenomenoAtmosferico(getCellStringValue(row.getCell(15)));
            meteoAM.setDireccionViento1(getCellStringValue(row.getCell(16)));
            meteoAM.setVelocidad1(getCellStringValue(row.getCell(17)));
            meteoAM.setDireccionViento2(getCellStringValue(row.getCell(18)));
            meteoAM.setVelocidad2(getCellStringValue(row.getCell(19)));
            meteoAM.setAlturaOlas(getCellStringValue(row.getCell(20)));
            meteoAM.setDescripcion(getCellStringValue(row.getCell(21)));
            meteoAM.setEstadoMarBahia(getCellStringValue(row.getCell(22)));

            meteoAMList.add(meteoAM);
        }
        workbook.close();
        return meteoAMList;
    }

    public List<RegionalMaritimoDTO.MeteoPM> processMeteoPMSheet(InputStream excelFile) throws Exception {
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("MeteoPM");

        List<RegionalMaritimoDTO.MeteoPM> meteoPMList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            RegionalMaritimoDTO.MeteoPM meteoPM = new RegionalMaritimoDTO.MeteoPM();
            meteoPM.setIdPronostico(getCellIntValue(row.getCell(0)));
            meteoPM.setIdCentro(getCellIntValue(row.getCell(1)));
            meteoPM.setIdBahia(getCellIntValue(row.getCell(2)));
            meteoPM.setInicio(getCellStringValue(row.getCell(3)));
            meteoPM.setFin(getCellStringValue(row.getCell(4)));
            meteoPM.setZonaHoraria(getCellStringValue(row.getCell(5)));
            meteoPM.setSituacionSinoptica(getCellStringValue(row.getCell(6)));
            meteoPM.setCoberturaNubosa(getCellStringValue(row.getCell(7)));
            meteoPM.setVisibilidad(getCellStringValue(row.getCell(8)));
            meteoPM.setFenomenoAtmosferico(getCellStringValue(row.getCell(9)));
            meteoPM.setDireccionViento1(getCellStringValue(row.getCell(10)));
            meteoPM.setVelocidad1(getCellStringValue(row.getCell(11)));
            meteoPM.setDireccionViento2(getCellStringValue(row.getCell(12)));
            meteoPM.setVelocidad2(getCellStringValue(row.getCell(13)));
            meteoPM.setAlturaOlas(getCellStringValue(row.getCell(14)));
            meteoPM.setDescripcion(getCellStringValue(row.getCell(15)));
            meteoPM.setEstadoMarBahia(getCellStringValue(row.getCell(16)));

            meteoPMList.add(meteoPM);
        }
        workbook.close();
        return meteoPMList;
    }

    public List<RegionalMaritimoDTO.ProximosPronosticos> processProximosPronosticosSheet(InputStream excelFile) throws Exception {
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("ProximosPronosticos");

        List<RegionalMaritimoDTO.ProximosPronosticos> proximosPronosticosList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            RegionalMaritimoDTO.ProximosPronosticos proximosPronosticos = new RegionalMaritimoDTO.ProximosPronosticos();
            proximosPronosticos.setIdPronostico(getCellStringValue(row.getCell(0)));
            proximosPronosticos.setIdCentro(getCellIntValue(row.getCell(1)));
            proximosPronosticos.setIdBahia(getCellIntValue(row.getCell(2)));
            proximosPronosticos.setDia(getCellStringValue(row.getCell(3)));
            proximosPronosticos.setNumeral(getCellIntValue(row.getCell(4)));
            proximosPronosticos.setMes(getCellStringValue(row.getCell(5)));
            proximosPronosticos.setSituacionSinoptica(getCellStringValue(row.getCell(6)));
            proximosPronosticos.setCoberturaNubosa(getCellStringValue(row.getCell(7)));
            proximosPronosticos.setVisibilidad(getCellStringValue(row.getCell(8)));
            proximosPronosticos.setFenomenoAtmosferico(getCellStringValue(row.getCell(9)));
            proximosPronosticos.setDireccionViento1(getCellStringValue(row.getCell(10)));
            proximosPronosticos.setVelocidad1(getCellStringValue(row.getCell(11)));
            proximosPronosticos.setDireccionViento2(getCellStringValue(row.getCell(12)));
            proximosPronosticos.setVelocidad2(getCellStringValue(row.getCell(13)));
            proximosPronosticos.setAlturaOlas(getCellStringValue(row.getCell(14)));
            proximosPronosticos.setDescripcion(getCellStringValue(row.getCell(15)));
            proximosPronosticos.setEstadoMarBahia(getCellStringValue(row.getCell(16)));
            proximosPronosticos.setTemperaturaMinima(getCellStringValue(row.getCell(17)));
            proximosPronosticos.setTemperaturaMaxima(getCellStringValue(row.getCell(18)));

            proximosPronosticosList.add(proximosPronosticos);
        }
        workbook.close();
        return proximosPronosticosList;
    }

    public List<CanalesAustralesDTO> processCanalesAustrales(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<CanalesAustralesDTO> dataList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            CanalesAustralesDTO dto = new CanalesAustralesDTO();
            dto.setId(getCellLongValue(row.getCell(0)));
            dto.setTitulo(getCellStringValue(row.getCell(1)));
            dto.setInicio(getCellStringValue(row.getCell(2)));
            dto.setFin(getCellStringValue(row.getCell(3)));
            dto.setZona(getCellStringValue(row.getCell(4)));
            dto.setNubosidad(getCellStringValue(row.getCell(5)));
            dto.setVisibilidad(getCellStringValue(row.getCell(6)));
            dto.setViento(getCellStringValue(row.getCell(7)));
            dto.setEstado_mar(getCellStringValue(row.getCell(8)));
            dataList.add(dto);
        }
        workbook.close();
        return dataList;
    }

    public List<CanalesMonttDTO> processCanalesMontt(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<CanalesMonttDTO> dataList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            CanalesMonttDTO dto = new CanalesMonttDTO();
            dto.setId(getCellLongValue(row.getCell(0)));
            dto.setTitulo(getCellStringValue(row.getCell(1)));
            dto.setInicio(getCellStringValue(row.getCell(2)));
            dto.setFin(getCellStringValue(row.getCell(3)));
            dto.setSituacion_sinoptica(getCellStringValue(row.getCell(4)));
            dto.setZona(getCellStringValue(row.getCell(5)));
            dto.setNubosidad(getCellStringValue(row.getCell(6)));
            dto.setVisibilidad(getCellStringValue(row.getCell(7)));
            dto.setViento(getCellStringValue(row.getCell(8)));
            dto.setEstado_mar(getCellStringValue(row.getCell(9)));
            dto.setApreciacion(getCellStringValue(row.getCell(10)));
            dto.setValida_desde(getCellStringValue(row.getCell(11)));
            dto.setValida_hasta(getCellStringValue(row.getCell(12)));
            dto.setZona_horaria(getCellStringValue(row.getCell(13)));
            dto.setSituacion(getCellStringValue(row.getCell(14)));
            dto.setCobertura_nubosa(getCellStringValue(row.getCell(15)));
            dto.setVisibilidad_apreciacion(getCellStringValue(row.getCell(16)));
            dto.setViento_apreciacion(getCellStringValue(row.getCell(17)));

            dataList.add(dto);
        }
        workbook.close();
        return dataList;
    }

    public List<ZonaNueveDTO> processZonaNueve(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ZonaNueveDTO> dataList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            ZonaNueveDTO zonanueve = new ZonaNueveDTO();
            zonanueve.setId(getCellLongValue(row.getCell(3)));
            zonanueve.setTitulo(getCellStringValue(row.getCell(0)));
            zonanueve.setInicio(getCellStringValue(row.getCell(1)));
            zonanueve.setFin(getCellStringValue(row.getCell(2)));
            zonanueve.setCampo(getCellStringValue(row.getCell(4)));
            zonanueve.setDescripcion(getCellStringValue(row.getCell(5)));
            zonanueve.setCobertura_nubosa(getCellStringValue(row.getCell(6)));
            zonanueve.setVisibilidad(getCellStringValue(row.getCell(7)));
            zonanueve.setViento(getCellStringValue(row.getCell(8)));
            zonanueve.setEstado_mar(getCellStringValue(row.getCell(9)));
            dataList.add(zonanueve);
        }
        workbook.close();
        return dataList;
    }

    public ZonaDiezDTO processZonaDiez(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);

        ZonaDiezDTO.ZonaDiezDTOBuilder zonaDiezBuilder = ZonaDiezDTO.builder();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();

            if (sheetName.equalsIgnoreCase("Parte I")) {
                // Procesar Parte I (Validez y Sectores)
                String inicio = getCellStringValue(sheet.getRow(1).getCell(1));
                String fin = getCellStringValue(sheet.getRow(1).getCell(2));

                zonaDiezBuilder.validez(ZonaDiezDTO.Validez.builder()
                        .inicio(inicio)
                        .fin(fin)
                        .build());

                zonaDiezBuilder.parteUno(sheetToParteUno(sheet));
            } else if (sheetName.equalsIgnoreCase("Parte II")) {
                // Procesar Parte II
                zonaDiezBuilder.parteDos(sheetToParteDos(sheet));
            } else if (sheetName.equalsIgnoreCase("Parte III")) {
                // Procesar Parte III
                zonaDiezBuilder.parteTres(sheetToParteTres(sheet));
            }
        }

        workbook.close();
        return zonaDiezBuilder.build();
    }

    private List<ZonaDiezDTO.ParteUno> sheetToParteUno(Sheet sheet) {
        List<ZonaDiezDTO.ParteUno> parteUnoList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(3) == null) continue;

            parteUnoList.add(ZonaDiezDTO.ParteUno.builder()
                    .titulo(getCellStringValue(row.getCell(0)))
                    .id(getCellStringValue(row.getCell(3)))
                    .sector(getCellStringValue(row.getCell(4)))
                    .condicion(getCellStringValue(row.getCell(5)))
                    .build());
        }
        return parteUnoList;
    }

    private List<ZonaDiezDTO.ParteDos> sheetToParteDos(Sheet sheet) {
        List<ZonaDiezDTO.ParteDos> parteDosList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(3) == null) continue;

            parteDosList.add(ZonaDiezDTO.ParteDos.builder()
                    .id(getCellStringValue(row.getCell(1)))
                    .titulo(getCellStringValue(row.getCell(0)))
                    .hora_analisis(getCellStringValue(row.getCell(2)))
                    .contenido(getCellStringValue(row.getCell(3)))
                    .build());
        }
        return parteDosList;
    }

    private List<ZonaDiezDTO.ParteTres> sheetToParteTres(Sheet sheet) {
        List<ZonaDiezDTO.ParteTres> parteTresList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(3) == null) continue;

            parteTresList.add(ZonaDiezDTO.ParteTres.builder()
                    .titulo(getCellStringValue(row.getCell(0)))
                    .id(getCellStringValue(row.getCell(1)))
                    .sector(getCellStringValue(row.getCell(2)))
                    .condicion(getCellStringValue(row.getCell(3)))
                    .build());
        }
        return parteTresList;
    }

    public List<ZonaUnoSeisDTO> processZonaUnoSeis(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ZonaUnoSeisDTO> dataList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            ZonaUnoSeisDTO zonaunoseis = new ZonaUnoSeisDTO();
            zonaunoseis.setId(getCellLongValue(row.getCell(0)));
            zonaunoseis.setTitulo(getCellStringValue(row.getCell(1)));
            zonaunoseis.setInicio(getCellStringValue(row.getCell(2)));
            zonaunoseis.setFin(getCellStringValue(row.getCell(3)));
            zonaunoseis.setZona(getCellStringValue(row.getCell(4)));
            zonaunoseis.setSituacion_sinoptica(getCellStringValue(row.getCell(5)));
            zonaunoseis.setNubosidad(getCellStringValue(row.getCell(6)));
            zonaunoseis.setVisibilidad(getCellStringValue(row.getCell(7)));
            zonaunoseis.setViento(getCellStringValue(row.getCell(8)));
            zonaunoseis.setMar_oceanico(getCellStringValue(row.getCell(9)));
            zonaunoseis.setMar_bahia(getCellStringValue(row.getCell(10)));
            dataList.add(zonaunoseis);
        }
        workbook.close();
        return dataList;
    }

    public List<ZonaSieteOchoDTO> processZonaSieteOcho(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ZonaSieteOchoDTO> dataList = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            ZonaSieteOchoDTO zonasieteocho = new ZonaSieteOchoDTO();
            zonasieteocho.setId(getCellLongValue(row.getCell(0)));
            zonasieteocho.setTitulo(getCellStringValue(row.getCell(1)));
            zonasieteocho.setInicio(getCellStringValue(row.getCell(2)));
            zonasieteocho.setFin(getCellStringValue(row.getCell(3)));
            zonasieteocho.setZona(getCellStringValue(row.getCell(4)));
            zonasieteocho.setSituacion_sinoptica(getCellStringValue(row.getCell(5)));
            zonasieteocho.setNubosidad(getCellStringValue(row.getCell(6)));
            zonasieteocho.setVisibilidad(getCellStringValue(row.getCell(7)));
            zonasieteocho.setViento(getCellStringValue(row.getCell(8)));
            zonasieteocho.setMar_oceanico(getCellStringValue(row.getCell(9)));
            dataList.add(zonasieteocho);
        }
        workbook.close();
        return dataList;
    }

    private Integer getCellIntValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        try {
            return Integer.parseInt(cell.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long getCellLongValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        }
        try {
            return Long.parseLong(cell.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Verificar si la celda es de tipo fecha
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(cell.getDateCellValue());
                } else {
                    // Convertir a entero si es numérico
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
                return "";
            default:
                return "UNSUPPORTED_CELL_TYPE";
        }
    }
}