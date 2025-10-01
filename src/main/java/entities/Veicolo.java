package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "veicoli")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Veicolo {
    @Id
    @GeneratedValue
    private Long idVeicolo;
    private String Marca;
    private int capienza;
    private String statocondizione;

    public Veicolo(){
    }

    public Veicolo(String Marca, int capienza, String statocondizione) {
        this.Marca = Marca;
        this.capienza = capienza;
        this.statocondizione = statocondizione;
    }
    public Long getIdVeicolo() {
        return idVeicolo;
    }

    public String getMarca() {
        return Marca;
    }
    public void setMarca(String marca) {
        this.Marca = marca;
    }
    public int getCapienza() {
        return capienza;
    }
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }
    public String getStatocondizione() {
        return statocondizione;
    }
    public void setStatocondizione(String statocondizione) {
        this.statocondizione = statocondizione;
    }
}
