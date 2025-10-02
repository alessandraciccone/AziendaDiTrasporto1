package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entities.*;
import dao.VeicoloDAO;

import com.github.javafaker.Faker;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;



//faker utente


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
return new Utente(nome, cognome,dataDiNascita,titoloDiViaggio,tessera,isAdmin);
    }





    //faker titolo di viaggio

     private static List<TitoloDiViaggio> generaTitoloDiViaggioCasuale(
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
                LocalDate inizio = dataEmissione.minusDays(faker.number().numberBetween(0, 60));
                LocalDate fine = inizio.plusDays(faker.number().numberBetween(30, 365));
                abb.setDataInizio(inizio);
                abb.setDataFine(fine);
                abb.setTipo(faker.options().option("Mensile","Settimanale"));
                abb.setTessera(tessera);
                titoli.add (abb);
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


    public static PuntoEmissione generaPuntoEmissione(){

        String nome = faker.company().name();
        String indirizzo = faker.address().fullAddress();

        List<TitoloDiViaggio> titoliDiViaggioVuoti = Collections.emptyList();
        if(random.nextBoolean()) {
            DistributoreStato[] stati = DistributoreStato.values();
            DistributoreStato statoCasuale = stati[random.nextInt(stati.length)];
            return new Distributore(nome, indirizzo, titoliDiViaggioVuoti, statoCasuale);
        }else{
            RivenditoreType[] tipi = RivenditoreType.values();
            RivenditoreType tipoCasuale = tipi[random.nextInt(tipi.length)];

            return new Rivenditore(nome, indirizzo, titoliDiViaggioVuoti, tipoCasuale);
        }
    }



}

