package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tram")
@Inheritance(strategy = InheritanceType.JOINED)

public class Tram extends Veicolo {

    public Tram(){ super();}

    public Tram(String Marca, int capienza, String statocondizione) {
        super(Marca, capienza, statocondizione);
    }
}
