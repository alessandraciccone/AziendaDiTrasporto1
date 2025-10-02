package entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rivenditore")
public class Rivenditore extends PuntoEmissione{

    @Enumerated(EnumType.STRING)
    @Column(name = "punto_vendita")
    private RivenditoreType puntoVendita;

    public Rivenditore() {

    }

    public Rivenditore(String nome, String indirizzo,  RivenditoreType puntoVendita) {
        super(nome, indirizzo);

        this.puntoVendita = puntoVendita;
    }

    public RivenditoreType getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(RivenditoreType puntoVendita) {
        this.puntoVendita = puntoVendita;
    }
}
