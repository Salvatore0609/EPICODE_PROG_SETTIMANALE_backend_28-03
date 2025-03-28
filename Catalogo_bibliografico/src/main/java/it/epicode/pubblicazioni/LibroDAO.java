package it.epicode.pubblicazioni;

import it.epicode.autori.Autore;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LibroDAO {
    private EntityManager em;

    // Ricerca per ISBN
    public Libro findByISBN(String ISBN) {
        return em.find(Libro.class, ISBN);
    }

    // Inserimento di un libro
    public void insert(Libro libro) {
        em.persist(libro);
    }

    // Inserimento di una lista di libri
    public void insert(List<Libro> libri) {
        libri.forEach(libro -> em.persist(libro));
    }

    // Rimozione di un libro dato l'ISBN
    public void delete(String ISBN) {
        Libro libro = findByISBN(ISBN);
        if (libro != null) {
            em.remove(libro);
        }
    }

    // Aggiornamento di un libro
    public void update(Libro libro) {
        em.merge(libro);
    }

    // Ricerca per autore
    public List<Libro> findByAutore(Autore autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }

    // Ricerca per titolo (parziale)
    public List<Libro> findByTitolo(String titolo) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.titolo LIKE :titolo", Libro.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }

    // Ricerca per anno di pubblicazione
    public List<Libro> findByAnno(int anno) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.annoPubblicazione = :anno", Libro.class)
                .setParameter("anno", anno)
                .getResultList();
    }

    // Ricerca per genere
    public List<Libro> findByGenere(String genere) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.genere = :genere", Libro.class)
                .setParameter("genere", genere)
                .getResultList();
    }
}
