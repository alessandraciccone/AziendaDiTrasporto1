package entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name= "tratte")
public class Tratta {
    @Id
    @GeneratedValue
    @Column(name = "tratta_id")
    private UUID id;

    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;

    @Column(nullable = false)
    private String capolinea;

    @Column(name = "tempo_previsto", nullable=false)
    private int tempoPrevisto;          //in minuti

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<AssegnazioneTratta> assegnazioni = new ArrayList<>();


    // costruttori
    public Tratta() {}
    public Tratta(String zonaPartenza, String capolinea, int tempoPrevisto) {
        this.zonaPartenza =zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
    }

    // getter e setter
    public UUID getId() { return id; }

    public String getZonaPartenza() { return zonaPartenza; }
    public void setZonaPartenza(String zonaPartenza) {this.zonaPartenza= zonaPartenza; }

    public String getCapolinea() { return capolinea; }
    public void setCapolinea(String capolinea) {this.capolinea= capolinea; }

    public int getTempoPrevisto() {return tempoPrevisto; }
    public void setTempoPrevisto(int tempoPrevisto) { this.tempoPrevisto = tempoPrevisto; }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevisto=" + tempoPrevisto +
                '}';
    }
}
