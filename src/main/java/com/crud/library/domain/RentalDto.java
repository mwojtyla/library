package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RentalDto {
    private Long id;
    private LocalDate dateOfRental;
    private LocalDate dateOfReturn;
    private Long readerId;
    private Long copyId;
}
