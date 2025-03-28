package it.epicode.utenti;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UtenteDAO {

    private EntityManager em;

    // Ricerca per numero di tessera
    public Utente findByNumeroTessera(String numeroTessera) {
        return em.find(Utente.class, numeroTessera);
    }

    // Inserimento di un utente
    public void insert(Utente utente) {
        em.persist(utente);
    }

    // Inserimento di una lista di utenti
    public void insert(List<Utente> utenti) {
        utenti.forEach(utente -> em.persist(utente));
    }

    // Rimozione di un utente dato il numero di tessera
    public void delete(String numeroTessera) {
        Utente utente = findByNumeroTessera(numeroTessera);
        if (utente != null) {
            em.remove(utente);
        }
    }

    // Aggiornamento di un utente
    public void update(Utente utente) {
        em.merge(utente);
    }

    // Ricerca per nome e cognome
    public List<Utente> findByNomeCognome(String nome, String cognome) {
        return em.createQuery(
                        "SELECT u FROM Utente u WHERE u.nome = :nome AND u.cognome = :cognome", Utente.class)
                .setParameter("nome", nome)
                .setParameter("cognome", cognome)
                .getResultList();
    }
}
