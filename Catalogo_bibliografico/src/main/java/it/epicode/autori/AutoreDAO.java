package it.epicode.autori;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AutoreDAO {
    private EntityManager em;
    // Trova un autore per ID
    public Autore findById(Long id) {
        return em.find(Autore.class, id);
    }
    // Inserisci un nuovo autore
    public void insert(Autore autore) {
        em.persist(autore);
    }
    // Inserisci più autori
    public void insert(List<Autore> autori) {
        autori.forEach(em::persist);
    }
    // Elimina un autore per ID
    public void delete(Long id) {
        Autore autore = findById(id);
        if (autore != null) {
            em.remove(autore);
        }
    }
    // Modifica un autore
    public void update(Autore autore) {
        em.merge(autore);
    }
    // Ricerca autori per cognome
    public List<Autore> findByCognome(String cognome) {
        return em.createQuery("SELECT a FROM Autore a WHERE a.cognome = :cognome", Autore.class)
                .setParameter("cognome", cognome)
                .getResultList();
    }
    // Ricerca di tutti gli autori
    public List<Autore> findAll() {
        return em.createQuery("SELECT a FROM Autore a", Autore.class)
                .getResultList();
    }
}

