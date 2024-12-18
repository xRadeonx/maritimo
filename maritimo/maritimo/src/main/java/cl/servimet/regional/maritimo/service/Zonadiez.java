package cl.servimet.regional.maritimo.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poiji.annotation.ExcelCellName;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Zonadiez {

    @ExcelCellName("id")
    private String id;

    @ExcelCellName("temporal")
    private String temporal;

    @ExcelCellName("nombre")
    private String nombre;

    @ExcelCellName("inicio")
    private String inicio;

    @ExcelCellName("fin")
    private String fin;

    @ExcelCellName("condicion")
    private String condicion;

    @ExcelCellName("contenido")
    private String contenido;

    public Zonadiez(String id, String temporal, String nombre, String inicio, String fin, String condicion, String contenido) {
        this.id = id;
        this.temporal = temporal;
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.condicion = condicion;
        this.contenido = contenido;
    }

    public Zonadiez() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
        this.temporal = temporal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Zonadiez{" +
                "id=" + id +
                ", temporal='" + temporal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", condicion='" + condicion + '\'' +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}