package entities;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "distributori")
public class Distributore extends PuntoEmissione{


    @Enumerated(EnumType.STRING)
    private DistributoreStato stato;


    public Distributore() {



    }

    public Distributore(String nome, String indirizzo, List<TitoloDiViaggio> titoloDiViaggio, DistributoreStato stato) {
        super(nome, indirizzo, titoloDiViaggio);
        this.stato = stato;
    }





    public DistributoreStato getStato() {
        return stato;
    }

    public void setStato(DistributoreStato stato) {
        this.stato = stato;
    }


    @Override
    public String toString() {
        return "Distributore{" +
                "stato=" + stato +
                '}';
    }
}
