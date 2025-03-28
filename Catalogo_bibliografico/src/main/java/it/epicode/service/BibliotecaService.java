package it.epicode.service;

import it.epicode.autori.Autore;
import it.epicode.autori.AutoreDAO;
import it.epicode.caseeditrici.CasaEditrice;
import it.epicode.caseeditrici.CaseEditriceDAO;
import it.epicode.pubblicazioni.Libro;
import it.epicode.pubblicazioni.LibroDAO;
import it.epicode.pubblicazioni.Rivista;
import it.epicode.pubblicazioni.RivistaDAO;
import it.epicode.utenti.Prestito;
import it.epicode.utenti.PrestitoDAO;
import it.epicode.utenti.Utente;
import it.epicode.utenti.UtenteDAO;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BibliotecaService {

    private LibroDAO libroDAO;
    private RivistaDAO rivistaDAO;
    private AutoreDAO autoreDAO;
    private CaseEditriceDAO casaEditriceDAO;
    private PrestitoDAO prestitoDAO;
    private UtenteDAO utenteDAO;

    public BibliotecaService(EntityManager em) {
        this.libroDAO = new LibroDAO(em);
        this.rivistaDAO = new RivistaDAO(em);
        this.autoreDAO = new AutoreDAO(em);
        this.casaEditriceDAO = new CaseEditriceDAO(em);
        this.prestitoDAO = new PrestitoDAO(em);
        this.utenteDAO = new UtenteDAO(em);
    }

    // Aggiungi un autore
    public void aggiungiAutore(Autore autore) {
        autoreDAO.insert(autore);
    }

    // Aggiungi una casa editrice
    public void aggiungiCasaEditrice(CasaEditrice casaEditrice) {
        casaEditriceDAO.insert(casaEditrice);
    }

    // Aggiungi un libro
    public void aggiungiLibro(Libro libro) {
        libroDAO.insert(libro);
    }

    // Aggiungi una rivista
    public void aggiungiRivista(Rivista rivista) {
        rivistaDAO.insert(rivista);
    }

    // Aggiungi un utente
    public void aggiungiUtente(Utente utente) {
        utenteDAO.insert(utente);
    }

    // Aggiungi un prestito
    public void aggiungiPrestito(Prestito prestito) {
        prestitoDAO.insert(prestito);
    }

    // Rimuovi un autore per ID
    public void rimuoviAutore(Long id) {
        autoreDAO.delete(id);
    }

    // Rimuovi una casa editrice per ID
    public void rimuoviCasaEditrice(Long id) {
        casaEditriceDAO.delete(id);
    }

    // Rimuovi un libro dato l'ISBN
    public void rimuoviLibro(String ISBN) {
        libroDAO.delete(ISBN);
    }

    // Rimuovi una rivista dato l'ISBN
    public void rimuoviRivista(String ISBN) {
        rivistaDAO.delete(ISBN);
    }

    // Rimuovi un utente dato l'ID
    public void rimuoviUtente(String numeroTessera) {
        utenteDAO.delete(numeroTessera);
    }

    // Rimuovi un prestito dato l'ID
    public void rimuoviPrestito(Long id) {
        prestitoDAO.delete(id);
    }

    // Aggiorna un autore
    public void aggiornaAutore(Autore autore) {
        autoreDAO.update(autore);
    }

    // Aggiorna una casa editrice
    public void aggiornaCasaEditrice(CasaEditrice casaEditrice) {
        casaEditriceDAO.update(casaEditrice);
    }

    // Aggiorna un libro
    public void aggiornaLibro(Libro libro) {
        libroDAO.update(libro);
    }

    // Aggiorna una rivista
    public void aggiornaRivista(Rivista rivista) {
        rivistaDAO.update(rivista);
    }

    // Aggiorna un utente
    public void aggiornaUtente(Utente utente) {
        utenteDAO.update(utente);
    }

    // Aggiorna un prestito
    public void aggiornaPrestito(Prestito prestito) {
        prestitoDAO.update(prestito);
    }

    // Ricerca per anno di pubblicazione
    public List<Libro> ricercaPerAnnoPubblicazione(int anno) {
        return libroDAO.findByAnno(anno);
    }

    // Ricerca per titolo (parziale)
    public List<Libro> ricercaPerTitolo(String titolo) {
        return libroDAO.findByTitolo(titolo);
    }
    // Ricerca autore per cognome
    public List<Autore> ricercaAutorePerCognome(String cognome) {
        return autoreDAO.findByCognome(cognome);
    }

    // Ricerca casa editrice per nome
    public List<CasaEditrice> ricercaCasaEditricePerNome(String nome) {
        return casaEditriceDAO.findByNome(nome);
    }

    // Ricerca libro per ISBN
    public Libro ricercaLibroPerISBN(String ISBN) {
        return libroDAO.findByISBN(ISBN);
    }

    // Ricerca rivista per ISBN
    public Rivista ricercaRivistaPerISBN(String ISBN) {
        return rivistaDAO.findByISBN(ISBN);
    }

    // Ricerca prestiti attivi per un utente
    public List<Prestito> ricercaPrestitiAttiviPerUtente(String numeroTessera) {
        return prestitoDAO.findPrestitiAttiviPerUtente(numeroTessera);
    }

    // Ricerca prestiti scaduti non restituiti
    public List<Prestito> ricercaPrestitiScadutiNonRestituiti() {
        return prestitoDAO.findPrestitiScadutiNonRestituiti();
    }
}
