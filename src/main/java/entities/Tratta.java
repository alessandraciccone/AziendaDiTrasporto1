package entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name= "tratte")
public class Tratta {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String zonaPartenza;

    @Column(nullable = false)
    private String capolinea;

    @Column(nullable=false)
    private int tempoPrevisto;          //in minuti

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
