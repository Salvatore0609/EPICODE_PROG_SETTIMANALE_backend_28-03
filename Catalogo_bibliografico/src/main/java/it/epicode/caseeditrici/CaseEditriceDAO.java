package it.epicode.caseeditrici;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CaseEditriceDAO {
    private EntityManager em;

    // Trova una casa editrice per ID
    public CasaEditrice findById(Long id) {
        return em.find(CasaEditrice.class, id);
    }

    // Inserisci una casa editrice
    public void insert(CasaEditrice casaEditrice) {
        em.persist(casaEditrice);
    }

    // Inserisci più case editrici
    public void insert(List<CasaEditrice> caseEditrici) {
        caseEditrici.forEach(em::persist);
    }

    // Elimina una casa editrice per ID
    public void delete(Long id) {
        CasaEditrice casaEditrice = findById(id);
        if (casaEditrice != null) {
            em.remove(casaEditrice);
        }
    }

    // Modifica una casa editrice
    public void update(CasaEditrice casaEditrice) {
        em.merge(casaEditrice);
    }

    // Ricerca case editrici per nome
    public List<CasaEditrice> findByNome(String nome) {
        return em.createQuery("SELECT ce FROM CasaEditrice ce WHERE ce.nome = :nome", CasaEditrice.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    // Ricerca di tutte le case editrici
    public List<CasaEditrice> findAll() {
        return em.createQuery("SELECT ce FROM CasaEditrice ce", CasaEditrice.class)
                .getResultList();
    }
}
