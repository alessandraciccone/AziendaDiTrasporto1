package dao;

import entities.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BigliettoDao {

    private EntityManager em;
    public BigliettoDao(EntityManager em){
        this.em=em;
    }

    //salva
    public void save(Biglietto biglietto){
        em.getTransaction().begin();
        em.persist(biglietto);
        em.getTransaction().commit();
    }

    //riceca x id
    public Biglietto findById (UUID id){
        return em.find(Biglietto.class, id);
    }

    //ricerca tutti i biglietti
    public List<Biglietto> findAll(){
        TypedQuery<Biglietto> query=em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }
    //ricerca biglietto associati a un veicolo(ricerca la vidimazione del biglietto)
    public List<Biglietto> findByVeicoilo(UUID idVeicolo){
        TypedQuery<Biglietto> query=em.createQuery( "SELECT b FROM Biglietto b WHERE b.veicolo.id = :idVeicolo",Biglietto.class);
        query.setParameter("idVeicolo", idVeicolo);
        return query.getResultList();

    }

    //ricerca biglietto x data di emiussioine

    public List <Biglietto> findByDataEmissione(LocalDate data){
        TypedQuery<Biglietto> query =em.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione = :data", Biglietto.class);
        query.setParameter("data",data);
        return query.getResultList();
    }

    //aggiorna
     public void update(Biglietto biglietto){
        em.getTransaction().begin();
        em.merge(biglietto);
        em.getTransaction().commit();
     }

     //cancella
    public void delete(Biglietto biglietto){
        em.getTransaction().begin();
        em.remove(em.contains(biglietto)? biglietto:em.merge(biglietto));
        em.getTransaction().commit();
    }
}
