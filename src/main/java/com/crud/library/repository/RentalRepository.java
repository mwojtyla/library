package com.crud.library.repository;

import com.crud.library.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findByDateOfRental(LocalDate dateOfRental);

    @Override
    List<Rental> findAll();

    Optional<Rental> findById(Long id);

    Rental save(Rental rental);

    void deleteById(Long id);
}
