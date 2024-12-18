package cl.servimet.regional.maritimo.service;

import cl.servimet.regional.maritimo.controller.ExcelConfig;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class RegionalService {
    public <T> List<T> leerArchivoExcel(ExcelConfig config, Class<T> modelo) {
        File archivo = new File(config.getRutaArchivo());
        if (!archivo.exists() || !archivo.isFile()) {
            throw new IllegalArgumentException("El archivo especificado no existe o no es un archivo válido: " + config.getRutaArchivo());
        }

        List<T> resultados = new ArrayList<>();
        for (String nombreHoja : config.getNombresHojas()) {
            try {
                PoijiOptions options = PoijiOptions.PoijiOptionsBuilder
                        .settings()
                        .sheetName(nombreHoja)
                        .trimCellValue(config.isTrimCellValue())
                        .build();

                List<T> resultadosHoja = Poiji.fromExcel(archivo, modelo, options);
                resultados.addAll(resultadosHoja);
            } catch (Exception e) {
                throw new RuntimeException("Error procesando la hoja: " + nombreHoja, e);
            }
        }
        return resultados;
    }
}