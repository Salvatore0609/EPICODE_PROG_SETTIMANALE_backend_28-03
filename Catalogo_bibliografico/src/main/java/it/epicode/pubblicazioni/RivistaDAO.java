package it.epicode.pubblicazioni;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RivistaDAO {

    private EntityManager em;

    // Ricerca per ISBN
    public Rivista findByISBN(String ISBN) {
        return em.find(Rivista.class, ISBN);
    }

    // Inserimento di una rivista
    public void insert(Rivista rivista) {
        em.persist(rivista);
    }

    // Inserimento di una lista di riviste
    public void insert(List<Rivista> riviste) {
        riviste.forEach(rivista -> em.persist(rivista));
    }

    // Rimozione di una rivista dato l'ISBN
    public void delete(String ISBN) {
        Rivista rivista = findByISBN(ISBN);
        if (rivista != null) {
            em.remove(rivista);
        }
    }

    // Aggiornamento di una rivista
    public void update(Rivista rivista) {
        em.merge(rivista);
    }

    // Ricerca per periodicità
    public List<Rivista> findByPeriodicita(Periodicita periodicita) {
        return em.createQuery("SELECT r FROM Rivista r WHERE r.periodicita = :periodicita", Rivista.class)
                .setParameter("periodicita", periodicita)
                .getResultList();
    }

    // Ricerca per titolo (parziale)
    public List<Rivista> findByTitolo(String titolo) {
        return em.createQuery("SELECT r FROM Rivista r WHERE r.titolo LIKE :titolo", Rivista.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }

    // Ricerca per anno di pubblicazione
    public List<Rivista> findByAnno(int anno) {
        return em.createQuery("SELECT r FROM Rivista r WHERE r.annoPubblicazione = :anno", Rivista.class)
                .setParameter("anno", anno)
                .getResultList();
    }
}
