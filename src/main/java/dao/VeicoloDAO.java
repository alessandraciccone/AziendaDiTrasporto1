package dao;

import entities.Veicolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
    public Veicolo findById(Long id) {
        return entityManager.find(Veicolo.class, id);
    }

    //  CERCA tutti i veicoli
    public List<Veicolo> findAll() {
        TypedQuery<Veicolo> query = entityManager.createQuery(
                "SELECT v FROM Veicolo v", Veicolo.class);
        return query.getResultList();
    }


    // CERCA veicoli per stato condizione
    public List<Veicolo> findByStatoCondizione(String statoCondizione) {
        TypedQuery<Veicolo> query = entityManager.createQuery(
                "SELECT v FROM Veicolo v WHERE v.statoCondizione = :stato", Veicolo.class);
        query.setParameter("stato", statoCondizione);
        return query.getResultList();
    }

    //  CERCA veicoli in BUONE condizioni
    public List<Veicolo> findVeicoliInBuoneCondizioni() {
        return findByStatoCondizione("BUONO");
    }

    //  CERCA veicoli che hanno bisogno di MANUTENZIONE
    public List<Veicolo> findVeicoliCheNecessitanoManutenzione() {
        return findByStatoCondizione("MANUTENZIONE");
    }

    //  AGGIORNA un veicolo esistente
    public void update(Veicolo veicolo) {
        entityManager.getTransaction().begin();  // Inizia la transazione
        entityManager.merge(veicolo);            // Aggiorna il veicolo
        entityManager.getTransaction().commit(); // Conferma le modifiche
        System.out.println("Veicolo aggiornato con successo!");
    }

    //  CAMBIA lo stato di un veicolo
    public void cambiaStato(Long veicoloId, String nuovoStato) {
        entityManager.getTransaction().begin();
        Veicolo veicolo = entityManager.find(Veicolo.class, veicoloId);
        if (veicolo != null) {
            veicolo.setStatoCondizione(nuovoStato);
            entityManager.merge(veicolo);
            System.out.println("Stato del veicolo aggiornato a: " + nuovoStato);
        }
        entityManager.getTransaction().commit();
    }

    //  METTI un veicolo in manutenzione
    public void mettiInManutenzione(Long veicoloId) {
        cambiaStato(veicoloId, "MANUTENZIONE");
    }

    //  RIPRISTINA un veicolo da manutenzione
    public void ripristinaDaManutenzione(Long veicoloId) {
        cambiaStato(veicoloId, "BUONO");
    }

    //  METTI un veicolo fuori servizio
    public void mettiFuoriServizio(Long veicoloId) {
        cambiaStato(veicoloId, "FUORI_SERVIZIO");
    }

    //  ELIMINA un veicolo
    public void delete(Veicolo veicolo) {
        entityManager.getTransaction().begin();  // Inizia la transazione
        entityManager.remove(veicolo);           // Elimina il veicolo
        entityManager.getTransaction().commit(); // Conferma le modifiche
        System.out.println("Veicolo eliminato con successo!");
    }

    //  ELIMINA un veicolo per ID
    public void deleteById(Long veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        if (veicolo != null) {
            delete(veicolo);
        }
    }

    //  CONTA quanti veicoli ci sono in totale
    public long countAll() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v", Long.class);
        return query.getSingleResult();
    }

    //  CONTA quanti veicoli sono in buone condizioni
    public long countVeicoliInBuoneCondizioni() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v WHERE v.statoCondizione = 'BUONO'", Long.class);
        return query.getSingleResult();
    }

    //  CONTA quanti veicoli sono in manutenzione
    public long countVeicoliInManutenzione() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Veicolo v WHERE v.statoCondizione = 'MANUTENZIONE'", Long.class);
        return query.getSingleResult();
    }

    //  VERIFICA se un veicolo esiste
    public boolean exists(Long veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        return veicolo != null;
    }

    //  VERIFICA se un veicolo è in buone condizioni
    public boolean isInBuoneCondizioni(Long veicoloId) {
        Veicolo veicolo = findById(veicoloId);
        return veicolo != null && "BUONO".equals(veicolo.getStatoCondizione());
    }
}