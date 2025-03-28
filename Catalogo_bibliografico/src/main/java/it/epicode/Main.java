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


        Autore autore1 = new Autore(null, "Mario", "Rossi");
        Autore autore2 = new Autore(null, "Gianni", "Verdi");
        bibliotecaService.aggiungiAutore(autore1);
        bibliotecaService.aggiungiAutore(autore2);


        CasaEditrice casaEditrice1 = new CasaEditrice(null, "Mondadori");
        CasaEditrice casaEditrice2 = new CasaEditrice(null, "Panini");
        bibliotecaService.aggiungiCasaEditrice(casaEditrice1);
        bibliotecaService.aggiungiCasaEditrice(casaEditrice2);


        Libro libro1 = new Libro("1234567890123", "Il Signore degli Anelli", 1954, 500, autore1, casaEditrice1, "Fantasy");
        Libro libro2 = new Libro("9876543210987", "Harry Potter e il calice di fuoco", 1997, 400, autore2, casaEditrice2, "Fantasy");
        bibliotecaService.aggiungiLibro(libro1);
        bibliotecaService.aggiungiLibro(libro2);

        Rivista rivista1 = new Rivista("9876543210987", "National Geographic", 2023, 100, Periodicita.MENSILE);
        Rivista rivista2 = new Rivista("1234567890123", "Cosmopolitan", 2023, 80, Periodicita.SETTIMANALE);
        bibliotecaService.aggiungiRivista(rivista1);
        bibliotecaService.aggiungiRivista(rivista2);

        //-------------------------------------


        Utente utente1 = new Utente(null, "Salvatore", "Sale", LocalDate.of(1998, 1, 1), "TESSERA123", null);
        Utente utente2 = new Utente(null, "Manuel", "Desole", LocalDate.of(1996, 5, 31), "TESSERA452", null);
        Utente utente3 = new Utente(null, "Giuseppe", "Verdi", LocalDate.of(1996, 5, 31), "TESSERA223", null);
        bibliotecaService.aggiungiUtente(utente1);
        bibliotecaService.aggiungiUtente(utente2);
        bibliotecaService.aggiungiUtente(utente3);


        Prestito prestito1 = new Prestito(null, utente1, libro1, rivista1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        Prestito prestito2 = new Prestito(null, utente1, libro1, rivista1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));;
        Prestito prestito3 = new Prestito(null, utente2, libro1, rivista2, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        Prestito prestito4 = new Prestito(null, utente3, libro2, rivista2, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        Prestito prestito5 = new Prestito(null, utente2, libro2, rivista2, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));


        // Aggiungi i prestiti al database
        bibliotecaService.aggiungiPrestito(prestito1);
        bibliotecaService.aggiungiPrestito(prestito2);
        bibliotecaService.aggiungiPrestito(prestito3);
        bibliotecaService.aggiungiPrestito(prestito4);
        bibliotecaService.aggiungiPrestito(prestito5);

        // Aggiungi i prestiti all'utente
        utente1.setPrestiti(List.of(prestito1, prestito2));
        utente2.setPrestiti(List.of(prestito3, prestito5));
        utente3.setPrestiti(List.of(prestito4));

        bibliotecaService.restituisciPrestito(1L);

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
