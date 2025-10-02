package entities;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento {

    public static CriteriaBuilder tipoAbbonamento;
    @Id
    @GeneratedValue
    private UUID idAbbonamento;

    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    private LocalDate dataInizio;
    private LocalDate dataFine;

    private Double prezzo;

    @Enumerated(EnumType.STRING)
    private StatoAbbonamento stato;

    @ManyToOne
    @JoinColumn(name = "id_tessera", nullable = false)
    private Tessera tessera;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    // Enum per il tipo di abbonamento
    public enum TipoAbbonamento {
        MENSILE,
        TRIMESTRALE,
        SEMESTRALE,
        ANNUALE
    }

    // Enum per lo stato dell'abbonamento
    public enum StatoAbbonamento {
        ATTIVO,
        SCADUTO,
        SOSPESO
    }

    // Costruttori
    public Abbonamento() {}

    public Abbonamento(TipoAbbonamento tipo, LocalDate dataInizio, LocalDate dataFine, Double prezzo, Tessera tessera, Utente utente) {
        this.tipo = tipo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.prezzo = prezzo;
        this.tessera = tessera;
        this.utente = utente;
        this.stato = StatoAbbonamento.ATTIVO;
    }



    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
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

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public StatoAbbonamento getStato() {
        return stato;
    }

    public void setStato(StatoAbbonamento stato) {
        this.stato = stato;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    // Metodi di utilità
    public boolean isValido() {
        LocalDate oggi = LocalDate.now();
        return stato == StatoAbbonamento.ATTIVO &&
                !oggi.isBefore(dataInizio) &&
                !oggi.isAfter(dataFine);
    }

    public void verificaScadenza() {
        if (LocalDate.now().isAfter(dataFine) && stato == StatoAbbonamento.ATTIVO) {
            this.stato = StatoAbbonamento.SCADUTO;
        }
    }

    public long giorniRimanenti() {
        return LocalDate.now().datesUntil(dataFine).count();
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "idAbbonamento=" + idAbbonamento +
                ", tipo=" + tipo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", prezzo=" + prezzo +
                ", stato=" + stato +
                ", tessera=" + (tessera != null ? tessera.getNumeroTessera() : "null") +
                ", utente=" + (utente != null ? utente.getNome() + " " + utente.getCognome() : "null") +
                '}';
    }
}
