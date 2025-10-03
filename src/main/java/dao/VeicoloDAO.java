package dao;

import entities.Veicolo;
import entities.StatoCondizione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class VeicoloDAO {

    private final EntityManager entityManager;

    // Costruttore - riceve l'EntityManager per fare le operazioni sul database
    public VeicoloDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // SALVA un nuovo veicolo nel database
    public void save(Veicolo veicolo) {
        entityManager.getTransaction().begin();  // Inizia la transazione
        entityManager.persist(veicolo);          // Salva il veicolo
        entityManager.getTransaction().commit(); // Conferma le modifiche
        System.out.println("Veicolo salvato con successo!");
    }

    // CERCA un veicolo per ID
    public Veicolo findById(UUID id) {
        return entityManager.find(Veicolo.class, id);
    }

    // CERCA tutti i veicoli
    public List<Veicolo> findAll() {
        TypedQuery<Veicolo> query = entityManager.createQuery(
            "SELECT v FROM Veicolo v", 
            Veicolo.class
        );
        return query.getResultList();
    }

    //  CERCA veicoli per stato condizione
    public List<Veicolo> findByStatoCondizione(StatoCondizione statoCondizione) {
        TypedQuery<Veicolo> query = entityManager.createQuery(
                "SELECT v FROM Veicolo v WHERE v.statoCondizione = :stato", Veicolo.class);
        query.setParameter("stato", statoCondizione);
        return query.getResultList();
    }

    // CERCA veicoli in BUONE condizioni
    public List<Veicolo> findVeicoliInBuoneCondizioni() {
        return findByStatoCondizione(StatoCondizione.BUONO);
    }

    // CERCA veicoli che hanno bisogno di MANUTENZIONE
    public List<Veicolo> findVeicoliCheNecessitanoManutenzione() {
        return findByStatoCondizione(StatoCondizione.MANUTENZIONE);
    }

    // AGGIORNA un veicolo esistente
    public void update(Veicolo veicolo) {
        entityManager.getTransaction().begin();  // Inizia la transazione
        entityManager.merge(veicolo);            // Aggiorna il veicolo
        entityManager.getTransaction().commit(); // Conferma le modifiche
        System.out.println("Veicolo aggiornato con successo!");
    }

    // CAMBIA lo stato di un veicolo
    public void cambiaStato(UUID veicoloId, StatoCondizione nuovoStato) {
        entityManager.getTransaction().begin();
        Veicolo veicolo = entityManager.find(Veicolo.class, veicoloId);
        if (veicolo != null) {
            veicolo.setStatoCondizione(nuovoStato);
            entityManager.merge(veicolo);
            System.out.println("Stato del veicolo aggiornato a: " + nuovoStato);
        }
        entityManager.getTransaction().commit();
    }

    // METTI un veicolo in manutenzione
    public void mettiInManutenzione(UUID veicoloId) {
        cambiaStato(veicoloId, StatoCondizione.MANUTENZIONE);
    }

    // RIPRISTINA un veicolo da manutenzione
    public void ripristinaDaManutenzione(UUID veicoloId) {
        cambiaStato(veicoloId, StatoCondizione.BUONO);
    }

    // METTI un veicolo fuori servizio
    public void mettiFuoriServizio(UUID veicoloId) {
        cambiaStato(veicoloId, StatoCondizione.FUORI_SERVIZIO);
    }

    // ELIMINA un veicolo
    public void delete(Veicolo veicolo) {
        entityManager.getTransaction().begin();  // Inizia la transazione
        entityManager.remove(veicolo);           // Elimina il veicolo
        entityManager.getTransaction().commit(); // Conferma le modifiche
        System.out.println("Veicolo eliminato con successo!");
    }

    // ELIMINA un veicolo per ID
    public void deleteById(UUID veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        if (veicolo != null) {
            delete(veicolo);
        }
    }

    // CONTA quanti veicoli ci sono in totale
    public long countAll() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v", Long.class);
        return query.getSingleResult();
    }

    // CONTA quanti veicoli sono in buone condizioni
    public long countVeicoliInBuoneCondizioni() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v WHERE v.statoCondizione = :stato", Long.class);
        query.setParameter("stato", StatoCondizione.BUONO);
        return query.getSingleResult();
    }

    // CONTA quanti veicoli sono in manutenzione
    public long countVeicoliInManutenzione() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v WHERE v.statoCondizione = :stato", Long.class);
        query.setParameter("stato", StatoCondizione.MANUTENZIONE);
        return query.getSingleResult();
    }

    // VERIFICA se un veicolo esiste
    public boolean exists(UUID veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        return veicolo != null;
    }

    // VERIFICA se un veicolo è in buone condizioni
    public boolean isInBuoneCondizioni(UUID veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        return veicolo != null && StatoCondizione.BUONO.equals(veicolo.getStatoCondizione());
    }
}