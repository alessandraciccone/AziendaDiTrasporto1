package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "autobus")
@Inheritance(strategy = InheritanceType.JOINED)

public class Autobus extends Veicolo {

    public Autobus(){ super();
    }

    public Autobus(String Marca, int capienza, String statocondizione) {
        super(Marca, capienza, statocondizione);
    }
}
