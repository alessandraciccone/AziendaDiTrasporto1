package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Biglietto extends TitoloDiViaggio {

    @ManyToOne
    @JoinColumn(name = "fk_veicolo")
    private Veicolo veicolo;

    public Biglietto() {}

    public Biglietto(PuntoEmissione puntoEmissione, Double costo, String tipo, LocalDate dataEmissione, Veicolo veicolo) {
        super(puntoEmissione, costo, tipo, dataEmissione);
        this.veicolo = veicolo;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "idTitoloDiViaggio=" + getId_Titolo_Di_Viaggio() +
                ", veicolo=" + veicolo +
                ", costo=" + getCosto() +
                ", tipo='" + getTipo() + '\'' +
                ", dataEmissione=" + getDataEmissione() +
                '}';
    }
}