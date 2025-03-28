package it.epicode.pubblicazioni;

import it.epicode.autori.Autore;
import it.epicode.caseeditrici.CasaEditrice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libri")
@SuperBuilder
public class Libro {
    @Id
    @Column(length = 13, nullable = false, unique = true)
    private String ISBN;

    @Column(length = 50, nullable = false)
    private String titolo;

    private int annoPubblicazione;
    private int numeroPagine;

    //Libro M ──────────────── 1 Autore

    // Motivazione: Un libro è scritto da un singolo autore.
    // Ogni libro ha un autore, ma un autore può scrivere molti libri.
    @ManyToOne
    private Autore autore;

    //Libro M ──────────────── 1 CasaEditrice
    //
    // Motivazione: Un libro è pubblicato da una casa editrice.
    // Una casa editrice può pubblicare molti libri, ma ogni libro ha una sola casa editrice.
    @ManyToOne
    private CasaEditrice casaEditrice;

    @Column(length = 50, nullable = false)
    private String genere;
}
