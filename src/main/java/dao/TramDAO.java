package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entities.Tram;

public class  TramDAO{
    private final EntityManager entityManager;

    public TramDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Tram tram) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(tram);

        transaction.commit();
        System.out.println("Tram salvato");
    }
}
