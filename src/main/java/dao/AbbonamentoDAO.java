package dao;

import entities.Abbonamento;
import entities.Tessera;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbbonamentoDAO {
    private final EntityManager entityManager;

    public AbbonamentoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void save(Abbonamento abbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(abbonamento);
            transaction.commit();
            System.out.println("Abbonamento salvato con successo: " + abbonamento.getTipo());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public Abbonamento findById(UUID id) {
        return entityManager.find(Abbonamento.class, id);
    }


    public List<Abbonamento> findByTessera(Tessera tessera) {
        TypedQuery<Abbonamento> query = entityManager.createQuery(
            "SELECT a FROM Abbonamento a WHERE a.tessera = :tessera", 
            Abbonamento.class
        );
        query.setParameter("tessera", tessera);
        return query.getResultList();
    }


    public List<Abbonamento> findByUtente(Utente utente) {
        TypedQuery<Abbonamento> query = entityManager.createQuery(
            "SELECT a FROM Abbonamento a WHERE a.utente = :utente", 
            Abbonamento.class
        );
        query.setParameter("utente", utente);
        return query.getResultList();
    }


    public List<Abbonamento> findAbbonamentiAttivi() {
        TypedQuery<Abbonamento> query = entityManager.createQuery(
            "SELECT a FROM Abbonamento a WHERE a.stato = 'ATTIVO' AND a.dataFine >= :oggi", 
            Abbonamento.class
        );
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }


    public List<Abbonamento> findAbbonamentiScaduti() {
        TypedQuery<Abbonamento> query = entityManager.createQuery(
            "SELECT a FROM Abbonamento a WHERE a.dataFine < :oggi", 
            Abbonamento.class
        );
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }


    public List<Abbonamento> findAbbonamentiInScadenza(int giorni) {
        LocalDate dataLimite = LocalDate.now().plusDays(giorni);
        TypedQuery<Abbonamento> query = entityManager.createQuery(
            "SELECT a FROM Abbonamento a WHERE a.stato = 'ATTIVO' AND a.dataFine BETWEEN :oggi AND :dataLimite", 
            Abbonamento.class
        );
        query.setParameter("oggi", LocalDate.now());
        query.setParameter("dataLimite", dataLimite);
        return query.getResultList();
    }


    public void update(Abbonamento abbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(abbonamento);
            transaction.commit();
            System.out.println("Abbonamento aggiornato: " + abbonamento.getTipo());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void delete(Abbonamento abbonamento) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(abbonamento) ? abbonamento : entityManager.merge(abbonamento));
            transaction.commit();
            System.out.println("Abbonamento eliminato: " + abbonamento.getTipo());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void verificaScadenzeAbbonamenti() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            List<Abbonamento> abbonamenti = entityManager.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.stato = 'ATTIVO' AND a.dataFine < :oggi", 
                Abbonamento.class
            )
            .setParameter("oggi", LocalDate.now())
            .getResultList();
            
            for (Abbonamento abbonamento : abbonamenti) {
                abbonamento.setStato(Abbonamento.StatoAbbonamento.SCADUTO);
                entityManager.merge(abbonamento);
            }
            
            transaction.commit();
            System.out.println("Verificati " + abbonamenti.size() + " abbonamenti scaduti");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

