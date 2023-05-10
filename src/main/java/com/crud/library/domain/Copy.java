package com.crud.library.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "COPIES")
public class Copy {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique=true)
    private Long id;

    @Column(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private Title title;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "copy",
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals = new ArrayList<>();
}
