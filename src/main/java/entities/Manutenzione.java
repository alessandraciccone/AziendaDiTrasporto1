package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "manutenzioni")
public class Manutenzione {
    @Id
    @GeneratedValue
    @Column(name = "id_manutenzione")
    private UUID idManutenzione;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "id_veicolo", nullable = false)
    private Veicolo veicolo;


    public Manutenzione(){}


    public Manutenzione(LocalDate dataInizio, LocalDate dataFine, Veicolo veicolo){
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.veicolo = veicolo;
    }

    public UUID getIdManutenzione() {
        return idManutenzione;
    }



    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    @Override
    public String toString() {
        return "Manutenzione{" +
                "idManutenzione=" + idManutenzione +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", veicolo=" + veicolo +
                '}';
    }
}
