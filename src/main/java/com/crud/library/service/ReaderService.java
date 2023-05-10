package com.crud.library.service;

import com.crud.library.domain.Rental;
import com.crud.library.exception.ReaderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderDbService readerDbService;
    private final RentalDbService rentalDbService;
    public void removeReader(Long readerId) throws ReaderNotFoundException {
        List<Rental> rentalsList = readerDbService.getReader(readerId).getRentals();
        rentalsList.stream()
                .map(r -> r.getCopy())
                .forEach(c -> c.setRentals(null));
        rentalsList
                .forEach(r -> r.setReader(null));
        rentalsList
                .forEach(r -> r.setCopy(null));
        rentalsList
                .forEach(r -> rentalDbService.deleteRental(r.getId()));
        readerDbService.deleteReader(readerId);
    }
}
