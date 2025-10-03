package entities;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataGenerator {

    private final Faker faker;
    private final Random random;

    public DataGenerator(Faker faker) {
        this.faker = faker;
        this.random = new Random();
    }


    public  Veicolo generaVeicoloCasuale() {
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

    public  Utente generaUtenteCasuale() {
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
        Utente utente= new Utente(nome, cognome,dataDiNascita, isAdmin);


        List<Tessera> tessera = generaTesseraCasuale(utente);
        utente.setTessere(tessera);


        return utente;
    }

    // faker titolo di viaggio

    public  List<TitoloDiViaggio> generaTitoloDiViaggioCasuale(
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
    public  List <Tessera> generaTesseraCasuale(Utente utente){
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

    public  Tratta generaTrattaCasuale() {
        String zonaPartenza = faker.address().streetAddress();
        String capolinea = faker.address().streetAddress();
        int tempoPrevisto = faker.number().numberBetween(10, 60);
        return new Tratta(zonaPartenza, capolinea, tempoPrevisto);
    }

    public  AssegnazioneTratta generaAssegnazioneCasuale(Tratta tratta, Veicolo veicolo) {
        LocalDateTime inizio = LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30));
        int tempoEffettivo = tratta.getTempoPrevisto() + faker.number().numberBetween(-5, 10);
        LocalDateTime fine = inizio.plusMinutes(tempoEffettivo);
        return new AssegnazioneTratta(tratta, veicolo, inizio, fine, tempoEffettivo);
    }

    public  PuntoEmissione generaPuntoEmissione() {

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
