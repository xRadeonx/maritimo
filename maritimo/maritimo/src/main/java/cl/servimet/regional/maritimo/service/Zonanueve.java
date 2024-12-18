package cl.servimet.regional.maritimo.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.poiji.annotation.ExcelCellName;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Zonanueve {;

    @ExcelCellName("id")
    private Long id;

    @ExcelCellName("titulo")
    private String titulo;

    @ExcelCellName("inicio")
    private String inicio;

    @ExcelCellName("fin")
    private String fin;

    @ExcelCellName("campo")
    private String campo;

    @ExcelCellName("descripcion")
    private String descripcion;

    @ExcelCellName("cobertura_nubosa")
    private String cobertura_nubosa;

    @ExcelCellName("visibilidad")
    private String visibilidad;

    @ExcelCellName("viento")
    private String viento;

    @ExcelCellName("estado_mar")
    private String estado_mar;

    public Zonanueve(Long id, String titulo, String inicio, String fin, String campo, String descripcion, String cobertura_nubosa, String visibilidad, String viento, String estado_mar) {
        this.id = id;
        this.titulo = titulo;
        this.inicio = inicio;
        this.fin = fin;
        this.campo = campo;
        this.descripcion = descripcion;
        this.cobertura_nubosa = cobertura_nubosa;
        this.visibilidad = visibilidad;
        this.viento = viento;
        this.estado_mar = estado_mar;
    }

    public Zonanueve() {
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

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCobertura_nubosa() {
        return cobertura_nubosa;
    }

    public void setCobertura_nubosa(String cobertura_nubosa) {
        this.cobertura_nubosa = cobertura_nubosa;
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

    @Override
    public String toString() {
        return "Zonanueve{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", campo='" + campo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cobertura_nubosa='" + cobertura_nubosa + '\'' +
                ", visibilidad='" + visibilidad + '\'' +
                ", viento='" + viento + '\'' +
                ", estado_mar='" + estado_mar + '\'' +
                '}';
    }
}

