package com.example.demo.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"docente", "discenti"})
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(name = "anno_accademico")
    private Integer annoAccademico;

    @ManyToOne
    @JoinColumn(name = "id_docente",  nullable = true)
    private Docente docente;

    @ManyToMany
    @JoinTable(
            name = "corso_discente",
            joinColumns = @JoinColumn(name = "id_corso"),
            inverseJoinColumns = @JoinColumn(name = "id_discente")
    )
    private List<Discente> discenti;


//    @ManyToMany(mappedBy = "corsi")
//    private Set<Discente> discenti=new HashSet<>();


}
