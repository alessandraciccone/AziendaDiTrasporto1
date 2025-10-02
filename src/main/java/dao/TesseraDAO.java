package dao;

import entities.Tessera;
import entities.Tessera.StatoTessera;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TesseraDAO {
    private EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    // Salva tessera
    public void save(Tessera tessera) {
        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();
    }

    // Cerca per ID
    public Tessera findById(UUID id) {
        return em.find(Tessera.class, id);
    }

    // Cerca per numero tessera
    public Tessera findByNumeroTessera(String numeroTessera) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                Tessera.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getSingleResult();
    }

    // Cerca tutte le tessere
    public List<Tessera> findAll() {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t", Tessera.class);
        return query.getResultList();
    }

    // Cerca tessere per stato
    public List<Tessera> findByStato(StatoTessera stato) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.stato = :stato",
                Tessera.class);
        query.setParameter("stato", stato);
        return query.getResultList();
    }

    // Cerca tessere per utente
    public List<Tessera> findByUtente(Utente utente) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.utente = :utente",
                Tessera.class);
        query.setParameter("utente", utente);
        return query.getResultList();
    }

    // Cerca tessere per ID utente
    public List<Tessera> findByUtenteId(UUID utenteId) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.utente.idUtente = :utenteId",
                Tessera.class);
        query.setParameter("utenteId", utenteId);
        return query.getResultList();
    }

    // Cerca tessere attive
    public List<Tessera> findTessereAttive() {
        return findByStato(StatoTessera.ATTIVA);
    }

    // Cerca tessere scadute
    public List<Tessera> findTessereScadute() {
        return findByStato(StatoTessera.SCADUTA);
    }

    // Cerca tessere bloccate
    public List<Tessera> findTessereBloccate() {
        return findByStato(StatoTessera.BLOCCATA);
    }

    // Cerca tessere in scadenza (entro X giorni)
    public List<Tessera> findTessereInScadenza(int giorni) {
        LocalDate dataLimite = LocalDate.now().plusDays(giorni);
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.stato = :stato AND t.dataScadenza <= :dataLimite",
                Tessera.class);
        query.setParameter("stato", StatoTessera.ATTIVA);
        query.setParameter("dataLimite", dataLimite);
        return query.getResultList();
    }

    // Cerca tessere valide (attive e non scadute)
    public List<Tessera> findTessereValide() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.stato = :stato AND t.dataScadenza > :oggi",
                Tessera.class);
        query.setParameter("stato", StatoTessera.ATTIVA);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Cerca tessere con abbonamenti
    public List<Tessera> findTessereConAbbonamenti() {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT DISTINCT t FROM Tessera t JOIN t.abbonamenti a",
                Tessera.class);
        return query.getResultList();
    }

    // Cerca tessere per data emissione
    public List<Tessera> findByDataEmissione(LocalDate dataEmissione) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataEmissione = :dataEmissione",
                Tessera.class);
        query.setParameter("dataEmissione", dataEmissione);
        return query.getResultList();
    }

    // Cerca tessere per range di date di emissione
    public List<Tessera> findByDataEmissioneRange(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine",
                Tessera.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getResultList();
    }

    // Aggiorna tessera
    public void update(Tessera tessera) {
        em.getTransaction().begin();
        em.merge(tessera);
        em.getTransaction().commit();
    }

    // Aggiorna stato tessera
    public void updateStato(UUID tesseraId, StatoTessera nuovoStato) {
        em.getTransaction().begin();
        Tessera tessera = em.find(Tessera.class, tesseraId);
        if (tessera != null) {
            tessera.setStato(nuovoStato);
            em.merge(tessera);
        }
        em.getTransaction().commit();
    }

    // Verifica e aggiorna tessere scadute
    public void verificaTessereScadute() {
        List<Tessera> tessereAttive = findTessereAttive();
        LocalDate oggi = LocalDate.now();

        em.getTransaction().begin();
        for (Tessera tessera : tessereAttive) {
            if (tessera.getDataScadenza().isBefore(oggi)) {
                tessera.setStato(StatoTessera.SCADUTA);
                em.merge(tessera);
            }
        }
        em.getTransaction().commit();
    }

    // Blocca tessera
    public void bloccaTessera(UUID tesseraId) {
        updateStato(tesseraId, StatoTessera.BLOCCATA);
    }

    // Sblocca tessera (rimette attiva se non scaduta)
    public void sbloccaTessera(UUID tesseraId) {
        Tessera tessera = findById(tesseraId);
        if (tessera != null) {
            if (tessera.getDataScadenza().isAfter(LocalDate.now())) {
                updateStato(tesseraId, StatoTessera.ATTIVA);
            } else {
                updateStato(tesseraId, StatoTessera.SCADUTA);
            }
        }
    }

    // Elimina tessera
    public void delete(Tessera tessera) {
        em.getTransaction().begin();
        em.remove(em.contains(tessera) ? tessera : em.merge(tessera));
        em.getTransaction().commit();
    }

    // Elimina tessera per ID
    public void deleteById(UUID tesseraId) {
        Tessera tessera = findById(tesseraId);
        if (tessera != null) {
            delete(tessera);
        }
    }

    // Conta tessere per stato
    public long countByStato(StatoTessera stato) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM Tessera t WHERE t.stato = :stato",
                Long.class);
        query.setParameter("stato", stato);
        return query.getSingleResult();
    }

    // Conta tessere totali
    public long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Tessera t", Long.class);
        return query.getSingleResult();
    }
}