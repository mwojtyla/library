package com.crud.library.service;

import com.crud.library.exception.RentalNotFoundException;
import com.crud.library.domain.Rental;;
import com.crud.library.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RentalDbService {
    private final RentalRepository rentalRepository;
    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }
    public Rental getRental(final Long rentalId) throws RentalNotFoundException {
        return rentalRepository.findById(rentalId).orElseThrow(RentalNotFoundException::new);
    }
    public Rental saveRental(final Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(final Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }
}
