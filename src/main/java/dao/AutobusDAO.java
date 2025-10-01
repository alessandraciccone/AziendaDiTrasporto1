package dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entities.Autobus;

public class AutobusDAO{
    private final EntityManager entityManager;

    public AutobusDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Autobus autobus) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(autobus);

        transaction.commit();
        System.out.println("Autobus salvato");
    }
}
