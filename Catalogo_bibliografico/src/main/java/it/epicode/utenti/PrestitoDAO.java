package it.epicode.utenti;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class PrestitoDAO {

    private EntityManager em;

    // Aggiungere un prestito
    public void insert(Prestito prestito) {
        em.persist(prestito);
    }

    // Aggiungere una lista di prestiti
    public void insert(List<Prestito> prestiti) {
        prestiti.forEach(prestito -> em.persist(prestito));
    }

    // Rimuovere un prestito dato l'ID
    public void delete(Long id) {
        Prestito prestito = em.find(Prestito.class, id);
        if (prestito != null) {
            em.remove(prestito);
        }
    }

    // Aggiornare un prestito
    public void update(Prestito prestito) {
        em.merge(prestito);
    }

    // Ricerca dei prestiti attivi per un utente dato il numero di tessera
    public List<Prestito> findPrestitiAttiviPerUtente(String numeroTessera) {
        List<Prestito> prestiti = em.createQuery(
                        "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();

        // Log per vedere cosa restituisce la query
        /*System.out.println("Prestiti attivi per utente " + numeroTessera + ": " + prestiti.size());
        prestiti.forEach(p -> {
            System.out.println("Prestito attivo trovato: ID = " + p.getId() + ", Data inizio: " + p.getDataInizioPrestito() +
                    ", Data fine: " + p.getDataFinePrestito());
        });*/
        return prestiti;
    }

    // Ricerca dei prestiti scaduti e non restituiti
    public List<Prestito> findPrestitiScadutiNonRestituiti() {
        List<Prestito> prestiti = em.createQuery(
                        "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < :oggi", Prestito.class)
                .setParameter("oggi", LocalDate.now())
                .getResultList();

        // Log per vedere cosa restituisce la query
        /*System.out.println("Prestiti scaduti non restituiti: " + prestiti.size());
        prestiti.forEach(p -> {
            System.out.println("Prestito scaduto non restituito trovato: ID = " + p.getId() + ", Data restituzione prevista: " +
                    p.getDataRestituzionePrevista());
        });*/
        return prestiti;
    }
}
