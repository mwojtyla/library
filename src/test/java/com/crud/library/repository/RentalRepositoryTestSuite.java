package com.crud.library.repository;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Rental;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class RentalRepositoryTestSuite {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    private Rental createRental1() {
        Reader reader = new Reader();
        Copy copy = new Copy();
        Rental rental = new Rental();

        copy.getRentals().add(rental);
        copyRepository.save(copy);

        reader.getRentals().add(rental);
        readerRepository.save(reader);

        rental.setDateOfRental(LocalDate.of(2023, 1, 22));
        rental.setDateOfReturn(LocalDate.of(2023, 3, 18));
        rental.setReader(reader);
        rental.setCopy(copy);

        return rental;
    }

    private Rental createRental2() {
        Reader reader = new Reader();
        Copy copy = new Copy();
        Rental rental = new Rental();

        copy.getRentals().add(rental);
        copyRepository.save(copy);

        reader.getRentals().add(rental);
        readerRepository.save(reader);

        rental.setDateOfRental(LocalDate.of(2022, 12, 1));
        rental.setDateOfReturn(LocalDate.of(2023, 1, 29));
        rental.setReader(reader);
        rental.setCopy(copy);

        return rental;
    }

    @Test
    void testFindAllRentals() {
        // Given
        Rental rental1 = createRental1();
        Rental rental2 = createRental2();
        rentalRepository.save(rental1);
        rentalRepository.save(rental2);
        // When
        List<Rental> resultList = rentalRepository.findAll();
        // Then
        assertEquals(2, resultList.size());
    }

    @Test
    void testFindRentalById() {
        // Given
        Rental rental = createRental1();
        rentalRepository.save(rental);
        Long rentalId = rental.getId();
        // When
        Optional<Rental> rentalReceived = rentalRepository.findById(rentalId);
        // Then
        assertTrue(rentalReceived.isPresent());
        assertEquals(LocalDate.of(2023, 1, 22), rentalReceived.get().getDateOfRental());
    }

    @Test
    void testFindByDateOfRentals(){
        // Given
        Rental rental = createRental1();
        rentalRepository.save(rental);
        // When
        List<Rental> resultList = rentalRepository.findByDateOfRental(LocalDate.of(2023, 1, 22));
        // Then
        assertEquals(1, resultList.size());
    }

    @Test
    void testRentalSave() {
        //Given
        Rental rental = createRental1();
        //When
        rentalRepository.save(rental);
        Long rentalId = rental.getId();
        //Then
        Optional<Rental> readRental = rentalRepository.findById(rentalId);
        assertTrue(readRental.isPresent());
        assertNotEquals(0, rentalId);
    }

    @Test
    void testDeleteRentalById() {
        // Given
        Rental rental = createRental1();
        rentalRepository.save(rental);
        Long rentalId = rental.getId();
        // When
        rentalRepository.deleteById(rentalId);
        readerRepository.deleteById(rental.getReader().getId());
        copyRepository.deleteById(rental.getCopy().getId());
        // Then
        assertEquals(Optional.empty(), rentalRepository.findById(rentalId));
        assertFalse(rentalRepository.existsById(rentalId));
    }

}

