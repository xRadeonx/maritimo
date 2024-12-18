package cl.servimet.regional.maritimo.service;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poiji.annotation.ExcelCellName;

@JsonPropertyOrder({ "id-zona", "zona", "situacion_sinoptica", "nubosidad", "visibilidad", "viento", "mar_oceanico", "mar_bahia" })
public class Regional {

    @ExcelCellName("id")
    @JsonProperty("id-zona")
    private Long id;

    @ExcelCellName("zona")
    private String zona;

    @ExcelCellName("situacion_sinoptica")
    private String situacion_sinoptica;

    @ExcelCellName("nubosidad")
    private String nubosidad;

    @ExcelCellName("visibilidad")
    private String visibilidad;

    @ExcelCellName("viento")
    private String viento;

    @ExcelCellName("mar_oceanico")
    private String mar_oceanico;

    @ExcelCellName("mar_bahia")
    private String mar_bahia;

    // Constructor completo
    public Regional(Long id, String zona, String situacion_sinoptica, String nubosidad, String visibilidad, String viento, String mar_oceanico, String mar_bahia) {
        this.id = id;
        this.zona = zona;
        this.situacion_sinoptica = situacion_sinoptica;
        this.nubosidad = nubosidad;
        this.visibilidad = visibilidad;
        this.viento = viento;
        this.mar_oceanico = mar_oceanico;
        this.mar_bahia = mar_bahia;
    }

    // Constructor vacío
    public Regional() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSituacion_sinoptica() {
        return situacion_sinoptica;
    }

    public void setSituacion_sinoptica(String situacion_sinoptica) {
        this.situacion_sinoptica = situacion_sinoptica;
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

    public String getMar_oceanico() {
        return mar_oceanico;
    }

    public void setMar_oceanico(String mar_oceanico) {
        this.mar_oceanico = mar_oceanico;
    }

    public String getMar_bahia() {
        return mar_bahia;
    }

    public void setMar_bahia(String mar_bahia) {
        this.mar_bahia = mar_bahia;
    }

    @Override
    public String toString() {
        return "Regional{" +
                "id=" + id +
                ", zona='" + zona + '\'' +
                ", situacion_sinoptica='" + situacion_sinoptica + '\'' +
                ", nubosidad='" + nubosidad + '\'' +
                ", visibilidad='" + visibilidad + '\'' +
                ", viento='" + viento + '\'' +
                ", mar_oceanico='" + mar_oceanico + '\'' +
                ", mar_bahia='" + mar_bahia + '\'' +
                '}';
    }
}
