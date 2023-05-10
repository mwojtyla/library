package com.crud.library.mapper;

import com.crud.library.domain.Copy;
import com.crud.library.domain.CopyDto;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.service.RentalDbService;
import com.crud.library.service.TitleDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopyMapper {

    private final TitleDbService titleDbService;
    private final RentalDbService rentalDbService;

    public Copy mapToCopy(final CopyDto copyDto) throws TitleNotFoundException {
        return new Copy(
                copyDto.getId(),
                copyDto.getStatus(),
                titleDbService.getTitle(copyDto.getTitleId()),
                rentalDbService.getRentals().stream()
                        .filter(r -> r.getCopy().getId().equals(copyDto.getId()))
                        .collect(Collectors.toList())
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.getStatus(),
                copy.getTitle().getId()
        );
    }

    public List<CopyDto> mapToCopiesDtoList(final List<Copy> copiesList) {
        return copiesList.stream()
                .map(this::mapToCopyDto)
                .toList();
    }
}
