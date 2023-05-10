package com.crud.library.service;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Reader;
import com.crud.library.domain.Rental;
import com.crud.library.domain.Status;
import com.crud.library.exception.CopyNotFoundException;
import com.crud.library.exception.ReaderNotFoundException;
import com.crud.library.exception.RentalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final ReaderDbService readerDbService;
    private final RentalDbService rentalDbService;
    private final CopyDbService copyDbService;
    public void rentBook(Long readerId, Long copyId) throws ReaderNotFoundException, CopyNotFoundException {
        Reader reader = readerDbService.getReader(readerId);
        Copy copy = copyDbService.getCopy(copyId);
        copy.setStatus(Status.RENTED);
        copyDbService.saveCopy(copy);
        Rental rental = new Rental();
        rental.setReader(reader);
        rental.setCopy(copy);
        rental.setDateOfRental(LocalDate.now());
        rental.setDateOfReturn(LocalDate.of(1,1,1));
        rentalDbService.saveRental(rental);
    }

    public void returnBook(Long rentalId) throws RentalNotFoundException {
        Rental rental = rentalDbService.getRental(rentalId);
        rental.getCopy().setStatus(Status.AVAILABLE);
        rental.setDateOfReturn(LocalDate.now());
        copyDbService.saveCopy(rental.getCopy());
        rentalDbService.saveRental(rental);
    }
}
