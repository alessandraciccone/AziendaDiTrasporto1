package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import entities.Veicolo;


@Entity
@Table(name = "assegnazioni_tratte")
public class AssegnazioneTratta {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tratta_id", nullable = false)
    private Tratta tratta;

    // Relazione con Veicolo (tram/autobus)
    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Veicolo veicolo;

    @Column(name = "data_inizio")
    private LocalDateTime dataInizio;
    @Column(name = "data_fine")
    private LocalDateTime dataFine;
    @Column(name = "tempo_effettivo")
    private int tempoEffettivo; // minuti effettivi

    // costruttori
    public AssegnazioneTratta() {}

    public AssegnazioneTratta(Tratta tratta, Veicolo veicolo, LocalDateTime dataInizio, LocalDateTime dataFine, int tempoEffettivo) {
        this.tratta = tratta;
        this.veicolo = veicolo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tempoEffettivo = tempoEffettivo;
    }


    // getter e setter
    public UUID getId() { return id; }

    public Tratta getTratta() { return tratta; }
    public void setTratta(Tratta tratta) { this.tratta = tratta; }

    public LocalDateTime getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDateTime dataInizio) { this.dataInizio = dataInizio; }

    public LocalDateTime getDataFine() { return dataFine; }
    public void setDataFine(LocalDateTime dataFine) { this.dataFine = dataFine; }

    public int getTempoEffettivo() { return tempoEffettivo; }
    public void setTempoEffettivo(int tempoEffettivo) { this.tempoEffettivo = tempoEffettivo; }

    public Veicolo getVeicolo() { return veicolo;
     }
    public void setVeicolo(Veicolo veicolo) { this.veicolo = veicolo; }


    @Override
    public String toString() {
        return "AssegnazioneTratta{" +
                "id=" + id +
                ", tratta=" + (tratta != null ? tratta.getId() : null) +
                ", veicolo=" + (veicolo != null ? veicolo.getIdVeicolo() : null) +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }

}
