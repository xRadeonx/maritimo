package cl.servimet.regional.maritimo.controller;

import java.util.Map;

public class ConsolidatedExcelConfig {
    private Map<String, ExcelConfig> configPorTipo;

    public ConsolidatedExcelConfig(Map<String, ExcelConfig> configPorTipo) {
        this.configPorTipo = configPorTipo;
    }

    public ExcelConfig getConfig(String tipoArchivo) {
        return configPorTipo.get(tipoArchivo);
    }
}
