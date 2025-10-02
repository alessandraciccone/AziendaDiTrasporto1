package dao;

import entities.AssegnazioneTratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class AssegnazioneTrattaDAO {

    private final EntityManager em;

    public AssegnazioneTrattaDAO(EntityManager em) {
        this.em=em;
    }

    public void save(AssegnazioneTratta assegnazione) {
        try {
            em.getTransaction().begin();
            em.persist(assegnazione);
            em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
        }
    }
    public AssegnazioneTratta findById (UUID id) {
        return em.find(AssegnazioneTratta.class, id);
    }

    public List<AssegnazioneTratta> findAll() {
        TypedQuery<AssegnazioneTratta> query =
                em.createQuery("SELECT a FROM AssegnazioneTratta a", AssegnazioneTratta.class);
        return query.getResultList();
    }

    // esempio: trova tutte le assegnazioeni di un certo veicolo


    public List<AssegnazioneTratta> findByVeicolo(UUID veicoloId) {
        TypedQuery<AssegnazioneTratta> query =
                em.createQuery("SELECT a FROM AssegnazioneTratta a WHERE a.veicolo.id = :veicoloId", AssegnazioneTratta.class);
                query.setParameter("veicoloId", veicoloId);
        return query.getResultList();
    }

    // esempio: calcola tempo medio effettivo di percorrenza di una tratta

    public Double tempoMedioByTratta(UUID trattaId) {
        TypedQuery<Double> query =
                em.createQuery("SELECT AVG(a.tempoEffettivo) FROM AssegnazioneTratta a WHERE a.tratta.id = :trattaId", Double.class);
        query.setParameter("trattaId", trattaId);
        return query.getSingleResult();
    }
}
