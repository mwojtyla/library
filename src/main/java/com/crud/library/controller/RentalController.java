package com.crud.library.controller;

import com.crud.library.domain.*;
import com.crud.library.exception.CopyNotFoundException;
import com.crud.library.exception.ReaderNotFoundException;
import com.crud.library.exception.RentalNotFoundException;
import com.crud.library.mapper.RentalMapper;
import com.crud.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalDbService rentalDbService;
    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @GetMapping
    public ResponseEntity<List<RentalDto>> getRentals() {
        List<Rental> rentals = rentalDbService.getRentals();
        return ResponseEntity.ok(rentalMapper.mapToRentalsDtoList(rentals));
    }
    @GetMapping("{rentalId}")
    public ResponseEntity<RentalDto> getRental(@PathVariable Long rentalId) throws RentalNotFoundException {
        return ResponseEntity.ok(rentalMapper.mapToRentalDto(rentalDbService.getRental(rentalId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRental(@RequestBody RentalDto rentalDto) throws ReaderNotFoundException, CopyNotFoundException {
        Rental rental = rentalMapper.mapToRental(rentalDto);
        rentalDbService.saveRental(rental);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<RentalDto> updateRental(@RequestBody RentalDto rentalDto) throws ReaderNotFoundException, CopyNotFoundException {
        Rental rental = rentalMapper.mapToRental(rentalDto);
        Rental savedRental = rentalDbService.saveRental(rental);
        return ResponseEntity.ok(rentalMapper.mapToRentalDto(savedRental));
    }

    @DeleteMapping("{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long rentalId) {
        rentalDbService.deleteRental(rentalId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rent/{readerId}/{copyId}")
    public ResponseEntity<Void> rentBook(@PathVariable Long readerId, @PathVariable Long copyId) throws ReaderNotFoundException, CopyNotFoundException {
        rentalService.rentBook(readerId, copyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping ("/return/{rentalId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long rentalId) throws RentalNotFoundException {
        rentalService.returnBook(rentalId);
        return ResponseEntity.ok().build();
    }
}
