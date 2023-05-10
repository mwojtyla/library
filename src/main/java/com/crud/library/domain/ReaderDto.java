package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReaderDto {
    private Long id;
    private String name;
    private String surname;
    private LocalDate accountCreationDate;
    private List<RentalDto> rentalsDtoList;
}
