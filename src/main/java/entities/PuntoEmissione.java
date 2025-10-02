package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "punti_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public class PuntoEmissione {

    @Id
    @GeneratedValue
    @Column(name = "id_punto_emissione")
    private UUID idPuntoEmissione;

    private String nome;
    private String indirizzo;

    @OneToMany(mappedBy = "puntoEmissione")
    private List<TitoloDiViaggio> titoloDiViaggio;


    public  PuntoEmissione(){}


    public PuntoEmissione( String nome, String indirizzo, List<TitoloDiViaggio> titoloDiViaggio) {

        this.nome = nome;
        this.indirizzo = indirizzo;
        this.titoloDiViaggio = titoloDiViaggio;
    }

    public UUID getIdPuntoEmissione() {
        return idPuntoEmissione;
    }






    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }


    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "idPuntoEmissione=" + idPuntoEmissione +
                ", nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", titoloDiViaggio=" + titoloDiViaggio +
                '}';
    }
}
