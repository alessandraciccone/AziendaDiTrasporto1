package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.*;
import dao.VeicoloDAO;
import com.github.javafaker.Faker;
import java.util.Locale;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("azienda_di_trasporto");
    private static final Faker faker = new Faker(new Locale("it"));

    // Metodi helper per generare veicoli casuali
    private static Veicolo generaAutobusCasuale() {
        String marca = faker.options().option("Mercedes", "Volvo", "Iveco", "Scania", "MAN", "Renault", "Fiat", "Setra", "Van Hool", "Irisbus");
        int capienza = faker.number().numberBetween(40, 80);
        String stato = faker.options().option("OK", "KO", "IN_MANUTENZIONE", "FUORI_SERVIZIO");
        return new Veicolo(marca, capienza, stato, "AUTOBUS");
    }

    private static Veicolo generaTramCasuale() {
        String marca = faker.options().option("Siemens", "Bombardier", "Alstom", "AnsaldoBreda", "CAF", "Stadler", "Škoda");
        int capienza = faker.number().numberBetween(100, 250);
        String stato = faker.options().option("OK", "IN_MANUTENZIONE", "FUORI_SERVIZIO");
        return new Veicolo(marca, capienza, stato, "TRAM");
    }

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO(em);

        // Genera veicoli casuali
        Veicolo autobus = generaAutobusCasuale();
        Veicolo tram = generaTramCasuale();

        veicoloDAO.save(autobus);                 
        veicoloDAO.save(tram);

        System.out.println("✓ Salvati con JavaFaker:");
        System.out.println("  - Autobus: " + autobus.getMarca() + " (capienza: " + autobus.getCapienza() + ", stato: " + autobus.getStatocondizione()+ ")");
        System.out.println("  - Tram: " + tram.getMarca() + " (capienza: " + tram.getCapienza() + ", stato: " + tram.getStatocondizione()+ ")");
        
        // Esempio: genera multipli veicoli
        System.out.println("\n✓ Generazione multipla:");
        for (int i = 0; i < 5; i++) {
            Veicolo bus = generaAutobusCasuale();
            veicoloDAO.save(bus);
            System.out.println("  - Autobus " + (i+1) + ": " + bus.getMarca());
        }

        System.out.println("Done");
    }
}


