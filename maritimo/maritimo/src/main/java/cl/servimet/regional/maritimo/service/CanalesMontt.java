package cl.servimet.regional.maritimo.service;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.poiji.annotation.ExcelCellName;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CanalesMontt {
    @ExcelCellName("id")
    private Long id;

    @ExcelCellName("titulo")
    private String titulo;

    @ExcelCellName("inicio")
    private String inicio;

    @ExcelCellName("fin")
    private String fin;

    @ExcelCellName("situacion_sinoptica")
    private String situacion_sinoptica;

    @ExcelCellName("zona")
    private String zona;

    @ExcelCellName("nubosidad")
    private String nubosidad;

    @ExcelCellName("visibilidad")
    private String visibilidad;

    @ExcelCellName("viento")
    private String viento;

    @ExcelCellName("estado_mar")
    private String estado_mar;

    @ExcelCellName("valida_desde")
    private String valida_desde;

    @ExcelCellName("valida_hasta")
    private String valida_hasta;

    @ExcelCellName("zona_horaria")
    private String zona_horaria;

    @ExcelCellName("situacion")
    private String situacion;

    @ExcelCellName("cobertura_nubosa")
    private String cobertura_nubosa;

    @ExcelCellName("visibilidad_apreciacion")
    private String visibilidad_apreciacion;

    @ExcelCellName("viento_apreciacion")
    private String viento_apreciacion;

    public CanalesMontt(Long id, String titulo, String inicio, String fin, String situacion_sinoptica, String zona, String nubosidad, String visibilidad, String viento, String estado_mar, String valida_desde, String valida_hasta, String zona_horaria, String situacion, String cobertura_nubosa, String visibilidad_apreciacion, String viento_apreciacion) {
        this.id = id;
        this.titulo = titulo;
        this.inicio = inicio;
        this.fin = fin;
        this.situacion_sinoptica = situacion_sinoptica;
        this.zona = zona;
        this.nubosidad = nubosidad;
        this.visibilidad = visibilidad;
        this.viento = viento;
        this.estado_mar = estado_mar;
        this.valida_desde = valida_desde;
        this.valida_hasta = valida_hasta;
        this.zona_horaria = zona_horaria;
        this.situacion = situacion;
        this.cobertura_nubosa = cobertura_nubosa;
        this.visibilidad_apreciacion = visibilidad_apreciacion;
        this.viento_apreciacion = viento_apreciacion;
    }

    public CanalesMontt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getSituacion_sinoptica() {
        return situacion_sinoptica;
    }

    public void setSituacion_sinoptica(String situacion_sinoptica) {
        this.situacion_sinoptica = situacion_sinoptica;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNubosidad() {
        return nubosidad;
    }

    public void setNubosidad(String nubosidad) {
        this.nubosidad = nubosidad;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getEstado_mar() {
        return estado_mar;
    }

    public void setEstado_mar(String estado_mar) {
        this.estado_mar = estado_mar;
    }

    public String getValida_desde() {
        return valida_desde;
    }

    public void setValida_desde(String valida_desde) {
        this.valida_desde = valida_desde;
    }

    public String getValida_hasta() {
        return valida_hasta;
    }

    public void setValida_hasta(String valida_hasta) {
        this.valida_hasta = valida_hasta;
    }

    public String getZona_horaria() {
        return zona_horaria;
    }

    public void setZona_horaria(String zona_horaria) {
        this.zona_horaria = zona_horaria;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getCobertura_nubosa() {
        return cobertura_nubosa;
    }

    public void setCobertura_nubosa(String cobertura_nubosa) {
        this.cobertura_nubosa = cobertura_nubosa;
    }

    public String getVisibilidad_apreciacion() {
        return visibilidad_apreciacion;
    }

    public void setVisibilidad_apreciacion(String visibilidad_apreciacion) {
        this.visibilidad_apreciacion = visibilidad_apreciacion;
    }

    public String getViento_apreciacion() {
        return viento_apreciacion;
    }

    public void setViento_apreciacion(String viento_apreciacion) {
        this.viento_apreciacion = viento_apreciacion;
    }

    @Override
    public String toString() {
        return "CanalesMontt{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", situacion_sinoptica='" + situacion_sinoptica + '\'' +
                ", zona='" + zona + '\'' +
                ", nubosidad='" + nubosidad + '\'' +
                ", visibilidad='" + visibilidad + '\'' +
                ", viento='" + viento + '\'' +
                ", estado_mar='" + estado_mar + '\'' +
                ", valida_desde='" + valida_desde + '\'' +
                ", valida_hasta='" + valida_hasta + '\'' +
                ", zona_horaria='" + zona_horaria + '\'' +
                ", situacion='" + situacion + '\'' +
                ", cobertura_nubosa='" + cobertura_nubosa + '\'' +
                ", visibilidad_apreciacion='" + visibilidad_apreciacion + '\'' +
                ", viento_apreciacion='" + viento_apreciacion + '\'' +
                '}';
    }
}
