package entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rivenditore")
public class Rivenditore extends PuntoEmissione{

    @Id
    @GeneratedValue
    @Column(name = "id_rivenditore")
    private UUID idRivenditore;

    @Column(name = "punto_vendita")
    private RivenditoreType puntoVendita;

    public Rivenditore() {

    }

    public Rivenditore(String nome, String indirizzo,  RivenditoreType puntoVendita) {
        super(nome, indirizzo);

        this.puntoVendita = puntoVendita;
    }

    public UUID getIdRivenditore() {
        return idRivenditore;
    }



    public RivenditoreType getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(RivenditoreType puntoVendita) {
        this.puntoVendita = puntoVendita;
    }
}
