package com.crud.library.mapper;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.service.CopyDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TitleMapper {

    private final CopyDbService copyDbService;

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getPublicationYear(),
                copyDbService.getAllCopies().stream()
                        .filter(c -> c.getTitle().getId().equals(titleDto.getId()))
                        .collect(Collectors.toList())
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getPublicationYear()
        );
    }

    public List<TitleDto> mapToTitlesDtoList(final List<Title> titlesList) {
        return titlesList.stream()
                .map(this::mapToTitleDto)
                .toList();
    }

}
