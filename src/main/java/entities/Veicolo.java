package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "veicoli")


public class Veicolo {
    @Id
    @GeneratedValue
    @Column(name = "id_veicolo")
    private UUID idVeicolo;
    @Enumerated(EnumType.STRING)
    private VeicoloType tipo;
    private int capienza;
    @Column(name = "stato_condizione")
    @Enumerated(EnumType.STRING)
    private VeicoloStato statoCondizione;

    @OneToMany(mappedBy = "veicolo")
    private List<Manutenzione> manutenzioneList;

    @OneToMany(mappedBy = "veicolo")
    private List<AssegnazioneTratta> assegnazioneTrattaList;

    public Veicolo(){
    }

    public Veicolo(VeicoloType tipo, int capienza, VeicoloStato statoCondizione) {
        this.tipo = tipo;
        this.capienza = capienza;
        this.statoCondizione = statoCondizione;
    }
    public UUID getIdVeicolo() {
        return idVeicolo;
    }



    public VeicoloType getTipo() {
        return tipo;
    }

    public void setTipo(VeicoloType tipo) {
        this.tipo = tipo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public VeicoloStato getStatoCondizione() {
        return statoCondizione;
    }

    public void setStatoCondizione(VeicoloStato statoCondizione) {
        this.statoCondizione = statoCondizione;
    }

    public List<Manutenzione> getManutenzioneList() {
        return manutenzioneList;
    }

    public void setManutenzioneList(List<Manutenzione> manutenzioneList) {
        this.manutenzioneList = manutenzioneList;
    }

    public List<AssegnazioneTratta> getAssegnazioneTrattaList() {
        return assegnazioneTrattaList;
    }

    public void setAssegnazioneTrattaList(List<AssegnazioneTratta> assegnazioneTrattaList) {
        this.assegnazioneTrattaList = assegnazioneTrattaList;
    }

    @Override
    public String toString() {
        return "Veicolo{" +
                "idVeicolo=" + idVeicolo +
                ", tipo=" + tipo +
                ", capienza=" + capienza +
                ", statoCondizione=" + statoCondizione +
                ", manutenzioneList=" + manutenzioneList +
                ", assegnazioneTrattaList=" + assegnazioneTrattaList +
                '}';
    }
}
