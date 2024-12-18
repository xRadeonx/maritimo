package cl.servimet.regional.maritimo.service;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("Identificador")
public class Identificador {
    @ExcelCellName("idCentro")
    private Long idCentro;

    @ExcelCellName("nombreCentro")
    private String nombreCentro;

    @ExcelCellName("idBahia")
    private Long idBahia;

    @ExcelCellName("nombreBahia")
    private String nombreBahia;

    public Identificador(Long idCentro, String nombreCentro, Long idBahia, String nombreBahia) {
        this.idCentro = idCentro;
        this.nombreCentro = nombreCentro;
        this.idBahia = idBahia;
        this.nombreBahia = nombreBahia;
    }

    public Identificador() {
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public Long getIdBahia() {
        return idBahia;
    }

    public void setIdBahia(Long idBahia) {
        this.idBahia = idBahia;
    }

    public String getNombreBahia() {
        return nombreBahia;
    }

    public void setNombreBahia(String nombreBahia) {
        this.nombreBahia = nombreBahia;
    }

    @Override
    public String toString() {
        return "Identificador{" +
                "idCentro=" + idCentro +
                ", nombreCentro='" + nombreCentro + '\'' +
                ", idBahia=" + idBahia +
                ", nombreBahia='" + nombreBahia + '\'' +
                '}';
    }
}
