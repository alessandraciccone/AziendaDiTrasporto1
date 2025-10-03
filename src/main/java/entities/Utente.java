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
private List<Tessera> tessere = new ArrayList<>();
@Column(name="is_admin")
private boolean isAdmin;

    public Utente() {}
    
    public Utente(String nome, String cognome, LocalDate dataDiNascita, boolean isAdmin) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.isAdmin=isAdmin;
    }

    public Utente(String nome, String cognome, LocalDate dataDiNascita, List<TitoloDiViaggio> titoloDiViaggio, List<Tessera> tessera, boolean isAdmin) {
    }

    public Utente(String nome, String cognome, LocalDate dataDiNascita) {
    }

    public UUID getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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

    public List<Tessera> getTessere() {
        return tessere;
    }

    public void setTessere(List<Tessera> tessere) {
        this.tessere = tessere;
    }

    public List<Abbonamento> getAllAbbonamenti() {
        List<Abbonamento> tuttiAbbonamenti = new ArrayList<>();
        for (Tessera tessera : tessere) {
            tuttiAbbonamenti.addAll(tessera.getAbbonamenti());
        }
        return tuttiAbbonamenti;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "idUtente=" + idUtente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", tessere=" + tessere +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public void setIsAdmin(boolean isAdmin) {
    }
}
