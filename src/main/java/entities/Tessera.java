package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue
    private UUID idTessera;

    @Column(unique = true, nullable = false)
    private String numeroTessera;

    private LocalDate dataEmissione;
    private LocalDate dataScadenza;

    @Enumerated(EnumType.STRING)
    private StatoTessera stato;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti = new ArrayList<>();

    // Enum per lo stato della tessera
    public enum StatoTessera {
        ATTIVA,
        SCADUTA,
        BLOCCATA,
        RINNOVATA
    }

    // Costruttori
    public Tessera() {}

    public Tessera(String numeroTessera, LocalDate dataEmissione, LocalDate dataScadenza, Utente utente) {
        this.numeroTessera = numeroTessera;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.utente = utente;
        this.stato = StatoTessera.ATTIVA;
    }

    // Getter e Setter
    public UUID getIdTessera() {
        return idTessera;
    }

    public String getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(String numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public StatoTessera getStato() {
        return stato;
    }

    public void setStato(StatoTessera stato) {
        this.stato = stato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    // Metodi di utilità
    public boolean isValida() {
        return stato == StatoTessera.ATTIVA &&
                LocalDate.now().isBefore(dataScadenza);
    }

    public void aggiungiAbbonamento(Abbonamento abbonamento) {
        abbonamenti.add(abbonamento);
        abbonamento.setTessera(this);
    }

    public void verificaScadenza() {
        if (LocalDate.now().isAfter(dataScadenza) && stato == StatoTessera.ATTIVA) {
            this.stato = StatoTessera.SCADUTA;
        }
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "idTessera=" + idTessera +
                ", numeroTessera='" + numeroTessera + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", stato=" + stato +
                ", utente=" + (utente != null ? utente.getNome() + " " + utente.getCognome() : "null") +
                ", numeroAbbonamenti=" + abbonamenti.size() +
                '}';
    }
}
