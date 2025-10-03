package entities;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietto extends TitoloDiViaggio {

    @ManyToOne
    @JoinColumn(name = "veicolo_id")
    private Veicolo veicolo;
    private LocalDate dataVidimazione;


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

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }
    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "veicolo=" + veicolo +
                '}';
    }
}