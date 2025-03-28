package it.epicode.utenti;

import it.epicode.pubblicazioni.Libro;
import it.epicode.pubblicazioni.Rivista;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prestiti")
@SuperBuilder
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //Prestito M ──────────────── 1 Libro
    //
    // Motivazione: Un prestito si riferisce a un singolo libro.
    // Ogni prestito riguarda un libro specifico, ma un libro può essere prestato più volte.
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    // ELEMENTO PRESTATO

    //Prestito M ──────────────── 1 Libro
    //
    // Motivazione: Un prestito si riferisce a un singolo libro.
    // Ogni prestito riguarda un libro specifico, ma un libro può essere prestato più volte.
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    //Prestito M ──────────────── 1 Rivista
    //
    // Motivazione: Un prestito si riferisce a una singola rivista.
    // Un prestito può essere per una rivista, e una rivista può essere prestata più volte.
    @ManyToOne
    @JoinColumn(name = "rivista_id")
    private Rivista rivista;

    private LocalDate dataInizioPrestito;
    private LocalDate dataRestituzionePrevista;
    private LocalDate dataRestituzioneEffettiva;
    //
    /*private LocalDate dataFinePrestito;*/

    // Costruttore per Prestito
    public Prestito(Utente utente, Libro libro, Rivista rivista, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.libro = libro;
        this.rivista = rivista;
        this.dataInizioPrestito = dataInizioPrestito;
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }

   /* public void segnalaRestituzione() {
        this.dataRestituzioneEffettiva = LocalDate.now();
        this.dataFinePrestito = this.dataRestituzioneEffettiva;
    }*/
}
