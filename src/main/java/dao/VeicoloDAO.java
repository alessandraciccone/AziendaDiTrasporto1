package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entities.Veicolo;

public class VeicoloDAO {
    private final EntityManager entityManager;

    public VeicoloDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Veicolo veicolo) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(veicolo);

        transaction.commit();
        System.out.println("Veicolo salvato");
    }

    public Veicolo findById(Long id) {
        return entityManager.find(Veicolo.class, id);
    }


}

