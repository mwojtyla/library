package com.crud.library.repository;

import com.crud.library.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class CopyRepositoryTestSuite {

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private RentalRepository rentalRepository;

    private Copy createCopy1(){
        Title title = new Title();
        Copy copy = new Copy();
        Rental rental = new Rental();

        rental.setCopy(copy);
        //rentalsRepository.save(rental);

        title.getCopies().add(copy);
        titleRepository.save(title);

        copy.setStatus(Status.AVAILABLE);
        copy.setTitle(title);
        copy.getRentals().add(rental);

        return copy;
    }

    private Copy createCopy2(){
        Title title = new Title();
        Copy copy = new Copy();
        Rental rental = new Rental();

        rental.setCopy(copy);
        //rentalsRepository.save(rental);

        title.getCopies().add(copy);
        titleRepository.save(title);

        copy.setStatus(Status.RENTED);
        copy.setTitle(title);
        copy.getRentals().add(rental);

        return copy;
    }

    @Test
    void testFindAllCopies() {
        // Given
        Copy copy1 = createCopy1();
        Copy copy2 = createCopy2();
        copyRepository.save(copy1);
        copyRepository.save(copy2);;
        // When
        List<Copy> resultList = copyRepository.findAll();
        // Then
        assertEquals(2, resultList.size());
    }

    @Test
    void testFindCopyById() {
        // Given
        Copy copy = createCopy1();
        copyRepository.save(copy);
        // When
        Optional<Copy> copyReceived = copyRepository.findById(copy.getId());
        // Then
        assertTrue(copyReceived.isPresent());
        assertEquals(Status.AVAILABLE, copyReceived.get().getStatus());
    }

    @Test
    void testCopySave() {
        // Given
        Copy copy = createCopy1();
        // When
        copyRepository.save(copy);
        Long copyId = copy.getId();
        // Then
        Optional<Copy> readCopy = copyRepository.findById(copyId);
        assertTrue(readCopy.isPresent());
        assertNotEquals(0, copyId);
    }

    @Test
    void testDeleteCopyById() {
        // Given
        Copy copy = createCopy2();
        copyRepository.save(copy);
        Long copyId = copy.getId();
        // When
        copyRepository.deleteById(copyId);

        titleRepository.deleteById(copy.getTitle().getId());
        // Then
        assertEquals(Optional.empty(), copyRepository.findById(copyId));
        assertFalse(copyRepository.existsById(copyId));
    }
}
