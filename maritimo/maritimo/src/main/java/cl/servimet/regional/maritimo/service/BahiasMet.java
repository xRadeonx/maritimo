package cl.servimet.regional.maritimo.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class BahiasMet {
        private List<Identificador> identificadores;
        private List<MeteoAM> meteoAMs;
        private List<MeteoPM> meteoPMs;
        private List<ProximosPronosticos> proximosPronosticos;

        public BahiasMet(List<Identificador> identificadores, List<MeteoAM> meteoAMs, List<MeteoPM> meteoPMs, List<ProximosPronosticos> proximosPronosticos) {
                this.identificadores = identificadores;
                this.meteoAMs = meteoAMs;
                this.meteoPMs = meteoPMs;
                this.proximosPronosticos = proximosPronosticos;
        }

        public BahiasMet() {
        }

        public List<Identificador> getIdentificadores() {
                return identificadores;
        }

        public void setIdentificadores(List<Identificador> identificadores) {
                this.identificadores = identificadores;
        }

        public List<MeteoAM> getMeteoAMs() {
                return meteoAMs;
        }

        public void setMeteoAMs(List<MeteoAM> meteoAMs) {
                this.meteoAMs = meteoAMs;
        }

        public List<MeteoPM> getMeteoPMs() {
                return meteoPMs;
        }

        public void setMeteoPMs(List<MeteoPM> meteoPMs) {
                this.meteoPMs = meteoPMs;
        }

        public List<ProximosPronosticos> getProximosPronosticos() {
                return proximosPronosticos;
        }

        public void setProximosPronosticos(List<ProximosPronosticos> proximosPronosticos) {
                this.proximosPronosticos = proximosPronosticos;
        }
}
