package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class UtenteDao {
    private EntityManager em;
    public UtenteDao(EntityManager em){
        this.em=em;

    }

    //salva
    public void save( Utente utente ){
em.getTransaction().begin();
em.persist(utente);
em.getTransaction().commit();
    }

    //cerca per id
    public Utente findById(UUID id){
        return em.find(Utente.class, id);
    }

//cerca tutti gli utenti
public List<Utente> findAll(){
        TypedQuery<Utente> query= em.createQuery("SELECT u FROM Utente u",Utente.class);
        return query.getResultList();
    }

    //cerca per cognone
    public List<Utente> findByCognome(String cognome){
        TypedQuery<Utente> query=em.createQuery("SELECT u FROM Utente u WHERE u.Cognome = :cognome", Utente.class);
        query.setParameter("cognome", cognome);
        return query.getResultList();

    }

    //cerca x tessera
    public List<Utente> findWithTessere(){
        TypedQuery <Utente> query =em.createQuery("SELECT DISTINCT u FROM Utente u JOIN u.tessere t",Utente.class);
        return query.getResultList();
    }

    // cerca x abbonamento
    public List <Utente> findWithAbbonamento(){
        TypedQuery<Utente> query=em.createQuery("SELECT DISTINCT u FROM Utente u JOIN u.abbonamenti a", Utente.class);
        return query.getResultList();
        }

        //aggiorna utente
    public void update(Utente utente){
        em.getTransaction().begin();
        em.merge(utente);
        em.getTransaction().commit();
    }

    //elimina utente
    public void delete(Utente utente){
        em.getTransaction().begin();
        em.remove(em.contains(utente) ? utente : em.merge(utente));
        em.getTransaction().commit();
    }
}
