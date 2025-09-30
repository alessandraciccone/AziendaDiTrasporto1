package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Biglietto extends  TitoloDiViaggio{

    @Id
    @GeneratedValue
    UUID idBiglietto;

    private Double prezzo;
    public Biglietto(){};

    public Biglietto(UUID idBiglietto, Double prezzo) {
        this.idBiglietto = idBiglietto;
        this.prezzo = prezzo;
    }

    public Biglietto(UUID idTitoloDiViaggio, PuntoEmissione puntoEmissione, Double costo, String tipo, LocalDate dataEmissione, UUID idBiglietto, Double prezzo) {
        super(idTitoloDiViaggio, puntoEmissione, costo, tipo, dataEmissione);
        this.idBiglietto = idBiglietto;
        this.prezzo = prezzo;
    }


    public UUID getIdBiglietto() {
        return idBiglietto;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "idBiglietto=" + idBiglietto +
                ", prezzo=" + prezzo +
                '}';
    }
}
