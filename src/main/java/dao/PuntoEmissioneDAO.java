package dao;

import entities.PuntoEmissione;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PuntoEmissioneDAO {

    private final EntityManager em;


    public PuntoEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    //  Metodo SALVA
    public void save(PuntoEmissione puntoEmissione) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(puntoEmissione);
            transaction.commit();
            System.out.println("PuntoEmissione " + puntoEmissione.getNome() + " salvato con successo!");
        } catch (NotFoundException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    //  Metodo CERCA PER ID
    public PuntoEmissione findById(java.util.UUID id) {

        return em.find(PuntoEmissione.class, id);
    }

    // Metodo ELIMINA
    public void delete(java.util.UUID id) {

        PuntoEmissione found = em.find(PuntoEmissione.class, id);

        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();

                em.remove(found);

                transaction.commit();
                System.out.println("PuntoEmissione con ID " + id + " eliminato con successo.");
            } catch (NotFoundException e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.err.println("Errore durante l'eliminazione del PuntoEmissione con ID " + id + ": " + e.getMessage());
            }
        } else {
            System.out.println("PuntoEmissione con ID " + id + " non trovato. Nessuna eliminazione eseguita.");
        }
    }


}

