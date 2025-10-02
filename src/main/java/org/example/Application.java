package org.example;

import entities.Veicolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.github.javafaker.Faker;
import java.util.Locale;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("azienda_di_trasporto");
    private static final Faker faker = new Faker(new Locale("it"));



    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        em.close();
    }
}


