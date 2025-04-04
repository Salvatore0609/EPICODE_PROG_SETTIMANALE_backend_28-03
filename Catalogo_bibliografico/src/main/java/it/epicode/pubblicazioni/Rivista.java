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
@Table(name = "riviste")
@SuperBuilder
public class Rivista {
    // ISBN come chiave primaria
    @Id
    @Column(length = 13, nullable = false, unique = true)
    private String ISBN;

    @Column(length = 50, nullable = false)
    private String titolo;

    private int annoPubblicazione;
    private int numeroPagine;

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;
}