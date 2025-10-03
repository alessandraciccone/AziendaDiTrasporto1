package dao;

import entities.Manutenzione;
import entities.Veicolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {
    private EntityManager em;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
    }

    // Salva manutenzione
    public void save(Manutenzione manutenzione) {
        em.getTransaction().begin();
        em.persist(manutenzione);
        em.getTransaction().commit();
    }

    // Cerca per ID
    public Manutenzione findById(UUID id) {
        return em.find(Manutenzione.class, id);
    }

    // Cerca tutte le manutenzioni
    public List<Manutenzione> findAll() {
        TypedQuery<Manutenzione> query = em.createQuery("SELECT m FROM Manutenzione m", Manutenzione.class);
        return query.getResultList();
    }

    // Cerca manutenzioni per veicolo
    public List<Manutenzione> findByVeicolo(Veicolo veicolo) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.veicolo = :veicolo ORDER BY m.dataInizio DESC",
                Manutenzione.class);
        query.setParameter("veicolo", veicolo);
        return query.getResultList();
    }

    // Cerca manutenzioni per ID veicolo
    public List<Manutenzione> findByVeicoloId(UUID veicoloId) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.veicolo.idVeicolo = :veicoloId ORDER BY m.dataInizio DESC",
                Manutenzione.class);
        query.setParameter("veicoloId", veicoloId);
        return query.getResultList();
    }

    // Cerca manutenzioni per data inizio
    public List<Manutenzione> findByDataInizio(LocalDate dataInizio) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio = :dataInizio",
                Manutenzione.class);
        query.setParameter("dataInizio", dataInizio);
        return query.getResultList();
    }

    // Cerca manutenzioni per data fine
    public List<Manutenzione> findByDataFine(LocalDate dataFine) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine = :dataFine",
                Manutenzione.class);
        query.setParameter("dataFine", dataFine);
        return query.getResultList();
    }

    // Cerca manutenzioni in corso (data inizio <= oggi e data fine >= oggi o null)
    public List<Manutenzione> findManutenzioniInCorso() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio <= :oggi AND (m.dataFine IS NULL OR m.dataFine >= :oggi)",
                Manutenzione.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Cerca manutenzioni terminate
    public List<Manutenzione> findManutenzioniTerminate() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine IS NOT NULL AND m.dataFine < :oggi",
                Manutenzione.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Cerca manutenzioni programmate (data inizio > oggi)
    public List<Manutenzione> findManutenzioniProgrammate() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio > :oggi",
                Manutenzione.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Cerca manutenzioni per range di date inizio
    public List<Manutenzione> findByDataInizioRange(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio BETWEEN :dataInizio AND :dataFine ORDER BY m.dataInizio",
                Manutenzione.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getResultList();
    }

    // Cerca manutenzioni per range di date fine
    public List<Manutenzione> findByDataFineRange(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataFine BETWEEN :dataInizio AND :dataFine ORDER BY m.dataFine",
                Manutenzione.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getResultList();
    }

    // Cerca manutenzioni che includono una data specifica
    public List<Manutenzione> findManutenzioniInData(LocalDate data) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.dataInizio <= :data AND (m.dataFine IS NULL OR m.dataFine >= :data)",
                Manutenzione.class);
        query.setParameter("data", data);
        return query.getResultList();
    }

    // Cerca ultima manutenzione di un veicolo
    public Manutenzione findUltimaManutenzioneByVeicolo(Veicolo veicolo) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.veicolo = :veicolo ORDER BY m.dataInizio DESC",
                Manutenzione.class);
        query.setParameter("veicolo", veicolo);
        query.setMaxResults(1);
        List<Manutenzione> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Cerca ultima manutenzione di un veicolo per ID
    public Manutenzione findUltimaManutenzioneByVeicoloId(UUID veicoloId) {
        TypedQuery<Manutenzione> query = em.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.veicolo.idVeicolo = :veicoloId ORDER BY m.dataInizio DESC",
                Manutenzione.class);
        query.setParameter("veicoloId", veicoloId);
        query.setMaxResults(1);
        List<Manutenzione> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Cerca veicoli attualmente in manutenzione
    public List<Veicolo> findVeicoliInManutenzione() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Veicolo> query = em.createQuery(
                "SELECT DISTINCT m.veicolo FROM Manutenzione m WHERE m.dataInizio <= :oggi AND (m.dataFine IS NULL OR m.dataFine >= :oggi)",
                Veicolo.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Cerca veicoli disponibili (non in manutenzione)
    public List<Veicolo> findVeicoliDisponibili() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Veicolo> query = em.createQuery(
                "SELECT v FROM Veicolo v WHERE v.idVeicolo NOT IN (" +
                        "SELECT DISTINCT m.veicolo.idVeicolo FROM Manutenzione m WHERE m.dataInizio <= :oggi AND (m.dataFine IS NULL OR m.dataFine >= :oggi))",
                Veicolo.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }

    // Aggiorna manutenzione
    public void update(Manutenzione manutenzione) {
        em.getTransaction().begin();
        em.merge(manutenzione);
        em.getTransaction().commit();
    }

    // Termina manutenzione (imposta data fine)
    public void terminaManutenzione(UUID manutenzioneId, LocalDate dataFine) {
        em.getTransaction().begin();
        Manutenzione manutenzione = em.find(Manutenzione.class, manutenzioneId);
        if (manutenzione != null) {
            manutenzione.setDataFine(dataFine);
            em.merge(manutenzione);
        }
        em.getTransaction().commit();
    }

    // Termina manutenzione oggi
    public void terminaManutenzioneOggi(UUID manutenzioneId) {
        terminaManutenzione(manutenzioneId, LocalDate.now());
    }

    // Elimina manutenzione
    public void delete(Manutenzione manutenzione) {
        em.getTransaction().begin();
        em.remove(em.contains(manutenzione) ? manutenzione : em.merge(manutenzione));
        em.getTransaction().commit();
    }

    // Elimina manutenzione per ID
    public void deleteById(UUID manutenzioneId) {
        Manutenzione manutenzione = findById(manutenzioneId);
        if (manutenzione != null) {
            delete(manutenzione);
        }
    }

    // Conta manutenzioni per veicolo
    public long countByVeicolo(Veicolo veicolo) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.veicolo = :veicolo",
                Long.class);
        query.setParameter("veicolo", veicolo);
        return query.getSingleResult();
    }

    // Conta manutenzioni per ID veicolo
    public long countByVeicoloId(UUID veicoloId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.veicolo.idVeicolo = :veicoloId",
                Long.class);
        query.setParameter("veicoloId", veicoloId);
        return query.getSingleResult();
    }

    // Conta manutenzioni in corso
    public long countManutenzioniInCorso() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.dataInizio <= :oggi AND (m.dataFine IS NULL OR m.dataFine >= :oggi)",
                Long.class);
        query.setParameter("oggi", oggi);
        return query.getSingleResult();
    }

    // Conta manutenzioni per periodo
    public long countByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.dataInizio BETWEEN :dataInizio AND :dataFine",
                Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getSingleResult();
    }

    // Conta tutte le manutenzioni
    public long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(m) FROM Manutenzione m", Long.class);
        return query.getSingleResult();
    }

    // Verifica se un veicolo è in manutenzione
    public boolean isVeicoloInManutenzione(UUID veicoloId) {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Manutenzione m WHERE m.veicolo.idVeicolo = :veicoloId AND m.dataInizio <= :oggi AND (m.dataFine IS NULL OR m.dataFine >= :oggi)",
                Long.class);
        query.setParameter("veicoloId", veicoloId);
        query.setParameter("oggi", oggi);
        return query.getSingleResult() > 0;
    }

    // Calcola durata media manutenzioni per veicolo
    public double calcolaDurataMediaManutenzioni(UUID veicoloId) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(DATEDIFF(m.dataFine, m.dataInizio)) FROM Manutenzione m WHERE m.veicolo.idVeicolo = :veicoloId AND m.dataFine IS NOT NULL",
                Double.class);
        query.setParameter("veicoloId", veicoloId);
        Double result = query.getSingleResult();
        return result != null ? result : 0.0;
    }
}