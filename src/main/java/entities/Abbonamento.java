package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity

public class Abbonamento extends TitoloDiViaggio{

    private LocalDate DataInizio;
    private LocalDate DataFine;
    @ManyToOne
    @JoinColumn(name="fk_tessera")
    private Tessera tessera;
    public Abbonamento(){}
    public Abbonamento(PuntoEmissione puntoEmissione, Double costo, String tipo,
                       LocalDate dataEmissione, LocalDate dataInizio, LocalDate dataFine, Tessera tessera) {
        super(puntoEmissione, costo, tipo, dataEmissione);
        this.DataInizio = dataInizio;
        this.DataFine = dataFine;
        this.tessera = tessera;
    }

    public LocalDate getDataInizio() {
        return DataInizio;
    }

    public LocalDate getDataFine() {
        return DataFine;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setDataInizio(LocalDate dataInizio) {
        DataInizio = dataInizio;
    }

    public void setDataFine(LocalDate dataFine) {
        DataFine = dataFine;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "DataInizio=" + DataInizio +
                ", DataFine=" + DataFine +
                ", tessera=" + tessera +
                ", Id_Titolo_Di_Viaggio=" + Id_Titolo_Di_Viaggio +
                '}';
    }
}
