package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "veicoli")
public class Veicolo {
    @Id
    @GeneratedValue
    private Long idVeicolo;
    private String Marca;
    private int capienza;
    private String statocondizione;
    private String tipoVeicolo; // AUTOBUS, TRAM, etc.

    public Veicolo(){
    }

    public Veicolo(String Marca, int capienza, String statocondizione, String tipoVeicolo) {
        this.Marca = Marca;
        this.capienza = capienza;
        this.statocondizione = statocondizione;
        this.tipoVeicolo = tipoVeicolo;
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
    public String getTipoVeicolo() {
        return tipoVeicolo;
    }
    public void setTipoVeicolo(String tipoVeicolo) {
        this.tipoVeicolo = tipoVeicolo;
    }
}
