package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import entities.*;
import dao.VeicoloDAO;
import com.github.javafaker.Faker;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("azienda_di_trasporto");
    private static final Faker faker = new Faker(new Locale("it"));

    // Metodo per generare un veicolo casuale
    private static Veicolo generaVeicoloCasuale() {
        // Genera tipo casuale (AUTOBUS o TRAM)
        VeicoloType tipo = faker.options().option(VeicoloType.values());

        // Genera capienza basata sul tipo
        int capienza;
        if (tipo == VeicoloType.AUTOBUS) {
            capienza = faker.number().numberBetween(30, 80);
        } else { // TRAM
            capienza = faker.number().numberBetween(100, 250);
        }

        // Genera stato casuale
        StatoCondizione statoCondizione = faker.options().option(StatoCondizione.values());

        return new Veicolo(tipo, capienza, statoCondizione);
    }

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO(em);

        System.out.println("🚌 GENERAZIONE VEICOLI CASUALI 🚋");
        System.out.println("================================\n");

        // Genera e salva 15 veicoli casuali
        System.out.println("Generazione di 15 veicoli casuali:");
        for (int i = 1; i <= 15; i++) {
            Veicolo veicolo = generaVeicoloCasuale();
            veicoloDAO.save(veicolo);

            System.out.println("   ✓ Veicolo " + i + ": " +
                    "ID=" + veicolo.getIdVeicolo() +
                    ", Tipo=" + veicolo.getTipo() +
                    ", Capienza=" + veicolo.getCapienza() +
                    ", Stato=" + veicolo.getStatoCondizione());
        }

        System.out.println("\n📊 STATISTICHE:");
        System.out.println("   • Totale veicoli creati: 15");

        em.close();
        emf.close();
        System.out.println("\n✅ Operazioni completate!");
    }

    //faker utente



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



}


