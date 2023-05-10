package com.crud.library.mapper;

import com.crud.library.domain.Reader;
import com.crud.library.domain.ReaderDto;
import com.crud.library.domain.Rental;
import com.crud.library.service.CopyDbService;
import com.crud.library.service.ReaderDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderMapper {

    private final RentalMapper rentalMapper;
    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getName(),
                readerDto.getSurname(),
                readerDto.getAccountCreationDate(),
                new ArrayList<Rental>()
        );
    }
    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getName(),
                reader.getSurname(),
                reader.getAccountCreationDate(),
                rentalMapper.mapToRentalsDtoList(reader.getRentals())
        );
    }
    public List<ReaderDto> mapToReadersDtoList(List<Reader> readersList) {
        return readersList.stream()
                .map(this::mapToReaderDto)
                .toList();
    }
}
