package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import entities.*;
import dao.VeicoloDAO;

import dao.TrattaDAO;
import dao.AssegnazioneTrattaDAO;

import com.github.javafaker.Faker;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;


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


//faker utente

    /*
    private static Utente generaUtenteCasuale() {
        String nome = faker.options().option("Michele", "Giulia", "Mattia", "Chiara", "Valentina", "Diego", "Giada");
    String cognome=faker.options().option("Rossi","Bianchi","Marino","Clemente","Di Marzio","Massimi","Sassi");
        List<LocalDate> dataDiNascitaPossibile = new ArrayList<>();
        dataDiNascitaPossibile.add(LocalDate.of(1993, 12, 14));
        dataDiNascitaPossibile.add(LocalDate.of(2000, 6, 1));
        dataDiNascitaPossibile.add(LocalDate.of(1996, 10, 4));
        dataDiNascitaPossibile.add(LocalDate.of(1957, 12, 6));
        dataDiNascitaPossibile.add(LocalDate.of(1980, 8, 24));
        dataDiNascitaPossibile.add(LocalDate.of(1986, 11, 26));
        dataDiNascitaPossibile.add(LocalDate.of(2005, 1, 23));


LocalDate dataDiNascita = faker.options().option(dataDiNascitaPossibile.toArray(new LocalDate[0]));
List <TitoloDiViaggio> titoloDiViaggio= generaTitoloDiViaggioCasuale();
List <Tessera> tessera= generaTesseraCasuale();
boolean isAdmin=faker.bool().bool();
return new Utente(nome, cognome,dataDiNascitaPossibile,titoloDiViaggiot,tessera,isAdmin);
    }
*/




    //faker titolo di viaggio

    /* private static List<TitoloDiViaggio> generaTitoloDiViaggioCasuale(
            Utente utente,
            Tessera tessera,
            List<PuntoEmissione> puntiEmissione,
            List<Veicolo> veicoli
    ) {
        List<TitoloDiViaggio> titoli = new ArrayList<>();
        int num = faker.number().numberBetween(1, 7);

        for (int i = 0; i < num; i++) {
            boolean eAbbonamento = faker.bool().bool();
            PuntoEmissione punto = faker.options().option(puntiEmissione.toArray(new PuntoEmissione[0]));
            LocalDate dataEmissione = LocalDate.now().minusDays(faker.number().numberBetween(1, 90));
            double costo = faker.number().randomDouble(1, 50, 300);

            if (eAbbonamento) {
                Abbonamento abb = new Abbonamento();
                abb.setTipo(faker.options().option(Abbonamento.TipoAbbonamento.values()));
                LocalDate inizio = dataEmissione.minusDays(faker.number().numberBetween(0, 60));
                LocalDate fine = inizio.plusDays(faker.number().numberBetween(30, 365));
                abb.setDataInizio(inizio);
                abb.setDataFine(fine);
                abb.setPrezzo(costo);
                abb.setStato(Abbonamento.StatoAbbonamento.ATTIVO);
                abb.setTessera(tessera);
                abb.setUtente(utente);
                titoli.add (abb);
            } else {
                Biglietto biglietto = new Biglietto();
                biglietto.setVeicolo(faker.options().option(veicoli.toArray(new Veicolo[0])));
                biglietto.setPuntoEmissione(punto);
                biglietto.setDataEmissione(dataEmissione);
                biglietto.setCosto(costo);
                biglietto.setTipo(faker.options().option("Ordinario", "Ridotto", "Turistico"));
                titoli.add(biglietto);
            }
        }

        return titoli;
    }


*/
    //faker Tratta e AssegnazioneTratta

    private static Tratta generaTrattaCasuale() {
        String zonaPartenza = faker.address().cityName();
        String capolinea = faker.address().cityName();
        int tempoPrevisto = faker.number().numberBetween(10, 60);
        return new Tratta(zonaPartenza, capolinea, tempoPrevisto);
    }

    private static AssegnazioneTratta generaAssegnazioneCasuale(Tratta tratta, Veicolo veicolo) {
        LocalDateTime inizio = LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30));
        int tempoEffettivo = tratta.getTempoPrevisto() + faker.number().numberBetween(-5, 10);
        LocalDateTime fine = inizio.plusMinutes(tempoEffettivo);
        return new AssegnazioneTratta(tratta, veicolo, inizio, fine, tempoEffettivo);
    }





    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO(em);


        TrattaDAO trattaDAO = new TrattaDAO(em);
        AssegnazioneTrattaDAO assegnazioneDAO = new AssegnazioneTrattaDAO(em);


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



        //  Genera tratta
        Tratta tratta = generaTrattaCasuale();
        trattaDAO.save(tratta);
        System.out.println("✓ Tratta salvata: " + tratta.getZonaPartenza() + " → " + tratta.getCapolinea());

        //  Genera assegnazioni
        AssegnazioneTratta ass1 = generaAssegnazioneCasuale(tratta, autobus);
        AssegnazioneTratta ass2 = generaAssegnazioneCasuale(tratta, tram);
        assegnazioneDAO.save(ass1);
        assegnazioneDAO.save(ass2);

        System.out.println("✓ Assegnazioni salvate:");
        System.out.println("  - " + ass1);
        System.out.println("  - " + ass2);


        System.out.println("Done");

        em.close();
    }
}


