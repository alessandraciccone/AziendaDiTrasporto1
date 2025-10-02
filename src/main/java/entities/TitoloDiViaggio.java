package entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "titoli_di_viaggio")
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class TitoloDiViaggio {
    @Id
    @GeneratedValue
    private UUID idTitoloDiViaggio;
    @ManyToOne
    @JoinColumn(name ="id_punto_emissione")
    private PuntoEmissione puntoEmissione;
    private Double costo;
    private String tipo;
    private LocalDate dataEmissione;

    public TitoloDiViaggio(){}

    public TitoloDiViaggio(PuntoEmissione puntoEmissione, Double costo, String tipo, LocalDate dataEmissione) {
        this.puntoEmissione = puntoEmissione;
        this.costo = costo;
        this.tipo = tipo;
        this.dataEmissione = dataEmissione;
    }

    public UUID getIdTitoloDiViaggio() {
        return idTitoloDiViaggio;
    }


    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }

    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    @Override
    public String toString() {
        return "TitoloDiViaggio{" +
                "idTitoloDiViaggio=" + idTitoloDiViaggio +
                ", puntoEmissione=" + puntoEmissione +
                ", costo=" + costo +
                ", tipo='" + tipo + '\'' +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
