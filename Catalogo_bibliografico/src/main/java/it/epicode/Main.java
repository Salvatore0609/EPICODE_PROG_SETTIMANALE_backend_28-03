package it.epicode;

import it.epicode.autori.Autore;
import it.epicode.caseeditrici.CasaEditrice;
import it.epicode.pubblicazioni.Libro;
import it.epicode.pubblicazioni.Periodicita;
import it.epicode.pubblicazioni.Rivista;
import it.epicode.service.BibliotecaService;
import it.epicode.utenti.Prestito;
import it.epicode.utenti.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("epicode");
        EntityManager em = emf.createEntityManager();

        BibliotecaService bibliotecaService = new BibliotecaService(em);

        em.getTransaction().begin();

        // Aggiungi un autore
        Autore autore1 = new Autore(null, "Mario", "Rossi");
        bibliotecaService.aggiungiAutore(autore1);

        // Aggiungi una casa editrice
        CasaEditrice casaEditrice1 = new CasaEditrice(null, "Mondadori");
        bibliotecaService.aggiungiCasaEditrice(casaEditrice1);

        // Aggiungi un libro
        Libro libro1 = new Libro("1234567890123", "Il Signore degli Anelli", 1954, 500, autore1, casaEditrice1, "Fantasy");
        bibliotecaService.aggiungiLibro(libro1);

        // Aggiungi una rivista
        Rivista rivista1 = new Rivista("9876543210987", "National Geographic", 2023, 100, Periodicita.MENSILE);
        bibliotecaService.aggiungiRivista(rivista1);

        //-------------------------------------

        // Aggiungi un utente
        Utente utente1 = new Utente(null, "Salvatore", "Sale", LocalDate.of(1998, 1, 1), "TESSERA123", null);
        bibliotecaService.aggiungiUtente(utente1);

        // Aggiungi prestiti
        Prestito prestito1 = new Prestito(null, utente1, libro1, rivista1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        Prestito prestito2 = new Prestito(null, utente1, libro1, rivista1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));;

        // Aggiungi i prestiti al database
        bibliotecaService.aggiungiPrestito(prestito1);
        bibliotecaService.aggiungiPrestito(prestito2);

        // Aggiungi i prestiti all'utente
        utente1.setPrestiti(List.of(prestito1, prestito2));

        em.getTransaction().commit();

        // Esegui ricerche ------------------------------------------------------------

        // Ricerca di un libro per ISBN
        Libro libroRicercato = bibliotecaService.ricercaLibroPerISBN("1234567890123");
        if (libroRicercato != null) {
            System.out.println("Libro trovato per ISBN: " + libroRicercato.getTitolo());
        } else {
            System.out.println("Libro non trovato per l'ISBN.");
        }

        // Ricerca di una rivista per ISBN
        Rivista rivistaRicercata = bibliotecaService.ricercaRivistaPerISBN("9876543210987");
        if (rivistaRicercata != null) {
            System.out.println("Rivista trovata per ISBN: " + rivistaRicercata.getTitolo());
        } else {
            System.out.println("Rivista non trovata per l'ISBN.");
        }

        // Ricerca per anno di pubblicazione
        List<Libro> libriPerAnno = bibliotecaService.ricercaPerAnnoPubblicazione(1954);
        System.out.println("Libri pubblicati nel 1954: " + libriPerAnno.size());

        // Ricerca per titotolo (parziale)
        List<Libro> libriPerTitolo = bibliotecaService.ricercaPerTitolo("Signore");
        System.out.println("Libri con 'Signore' nel titolo: " + libriPerTitolo.size());

        // Ricerca degli autori per cognome
        List<Autore> autoriTrovati = bibliotecaService.ricercaAutorePerCognome("Rossi");
        System.out.println("Autori trovati: " + autoriTrovati.size());

        // Ricerca case editrici per nome
        List<CasaEditrice> caseEditriciTrovate = bibliotecaService.ricercaCasaEditricePerNome("Mondadori");
        System.out.println("Case editrici trovate: " + caseEditriciTrovate.size());

        // Ricerca prestiti attivi per utente
        List<Prestito> prestitiAttivi = bibliotecaService.ricercaPrestitiAttiviPerUtente("TESSERA123");
        System.out.println("Prestiti attivi per l'utente: " + prestitiAttivi.size());

        // Log per vedere le informazioni sui prestiti attivi
        /*prestitiAttivi.forEach(p -> {
            System.out.println("Prestito attivo trovato: ID = " + p.getId() + ", Data inizio: " + p.getDataInizioPrestito() +
                    ", Data fine: " + p.getDataFinePrestito() + ", Data restituzione effettiva: " + p.getDataRestituzioneEffettiva());
        });*/

        // Ricerca prestiti scaduti non restituiti
        List<Prestito> prestitiScaduti = bibliotecaService.ricercaPrestitiScadutiNonRestituiti();
        System.out.println("Prestiti scaduti non restituiti: " + prestitiScaduti.size());

        // Log per vedere le informazioni sui prestiti scaduti non restituiti
        /*prestitiScaduti.forEach(p -> {
            System.out.println("Prestito scaduto non restituito trovato: ID = " + p.getId() + ", Data restituzione prevista: " +
                    p.getDataRestituzionePrevista() + ", Data restituzione effettiva: " + p.getDataRestituzioneEffettiva());
        });*/


        em.close();
        emf.close();
    }
}
