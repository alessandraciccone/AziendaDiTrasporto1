package dao;

import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.UUID;

public class TrattaDAO {

    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }


    // Metodo save

    public void save(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(tratta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    // Metodo FIND BY ID

    public Tratta findById(UUID id) {
        return em.find(Tratta.class, id);
    }

    // Metodo FIND ALL

    public List<Tratta> findAll() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    // Metodo UPDATE

    public void update(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tratta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    // Metodo DELETE

    public void delete(UUID id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tratta tratta = em.find(Tratta.class, id);
            if (tratta != null) {
                em.remove(tratta);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
