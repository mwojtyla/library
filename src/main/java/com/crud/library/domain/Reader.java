package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "READERS")
public class Reader {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique=true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "ACCOUNT_CREATION_DATE")
    private LocalDate accountCreationDate;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "reader",
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals = new ArrayList<>();

}
