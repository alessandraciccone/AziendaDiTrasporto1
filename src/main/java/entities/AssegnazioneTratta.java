package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assegnazioni_tratte")
public class AssegnazioneTratta {

    @Id
    @GeneratedValue
    private UUID id;

    // @ManyToOne
    // @JoinColumn(name = "tratta_id", nullable = false)
     private Tratta tratta;

    // Relazione con Mezzo (tram/autobus)
    // @ManyToOne
    // @JoinColumn(name = "mezzo_id", nullable = false)
    // private Mezzo mezzo;

    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int tempoEffettivo; // minuti effettivi

    // costruttori
    public AssegnazioneTratta() {}

    public AssegnazioneTratta(Tratta tratta, LocalDateTime dataInizio, int tempoEffettivo) {
        this.tratta = tratta;
        this.dataInizio = dataInizio;
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

    @Override
    public String toString() {
        return "AssegnazioneTratta{" +
                "id=" + id +
                ", tratta=" + (tratta != null ? tratta.getId() : null) +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}
