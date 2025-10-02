package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id

    @GeneratedValue
    private UUID idUtente;

    private String Nome;
    private String Cognome;
    @Column(name = "data_di_nascita")
    private LocalDate DataDiNascita;

    @OneToMany(mappedBy = "utente")
    private List<Abbonamento> abbonamenti = new ArrayList<>();
    @OneToMany(mappedBy = "utente")
    private List<Tessera> tessere = new ArrayList<>();


    public Utente(){};
    public Utente(String nome, String cognome, LocalDate dataDiNascita, List<Abbonamento> abbonamenti, List<Tessera> tessere) {

        Nome = nome;
        Cognome = cognome;
        DataDiNascita = dataDiNascita;
        this.abbonamenti = abbonamenti;
        this.tessere = tessere;



    }
    public UUID getIdUtente() {
        return idUtente;
    }


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return DataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        DataDiNascita = dataDiNascita;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    public List<Tessera> getTessere() {
        return tessere;
    }

    public void setTessere(List<Tessera> tessere) {
        this.tessere = tessere;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "idUtente=" + idUtente +
                ", Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", DataDiNascita=" + DataDiNascita +
                ", abbonamenti=" + abbonamenti +
                ", tessere=" + tessere +
                '}';
    }
}
