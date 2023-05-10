package com.crud.library.repository;

import com.crud.library.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class ReaderRepositoryTestSuite {

    @Autowired
    private ReaderRepository readerRepository;

    private Reader createReader1() {
        Rental rental = new Rental();
        Reader reader = new Reader();
        reader.setName("Joe");
        reader.setSurname("Cartright");
        reader.setAccountCreationDate(LocalDate.of(2023, 1, 1));
        reader.getRentals().add(rental);
        rental.setReader(reader);
        return reader;
    }

    private Reader createReader2() {
        Rental rental = new Rental();
        Reader reader = new Reader();
        reader.setName("Billy");
        reader.setSurname("Black");
        reader.setAccountCreationDate(LocalDate.of(2022, 12, 15));
        reader.getRentals().add(rental);
        rental.setReader(reader);
        return reader;
    }

    @Test
    void testFindAllReaders() {
        // Given
        Reader reader1 = createReader1();
        Reader reader2 = createReader2();
        readerRepository.save(reader1);
        readerRepository.save(reader2);
        // When
        List<Reader> resultList = readerRepository.findAll();
        // Then
        assertEquals(2, resultList.size());
    }

    @Test
    void testFindReaderById() {
        // Given
        Reader reader = createReader1();
        readerRepository.save(reader);
        // When
        Optional<Reader> readerReceived = readerRepository.findById(reader.getId());
        // Then
        assertTrue(readerReceived.isPresent());
        assertEquals("Cartright", readerReceived.get().getSurname());
    }

    @Test
    void testReaderSave() {
        // Given
        Reader reader = createReader1();
        // When
        readerRepository.save(reader);
        Long readerId = reader.getId();
        // Then
        Optional<Reader> readReader = readerRepository.findById(readerId);
        assertTrue(readReader.isPresent());
        assertNotEquals(0, readerId);
    }

    @Test
    void testDeleteReaderById() {
        // Given
        Reader reader = createReader1();
        readerRepository.save(reader);
        Long readerId = reader.getId();
        // When
        readerRepository.deleteById(readerId);
        // Then
        assertEquals(Optional.empty(), readerRepository.findById(readerId));
        assertFalse(readerRepository.existsById(readerId));
    }

}
