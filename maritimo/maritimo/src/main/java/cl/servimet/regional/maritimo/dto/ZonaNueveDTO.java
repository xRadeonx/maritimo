package cl.servimet.regional.maritimo.dto;

import lombok.Data;

@Data
public class ZonaNueveDTO {

    private Long id;
    private String titulo;
    private String inicio;
    private String fin;
    private String campo;
    private String descripcion;
    private String cobertura_nubosa;
    private String visibilidad;
    private String viento;
    private String estado_mar;
}