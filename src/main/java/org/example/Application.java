package org.example;

import com.github.javafaker.DateAndTime;
import dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.*;
import entities.DataGenerator;
import com.github.javafaker.Faker;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("azienda_di_trasporto");
    private static final Faker faker = new Faker(new Locale("it"));
    private static final Random random = new Random();


    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        DataGenerator generator = new DataGenerator(faker);
        VeicoloDAO veicoloDAO = new VeicoloDAO(em);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        ManutenzioneDAO manutenzioneDAO=new ManutenzioneDAO(em);
        AssegnazioneTrattaDAO assegnazioneDAO = new AssegnazioneTrattaDAO(em);
        UtenteDao ud = new UtenteDao(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        TitoloDiViaggioDao titoloDAO = new TitoloDiViaggioDao(em);


        try {
            try {

                // Genera veicoli casuali
                Veicolo autobus = generator.generaVeicoloCasuale();
                Veicolo tram = generator.generaVeicoloCasuale();

                veicoloDAO.save(autobus);
                veicoloDAO.save(tram);

                System.out.println(" Salvati con JavaFaker:");

                //genera manutenzione
List <Manutenzione> manutenzioni =new ArrayList<>();
manutenzioni.add(generator.generaManutezioneCasuale(autobus));
manutenzioni.add(generator.generaManutezioneCasuale(autobus));
manutenzioni.add(generator.generaManutezioneCasuale(tram));
manutenzioni.add(generator.generaManutezioneCasuale(tram));

for(Manutenzione m: manutenzioni) {
    manutenzioneDAO.save(m);
}

                System.out.println("Manutenzioni generate");
for(Manutenzione m: manutenzioni){
    System.out.println("Veicolo: "+ m.getVeicolo().getTipo() +
            ", Inizio: " + m.getDataInizio() +
            ", Fine: " + m.getDataFine());

}
//generatore tratta

                Tratta tratta = generator.generaTrattaCasuale();
                trattaDAO.save(tratta);
                System.out.println("✓ Tratta salvata: " + tratta.getZonaPartenza() + " → " + tratta.getCapolinea());


                //  Genera assegnazioni


                AssegnazioneTratta ass1 = generator.generaAssegnazioneCasuale(tratta, autobus);
                AssegnazioneTratta ass2 = generator.generaAssegnazioneCasuale(tratta, tram);
                assegnazioneDAO.save(ass1);
                assegnazioneDAO.save(ass2);

                System.out.println("✓ Assegnazioni salvate:");
                System.out.println("  - " + ass1);
                System.out.println("  - " + ass2);
                System.out.println("Done");

            } catch (Exception e) {
                System.err.println("Errore durante l'esecuzione: " + e.getMessage());
            }


            //generare punto emissione

            List<PuntoEmissione> puntiEmissioneList;
            PuntoEmissioneDAO pd = null;
            try {
                pd = new PuntoEmissioneDAO(em);

                int numeroDaGenerare = 10;
                puntiEmissioneList = new ArrayList<>();

                for (int i = 0; i < numeroDaGenerare; i++) {
                    PuntoEmissione puntoEmissione = generator.generaPuntoEmissione();
                    pd.save(puntoEmissione);
                    puntiEmissioneList.add(puntoEmissione);
                }
            } catch (Exception e) {
                System.err.println("Errore durante l'esecuzione: " + e.getMessage());
            }
            //genera utenti

            try {

                int numeroDiPersoneCreate = 20;


                // Prima genera punti emissione e veicoli (già fatto sopra)

                puntiEmissioneList = pd.findAll();
                List<Veicolo> veicoliList = veicoloDAO.findAll();

                for (int i = 0; i < numeroDiPersoneCreate; i++) {
                    Utente utente = generator.generaUtenteCasuale();
                    ud.save(utente);


                    // Salva anche le tessere dell'utente
                    for (Tessera tessera : utente.getTessere()) {
                        tesseraDAO.save(tessera);

                        // Genera e salva titoli di viaggio per ogni tessera
                        List<TitoloDiViaggio> titoli = generator.generaTitoloDiViaggioCasuale(
                                utente,
                                tessera,
                                puntiEmissioneList,
                                veicoliList
                        );

                        for (TitoloDiViaggio titolo : titoli) {
                            titoloDAO.save(titolo);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Errore durante l'esecuzione: " + e.getMessage());
            }
            System.out.println("✓ Generati utenti con tessere e titoli di viaggio");


            try {

                VeicoloDAO vd = new VeicoloDAO(em);

                System.out.println("     GENERAZIONE VEICOLI CASUALI");
                System.out.println("=====================================");

                // Genera e salva 15 veicoli casuali
                int numeroVeicoli = 15;
                System.out.println("📝 Generazione di " + numeroVeicoli + " veicoli casuali...\n");

                for (int i = 1; i <= numeroVeicoli; i++) {
                    Veicolo veicolo = generator.generaVeicoloCasuale();
                    vd.save(veicolo);
                }

                System.out.println("\n   ✓ Veicoli totali generati: " + numeroVeicoli);

                // Verifica che i veicoli siano stati salvati
                long count = vd.countAll();
                System.out.println("   📊 Veicoli nel database: " + count);

                // Mostra tutti i veicoli salvati
                System.out.println("\n📋 ELENCO VEICOLI NEL DATABASE:");
                System.out.println("=====================================");
                List<Veicolo> veicoli = vd.findAll();
                for (int i = 0; i < veicoli.size(); i++) {
                    Veicolo v = veicoli.get(i);
                    System.out.println((i + 1) + ". " + v.getTipo() +
                            " | Capienza: " + v.getCapienza() +
                            " | Stato: " + v.getStatoCondizione());
                }

            } catch (Exception e) {
                System.err.println("❌ Errore durante l'esecuzione: " + e.getMessage());
                e.printStackTrace();
            }


        } catch (Exception e) {
            System.err.println("Errore durante l'esecuzione: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            System.out.println("\n✅ Operazioni completate!");
        }


    }
}