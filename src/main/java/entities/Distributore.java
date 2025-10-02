package entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "distributore")
public class Distributore extends PuntoEmissione{

    @Enumerated(EnumType.STRING)
    private DistributoreStato stato;



    public Distributore() {



    }

    public Distributore(String nome, String indirizzo,  DistributoreStato stato) {
        super(nome, indirizzo);

        this.stato = stato;
    }

    public DistributoreStato getStato() {
        return stato;
    }

    public void setStato(DistributoreStato stato) {
        this.stato = stato;
    }
}
