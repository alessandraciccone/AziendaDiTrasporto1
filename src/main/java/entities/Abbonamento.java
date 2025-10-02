package entities;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento extends TitoloDiViaggio {


    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    @ManyToOne
    @JoinColumn(name = "fk_tessera")
    private Tessera tessera;
    @Column(name="attivo")
 private boolean attivo;
    public Abbonamento() {
    }

    public Abbonamento(PuntoEmissione puntoEmissione, Double costo, String tipo,
                       LocalDate dataEmissione, LocalDate dataInizio, LocalDate dataFine, Tessera tessera) {
        super(puntoEmissione, costo, tipo, dataEmissione);
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tessera = tessera;
        this.attivo=true;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tessera=" + tessera +
                ", attivo=" + attivo +
                '}';
    }
}
