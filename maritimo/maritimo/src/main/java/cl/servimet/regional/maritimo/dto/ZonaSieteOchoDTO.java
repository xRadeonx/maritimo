package cl.servimet.regional.maritimo.dto;

import lombok.Data;

@Data
public class ZonaSieteOchoDTO {
    private Long id;
    private String titulo;
    private String inicio;
    private String fin;
    private String zona;
    private String situacion_sinoptica;
    private String nubosidad;
    private String visibilidad;
    private String viento;
    private String mar_oceanico;
}