package cl.servimet.regional.maritimo.service;

import cl.servimet.regional.maritimo.controller.ConsolidatedExcelConfig;
import cl.servimet.regional.maritimo.controller.ExcelConfig;

import java.util.HashMap;
import java.util.Map;

public class ConsolidatedExcelConfigFactory {

    public static ConsolidatedExcelConfig createConfig() {
        Map<String, ExcelConfig> configMap = new HashMap<>();

        // Define configuraciones individuales
        configMap.put("iden", new ExcelConfig(
                "C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx",
                new String[]{"Identificador"}, true
        ));
        configMap.put("meteoam", new ExcelConfig(
                "C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx",
                new String[]{"MeteoAM"}, true
        ));
        configMap.put("meteopm", new ExcelConfig(
                "C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx",
                new String[]{"MeteoPM"}, true
        ));
        configMap.put("proximos", new ExcelConfig(
                "C:\\Users\\Radeon-PC\\Desktop\\cenmeteoique.xlsx",
                new String[]{"ProximosPronosticos"}, true
        ));

        return new ConsolidatedExcelConfig(configMap);
    }
}
