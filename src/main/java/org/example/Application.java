package org.example;

import com.github.javafaker.DateAndTime;
import dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.*;

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
        VeicoloDAO veicoloDAO = new VeicoloDAO(em);


        TrattaDAO trattaDAO = new TrattaDAO(em);
        AssegnazioneTrattaDAO assegnazioneDAO = new AssegnazioneTrattaDAO(em);


        // Genera veicoli casuali
        Veicolo autobus = generaVeicoloCasuale();
        Veicolo tram = generaVeicoloCasuale();

        veicoloDAO.save(autobus);
        veicoloDAO.save(tram);

        System.out.println("✓ Salvati con JavaFaker:");


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


        //generare punto emissione
        PuntoEmissioneDAO pd = new PuntoEmissioneDAO(em);

        int numeroDaGenerare =10;

        for (int i = 0; i < numeroDaGenerare; i++) {
            PuntoEmissione puntoEmissione= generaPuntoEmissione();
            pd.save(puntoEmissione);
        }

        //genera utenti

        UtenteDao ud = new UtenteDao(em);
        int numeroDiPersoneCreate = 20;

        for (int i = 0; i < numeroDiPersoneCreate; i++) {
            Utente utente = generaUtenteCasuale();
            ud.save(utente);

        }


        try {

            VeicoloDAO vd = new VeicoloDAO(em);

            System.out.println("     GENERAZIONE VEICOLI CASUALI");
            System.out.println("=====================================");

            // Genera e salva 15 veicoli casuali
            int numeroVeicoli = 15;
            System.out.println("📝 Generazione di " + numeroVeicoli + " veicoli casuali...\n");

            for (int i = 1; i <= numeroVeicoli; i++) {
                Veicolo veicolo = generaVeicoloCasuale();
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

    private static Veicolo generaVeicoloCasuale() {
        // Genera tipo casuale (AUTOBUS o TRAM)
        VeicoloType tipo = faker.options().option(VeicoloType.values());

        // Genera capienza basata sul tipo
        int capienza;
        if (tipo == VeicoloType.AUTOBUS) {
            // Gli autobus hanno capienza tra 30 e 80 passeggeri
            capienza = faker.number().numberBetween(30, 80);
        } else {
            // I tram hanno capienza maggiore tra 100 e 250 passeggeri
            capienza = faker.number().numberBetween(100, 250);
        }

        // Genera stato casuale tra BUONO, MANUTENZIONE, FUORI_SERVIZIO
        StatoCondizione statoCondizione = faker.options().option(StatoCondizione.values());

        // Crea e restituisce il veicolo
        return new Veicolo(tipo, capienza, statoCondizione);
    }

    // faker utente

    private static Utente generaUtenteCasuale() {
        String nome = faker.options().option("Michele", "Giulia", "Mattia", "Chiara", "Valentina", "Diego", "Giada");
        String cognome = faker.options().option("Rossi", "Bianchi", "Marino", "Clemente", "Di Marzio", "Massimi",
                "Sassi");
        List<LocalDate> dataDiNascitaPossibile = new ArrayList<>();
        dataDiNascitaPossibile.add(LocalDate.of(1993, 12, 14));
        dataDiNascitaPossibile.add(LocalDate.of(2000, 6, 1));
        dataDiNascitaPossibile.add(LocalDate.of(1996, 10, 4));
        dataDiNascitaPossibile.add(LocalDate.of(1957, 12, 6));
        dataDiNascitaPossibile.add(LocalDate.of(1980, 8, 24));
        dataDiNascitaPossibile.add(LocalDate.of(1986, 11, 26));
        dataDiNascitaPossibile.add(LocalDate.of(2005, 1, 23));

        LocalDate dataDiNascita = faker.options().option(dataDiNascitaPossibile.toArray(new LocalDate[0]));
        boolean isAdmin = faker.bool().bool();
        Utente utente= new Utente(nome, cognome,dataDiNascita);
        utente.setIsAdmin(isAdmin);

        List<Tessera> tessera = generaTesseraCasuale(utente);
        utente.setTessere(tessera);


        return utente;
    }

    // faker titolo di viaggio

    private static List<TitoloDiViaggio> generaTitoloDiViaggioCasuale(
            Utente utente,
            Tessera tessera,
            List<PuntoEmissione> puntiEmissione,
            List<Veicolo> veicoli) {
        List<TitoloDiViaggio> titoli = new ArrayList<>();
        int num = faker.number().numberBetween(1, 7);

        for (int i = 0; i < num; i++) {
            boolean eAbbonamento = faker.bool().bool();
            PuntoEmissione punto = faker.options().option(puntiEmissione.toArray(new PuntoEmissione[0]));
            LocalDate dataEmissione = LocalDate.now().minusDays(faker.number().numberBetween(1, 90));
            double costo = faker.number().randomDouble(1, 50, 300);

            if (eAbbonamento) {
                Abbonamento abb = new Abbonamento();
                LocalDate inizio = dataEmissione.minusDays(faker.number().numberBetween(0, 60));
                LocalDate fine = inizio.plusDays(faker.number().numberBetween(30, 365));
                abb.setDataInizio(inizio);
                abb.setDataFine(fine);
                abb.setTipo(faker.options().option("Mensile", "Settimanale"));
                abb.setTessera(tessera);
                titoli.add(abb);
            } else {
                Biglietto biglietto = new Biglietto();
                biglietto.setVeicolo(faker.options().option(veicoli.toArray(new Veicolo[0])));
                biglietto.setPuntoEmissione(punto);
                biglietto.setDataEmissione(dataEmissione);
                titoli.add(biglietto);
            }
        }

        return titoli;
    }


    //faker generaTesseraCasuale
        private static List <Tessera> generaTesseraCasuale(Utente utente){
        List <Tessera> tessere= new ArrayList<>();
        int nTessere= faker.number().numberBetween(1,7);
        for (int i=0; i< nTessere; i++){
            String nTessera= UUID.randomUUID().toString();
            LocalDate dataEmissione= faker.date()
            .past(365*7, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate dataScadenza = dataEmissione.plusYears(faker.number().numberBetween(1,7));
            Tessera tessera = new Tessera(nTessera, dataEmissione, dataScadenza,utente);
            tessera.setStato(faker.options().option(Tessera.StatoTessera.values()));
            tessere.add(tessera);
        }
            return tessere;
        }


    // faker Tratta e AssegnazioneTratta

    private static Tratta generaTrattaCasuale() {
        String zonaPartenza = faker.address().streetAddress();
        String capolinea = faker.address().streetAddress();
        int tempoPrevisto = faker.number().numberBetween(10, 60);
        return new Tratta(zonaPartenza, capolinea, tempoPrevisto);
    }

    private static AssegnazioneTratta generaAssegnazioneCasuale(Tratta tratta, Veicolo veicolo) {
        LocalDateTime inizio = LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30));
        int tempoEffettivo = tratta.getTempoPrevisto() + faker.number().numberBetween(-5, 10);
        LocalDateTime fine = inizio.plusMinutes(tempoEffettivo);
        return new AssegnazioneTratta(tratta, veicolo, inizio, fine, tempoEffettivo);
    }

    public static PuntoEmissione generaPuntoEmissione() {

        String nome = faker.company().name();
        String indirizzo = faker.address().fullAddress();

        List<TitoloDiViaggio> titoliDiViaggioVuoti = Collections.emptyList();
        if (random.nextBoolean()) {
            DistributoreStato[] stati = DistributoreStato.values();
            DistributoreStato statoCasuale = stati[random.nextInt(stati.length)];
            return new Distributore(nome, indirizzo, titoliDiViaggioVuoti, statoCasuale);
        } else {
            RivenditoreType[] tipi = RivenditoreType.values();
            RivenditoreType tipoCasuale = tipi[random.nextInt(tipi.length)];

            return new Rivenditore(nome, indirizzo, titoliDiViaggioVuoti, tipoCasuale);
        }
    }


}