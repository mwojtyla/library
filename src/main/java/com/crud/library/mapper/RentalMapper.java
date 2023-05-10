package com.crud.library.mapper;

import com.crud.library.domain.Rental;
import com.crud.library.domain.RentalDto;
import com.crud.library.exception.CopyNotFoundException;
import com.crud.library.exception.ReaderNotFoundException;
import com.crud.library.service.CopyDbService;
import com.crud.library.service.ReaderDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalMapper {

    private final ReaderDbService readerDbService;
    private final CopyDbService copyDbService;

    public Rental mapToRental(final RentalDto rentalDto) throws ReaderNotFoundException, CopyNotFoundException {
        return new Rental(
                rentalDto.getId(),
                rentalDto.getDateOfRental(),
                rentalDto.getDateOfReturn(),
                readerDbService.getReader(rentalDto.getReaderId()),
                copyDbService.getCopy(rentalDto.getCopyId())
        );
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getDateOfRental(),
                rental.getDateOfReturn(),
                rental.getReader().getId(),
                rental.getCopy().getId()
        );
    }

    public List<RentalDto> mapToRentalsDtoList(final List<Rental> rentalsList) {
        return rentalsList.stream()
                .map(this::mapToRentalDto)
                .toList();
    }
}
