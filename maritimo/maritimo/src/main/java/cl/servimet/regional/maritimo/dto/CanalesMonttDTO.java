package cl.servimet.regional.maritimo.dto;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;

@Data
public class CanalesMonttDTO {
    private Long id;
    private String titulo;
    private String inicio;
    private String fin;
    private String situacion_sinoptica;
    private String zona;
    private String nubosidad;
    private String visibilidad;
    private String viento;
    private String estado_mar;
    private String apreciacion;
    private String valida_desde;
    private String valida_hasta;
    private String zona_horaria;
    private String situacion;
    private String cobertura_nubosa;
    private String visibilidad_apreciacion;
    private String viento_apreciacion;
}