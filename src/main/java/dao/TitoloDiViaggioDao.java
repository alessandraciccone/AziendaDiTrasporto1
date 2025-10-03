package dao;
import entities.Abbonamento;
import entities.Biglietto;
import entities.TitoloDiViaggio;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TitoloDiViaggioDao {

    private EntityManager em;

    public TitoloDiViaggioDao(EntityManager em) {
        this.em = em;

    }

    //save

    public void save(TitoloDiViaggio titoloDiViaggio) {
        em.getTransaction().begin();
        em.persist(titoloDiViaggio);
        em.getTransaction().commit();
    }



    //ricerca biglietto associati a un veicolo(ricerca la vidimazione del biglietto)
    public List<Biglietto> findByVeicoilo(UUID idVeicolo){
        TypedQuery<Biglietto> query=em.createQuery( "SELECT b FROM Biglietto b WHERE b.veicolo.idVeicolo = :idVeicolo",Biglietto.class);
        query.setParameter("idVeicolo", idVeicolo);
        return query.getResultList();

    }

    //ricerca biglietto x data di emiussioine

    public List <Biglietto> findByDataEmissione(LocalDate data){
        TypedQuery<Biglietto> query =em.createQuery("SELECT b FROM Biglietto b WHERE b.dataEmissione = :data", Biglietto.class);
        query.setParameter("data",data);
        return query.getResultList();
    }

    //ricerca x id tutti i titoli di viaggio

    public TitoloDiViaggio findById(UUID id) {
        TitoloDiViaggio titolo = em.find(TitoloDiViaggio.class, id);
        if (titolo == null) {
            throw new NotFoundException("Titolo di viaggio con ID " + id + " non trovato.");
        }
        return titolo;
    }


    //ricerca tutti i titoli di viaggio
    public List<TitoloDiViaggio> findAllTitoli(){
        TypedQuery<TitoloDiViaggio> query=em.createQuery("SELECT a FROM TitoloDiViaggio t", TitoloDiViaggio.class);
        return query.getResultList();
    }
    //ricerca x tessera
    public List<Abbonamento> findBytessera(UUID idTessera){
        TypedQuery<Abbonamento> query=em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.idTessera = :idTessera", Abbonamento.class);
        query.setParameter("idTessera", idTessera);
        return query.getResultList();
    }

    //ricerca abbonamenti attivi in una data specifica
    public List<Abbonamento> findAttiviInData(LocalDate data){
        TypedQuery<Abbonamento> query=em.createQuery("SELECT a FROM Abbonamento a WHERE :data BETWEEN a.dataInizio AND a.dataFine", Abbonamento.class);
        query.setParameter("data",data);
        return query.getResultList();
    }


    //aggiorna
    public void update(TitoloDiViaggio titoloDiViaggio){
        em.getTransaction().begin();
        em.merge(titoloDiViaggio);
        em.getTransaction().commit();
    }

    //cancella
    public void delete(TitoloDiViaggio titoloDiViaggio){
        em.getTransaction().begin();
        em.remove(em.contains(titoloDiViaggio)? titoloDiViaggio:em.merge(titoloDiViaggio));
        em.getTransaction().commit();
    }
    // ricerca di tutti gli abbonamenti tramite id utente
    public List<Abbonamento> findByUtenteId(UUID idUtente){
        TypedQuery query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.utente.idUtente = :idUtente", Abbonamento.class);
        query.setParameter("idUtente", idUtente);
        return query.getResultList();
    }
}
