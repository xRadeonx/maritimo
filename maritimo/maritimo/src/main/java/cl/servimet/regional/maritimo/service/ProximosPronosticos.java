package cl.servimet.regional.maritimo.service;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("ProximosPronosticos")
public class ProximosPronosticos {
    @ExcelCellName("idPronostico")
    private String idPronostico;

    @ExcelCellName("idCentro")
    private Long idCentro;

    @ExcelCellName("idBahia")
    private Long idBahia;

    @ExcelCellName("dia")
    private String dia;

    @ExcelCellName("numeral")
    private Integer numeral;

    @ExcelCellName("mes")
    private String mes;

    @ExcelCellName("situacionSinoptica")
    private String situacionSinoptica;

    @ExcelCellName("coberturaNubosa")
    private String coberturaNubosa;

    @ExcelCellName("visibilidad")
    private String visibilidad;

    @ExcelCellName("fenomenoAtmosferico")
    private String fenomenoAtmosferico;

    @ExcelCellName("direccionViento1")
    private String direccionViento1;

    @ExcelCellName("velocidad1")
    private String velocidad1;

    @ExcelCellName("direccionViento2")
    private String direccionViento2;

    @ExcelCellName("velocidad2")
    private String velocidad2;

    @ExcelCellName("alturaOlas")
    private String alturaOlas;

    @ExcelCellName("descripcion")
    private String descripcion;

    @ExcelCellName("estadoMarBahia")
    private String estadoMarBahia;

    @ExcelCellName("temperaturaMinima")
    private String temperaturaMinima;

    @ExcelCellName("temperaturaMaxima")
    private String temperaturaMaxima;

    public ProximosPronosticos(String idPronostico, Long idCentro, Long idBahia, String dia, Integer numeral, String mes, String situacionSinoptica, String coberturaNubosa, String visibilidad, String fenomenoAtmosferico, String direccionViento1, String velocidad1, String direccionViento2, String velocidad2, String alturaOlas, String descripcion, String estadoMarBahia, String temperaturaMinima, String temperaturaMaxima) {
        this.idPronostico = idPronostico;
        this.idCentro = idCentro;
        this.idBahia = idBahia;
        this.dia = dia;
        this.numeral = numeral;
        this.mes = mes;
        this.situacionSinoptica = situacionSinoptica;
        this.coberturaNubosa = coberturaNubosa;
        this.visibilidad = visibilidad;
        this.fenomenoAtmosferico = fenomenoAtmosferico;
        this.direccionViento1 = direccionViento1;
        this.velocidad1 = velocidad1;
        this.direccionViento2 = direccionViento2;
        this.velocidad2 = velocidad2;
        this.alturaOlas = alturaOlas;
        this.descripcion = descripcion;
        this.estadoMarBahia = estadoMarBahia;
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public ProximosPronosticos() {
    }

    public String getIdPronostico() {
        return idPronostico;
    }

    public void setIdPronostico(String idPronostico) {
        this.idPronostico = idPronostico;
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }

    public Long getIdBahia() {
        return idBahia;
    }

    public void setIdBahia(Long idBahia) {
        this.idBahia = idBahia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getNumeral() {
        return numeral;
    }

    public void setNumeral(Integer numeral) {
        this.numeral = numeral;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getSituacionSinoptica() {
        return situacionSinoptica;
    }

    public void setSituacionSinoptica(String situacionSinoptica) {
        this.situacionSinoptica = situacionSinoptica;
    }

    public String getCoberturaNubosa() {
        return coberturaNubosa;
    }

    public void setCoberturaNubosa(String coberturaNubosa) {
        this.coberturaNubosa = coberturaNubosa;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getFenomenoAtmosferico() {
        return fenomenoAtmosferico;
    }

    public void setFenomenoAtmosferico(String fenomenoAtmosferico) {
        this.fenomenoAtmosferico = fenomenoAtmosferico;
    }

    public String getDireccionViento1() {
        return direccionViento1;
    }

    public void setDireccionViento1(String direccionViento1) {
        this.direccionViento1 = direccionViento1;
    }

    public String getVelocidad1() {
        return velocidad1;
    }

    public void setVelocidad1(String velocidad1) {
        this.velocidad1 = velocidad1;
    }

    public String getDireccionViento2() {
        return direccionViento2;
    }

    public void setDireccionViento2(String direccionViento2) {
        this.direccionViento2 = direccionViento2;
    }

    public String getVelocidad2() {
        return velocidad2;
    }

    public void setVelocidad2(String velocidad2) {
        this.velocidad2 = velocidad2;
    }

    public String getAlturaOlas() {
        return alturaOlas;
    }

    public void setAlturaOlas(String alturaOlas) {
        this.alturaOlas = alturaOlas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoMarBahia() {
        return estadoMarBahia;
    }

    public void setEstadoMarBahia(String estadoMarBahia) {
        this.estadoMarBahia = estadoMarBahia;
    }

    public String getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(String temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public String getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(String temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    @Override
    public String toString() {
        return "ProximosPronosticos{" +
                "idPronostico='" + idPronostico + '\'' +
                ", idCentro=" + idCentro +
                ", idBahia=" + idBahia +
                ", dia='" + dia + '\'' +
                ", numeral=" + numeral +
                ", mes='" + mes + '\'' +
                ", situacionSinoptica='" + situacionSinoptica + '\'' +
                ", coberturaNubosa='" + coberturaNubosa + '\'' +
                ", visibilidad='" + visibilidad + '\'' +
                ", fenomenoAtmosferico='" + fenomenoAtmosferico + '\'' +
                ", direccionViento1='" + direccionViento1 + '\'' +
                ", velocidad1='" + velocidad1 + '\'' +
                ", direccionViento2='" + direccionViento2 + '\'' +
                ", velocidad2='" + velocidad2 + '\'' +
                ", alturaOlas='" + alturaOlas + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estadoMarBahia='" + estadoMarBahia + '\'' +
                ", temperaturaMinima='" + temperaturaMinima + '\'' +
                ", temperaturaMaxima='" + temperaturaMaxima + '\'' +
                '}';
    }
}
