package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "punto_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public class PuntoEmissione {

    @Id
    @GeneratedValue
    @Column(name = "id_punto_emissione")
    private UUID idPuntoEmissione;

    private String nome;
    private String indirizzo;

    @OneToMany(mappedBy = "puntoEmissione")
    private List<TitoloDiViaggio> titoliDiViaggio;

    public  PuntoEmissione(){}


    public PuntoEmissione(  String nome, String indirizzo) {

        this.nome = nome;
        this.indirizzo = indirizzo;

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

    public List<TitoloDiViaggio> getTitoliDiViaggio() {
        return titoliDiViaggio;
    }

    public void setTitoliDiViaggio(List<TitoloDiViaggio> titoliDiViaggio) {
        this.titoliDiViaggio = titoliDiViaggio;
    }
}
