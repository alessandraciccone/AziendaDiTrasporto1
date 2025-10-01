package dao;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbbonamentoDao {
    private EntityManager em;
public AbbonamentoDao(EntityManager em){
    this.em=em;
    }

    //salva
    public void save(Abbonamento abbonamento){
    em.getTransaction().begin();
    em.persist(abbonamento);
    em.getTransaction().commit();
    }
    //ricerca x id
    public Abbonamento findById(UUID id){
    return em.find(Abbonamento.class,id);
    }
    //ricerca tutti gli abbonamenti
    public List<Abbonamento> findAll(){
        TypedQuery<Abbonamento> query=em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class);
        return query.getResultList();
    }
    //ricerca x tessera
    public List<Abbonamento> findBytessera(UUID idTessera){
    TypedQuery<Abbonamento> query=em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera", Abbonamento.class);
    query.setParameter("idTessera", idTessera);
    return query.getResultList();
    }

    //ricerca abbonamenti attivi in una data specifica
    public List<Abbonamento> findAttiviInData(LocalDate data){
    TypedQuery<Abbonamento> query=em.createQuery("SELECT a FROM Abbonamento a WHERE :data BETWEEN a.DataInizio AND a.DataFine", Abbonamento.class);
    query.setParameter("data",data);
    return query.getResultList();
    }

    //aggiorna
    public void update(Abbonamento abbonamento){
    em.getTransaction().begin();
    em.merge(abbonamento);
    em.getTransaction().commit();
    }

    //elimina

    public void delete(Abbonamento abbonamento){
    em.getTransaction().begin();
    em.remove(em.contains(abbonamento)?abbonamento:em.merge(abbonamento));
    em.getTransaction().commit();
    }
}
