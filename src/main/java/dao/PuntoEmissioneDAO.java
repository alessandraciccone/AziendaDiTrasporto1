package dao;

import entities.Distributore;
import entities.DistributoreStato;
import entities.PuntoEmissione;
import entities.Rivenditore;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

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





    // trova tutti i punti rivenditori
    public List<Rivenditore> findAllRivenditori(){
        TypedQuery<Rivenditore> query = em.createQuery("SELECT r FROM Rivenditori r", Rivenditore.class);
        return query.getResultList();
    }

    // trova tutti i distributori
    public List<Distributore> findAllDistributori(){
        TypedQuery<Distributore> query = em.createQuery("SELECT d FROM Distributori d", Distributore.class);
        return query.getResultList();
    }

    // trova i distributori attivi
    public List<Distributore> findAllActive(){
        TypedQuery<Distributore> query = em.createQuery("SELECT d FROM Distributori d WHERE d.stato = :attivo", Distributore.class);
        query.setParameter("stato",DistributoreStato.ATTIVO);
        return query.getResultList();
    }

    // aggiorna distributore
    public void updateDistributore(Distributore distributore){
        em.getTransaction().begin();
        em.merge(distributore);
        em.getTransaction().commit();

    }

    //agiornamento 2
    public void update(DistributoreStato oldStato,DistributoreStato newStato){
        EntityTransaction transaction = em.getTransaction();
    transaction.begin();
        Query query = em.createQuery("UPDATE Distributore d SET d.stato = :new WHERE d.stato = :old");
        query.setParameter("new", newStato);
        query.setParameter("old",oldStato);

        int numModified = query.executeUpdate();

        transaction.commit();

        System.out.println(numModified + "elementi aggiornati");


    }
}


