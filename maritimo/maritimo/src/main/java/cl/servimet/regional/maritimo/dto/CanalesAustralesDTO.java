package cl.servimet.regional.maritimo.dto;

import lombok.Data;

@Data
public class CanalesAustralesDTO {
    private Long id;
    private String titulo;
    private String inicio;
    private String fin;
    private String zona;
    private String nubosidad;
    private String visibilidad;
    private String viento;
    private String estado_mar;
}