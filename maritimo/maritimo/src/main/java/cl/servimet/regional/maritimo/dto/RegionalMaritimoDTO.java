package cl.servimet.regional.maritimo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class RegionalMaritimoDTO {
    private List<Identificadores> identificadorList;
    private List<MeteoAM> meteoamList;
    private List<MeteoPM> meteopmList;
    private List<ProximosPronosticos> proximosPronosticosList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Identificadores{
        private int idCentro;
        private String nombreCentro;
        private int idBahia;
        private String nombreBahia;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MeteoAM{
        private int idPronostico;
        private int idCentro;
        private int idBahia;
        private String dia;
        private int numeral;
        private String mes;
        private String inicio;
        private String fin;
        private String zonaHoraria;
        private String temperaturaMinima;
        private String temperaturaMaxima;
        private String indiceRadiacionUV;
        private String situacionSinoptica;
        private String coberturaNubosa;
        private String visibilidad;
        private String fenomenoAtmosferico;
        private String direccionViento1;
        private String velocidad1;
        private String direccionViento2;
        private String velocidad2;
        private String alturaOlas;
        private String descripcion;
        private String estadoMarBahia;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MeteoPM{
        private int idPronostico;
        private int idCentro;
        private int idBahia;
        private String inicio;
        private String fin;
        private String zonaHoraria;
        private String situacionSinoptica;
        private String coberturaNubosa;
        private String visibilidad;
        private String fenomenoAtmosferico;
        private String direccionViento1;
        private String velocidad1;
        private String direccionViento2;
        private String velocidad2;
        private String alturaOlas;
        private String descripcion;
        private String estadoMarBahia;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProximosPronosticos{
        private String idPronostico;
        private int idCentro;
        private int idBahia;
        private String dia;
        private int numeral;
        private String mes;
        private String situacionSinoptica;
        private String coberturaNubosa;
        private String visibilidad;
        private String fenomenoAtmosferico;
        private String direccionViento1;
        private String velocidad1;
        private String direccionViento2;
        private String velocidad2;
        private String alturaOlas;
        private String descripcion;
        private String estadoMarBahia;
        private String temperaturaMinima;
        private String temperaturaMaxima;
    }
}
