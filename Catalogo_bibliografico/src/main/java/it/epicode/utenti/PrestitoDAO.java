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

    // Ricerca di un prestito dato l'ID
    public Prestito findById(Long id) {
        return em.find(Prestito.class, id);
    }

    // Ricerca dei prestiti attivi per un utente dato il numero di tessera
    public List<Prestito> findPrestitiAttiviPerUtente(String numeroTessera) {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :tessera " +
                                "AND (p.dataRestituzioneEffettiva IS NULL OR p.dataRestituzioneEffettiva > CURRENT_DATE)",
                        Prestito.class)
                .setParameter("tessera", numeroTessera)
                .getResultList();
    }

    // Ricerca dei prestiti scaduti e non restituiti
    public List<Prestito> findPrestitiScadutiNonRestituiti() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE " +
                                "AND p.dataRestituzioneEffettiva IS NULL",
                        Prestito.class)
                .getResultList();
    }


}
