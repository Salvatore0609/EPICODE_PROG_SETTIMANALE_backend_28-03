package it.epicode.utenti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;
    @Column(length = 50, nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @Column(nullable = false, unique = true)
    private String numeroTessera;


    // Utente 1 ──────────────── M Prestito
    //
    // Motivazione: Un utente può avere più prestiti.
    // Un utente potrebbe avere molti libri o riviste in prestito nel tempo.
    @OneToMany(mappedBy = "utente")
    private List<Prestito> prestiti;

    /*public List<Prestito> getPrestitiConclusi() {
        return prestiti.stream()
                .filter(p -> p.getDataRestituzioneEffettiva() != null)
                .collect(Collectors.toList());
    }

    public List<Prestito> getPrestitiAttivi() {
        return prestiti.stream()
                .filter(p -> p.getDataRestituzioneEffettiva() == null)
                .collect(Collectors.toList());
    }*/
}