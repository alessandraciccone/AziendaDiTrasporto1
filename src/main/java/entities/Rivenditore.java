package entities;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "rivenditori")
public class Rivenditore extends PuntoEmissione{



    @Column(name = "punto_vendita")
    @Enumerated(EnumType.STRING)
    private RivenditoreType puntoVendita;

    public Rivenditore() {

    }


    public Rivenditore(String nome, String indirizzo, List<TitoloDiViaggio> titoloDiViaggio, RivenditoreType puntoVendita) {
        super(nome, indirizzo, titoloDiViaggio);
        this.puntoVendita = puntoVendita;
    }

    public RivenditoreType getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(RivenditoreType puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "puntoVendita=" + puntoVendita +
                '}';
    }
}
