package cl.servimet.regional.maritimo.controller;

public class ExcelConfig {
        private String rutaArchivo;
        private String[] nombresHojas;
        private boolean trimCellValue;

        public ExcelConfig(String rutaArchivo, String[] nombresHojas, boolean trimCellValue) {
            this.rutaArchivo = rutaArchivo;
            this.nombresHojas = nombresHojas;
            this.trimCellValue = trimCellValue;
        }

        public String getRutaArchivo() {
            return rutaArchivo;
        }

        public String[] getNombresHojas() {
            return nombresHojas;
        }

        public boolean isTrimCellValue() {
            return trimCellValue;
        }

}
