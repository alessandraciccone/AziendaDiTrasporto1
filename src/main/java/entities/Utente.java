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

    private String nome;
    private String cognome;
    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @OneToMany(mappedBy = "utente")
    private List<Abbonamento> abbonamenti = new ArrayList<>();
    @OneToMany(mappedBy = "utente")
    private List<Tessera> tessere = new ArrayList<>();


    public Utente(){};
    public Utente(String nome, String cognome, LocalDate dataDiNascita, List<Abbonamento> abbonamenti, List<Tessera> tessere) {

        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.abbonamenti = abbonamenti;
        this.tessere = tessere;



    }
    public UUID getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
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


}
