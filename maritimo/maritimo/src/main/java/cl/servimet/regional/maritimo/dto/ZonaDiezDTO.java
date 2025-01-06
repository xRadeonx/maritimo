package cl.servimet.regional.maritimo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class ZonaDiezDTO {

    private Validez validez;
    private List<ParteUno> parteUno;
    private List<ParteDos> parteDos;
    private List<ParteTres> parteTres;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Validez {
        private String inicio;
        private String fin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParteUno{
        private String titulo;
        private String id;
        private String sector;
        private String condicion;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParteDos {
        private String id;
        private String titulo;
        private String hora_analisis;
        private String contenido;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParteTres {
        private String titulo;
        private String id;
        private String sector;
        private String condicion;
    }
}
