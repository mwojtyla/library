package com.crud.library.service;

import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.domain.Title;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleDbService {

    private final TitleRepository titleRepository;

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Title getTitle(final Long titleId) throws TitleNotFoundException {
        return titleRepository.findById(titleId).orElseThrow(TitleNotFoundException::new);
    }

    public Title getTitleByTitle(final String title) {
        return titleRepository.findByTitle(title);
    }
    public List<Title> getTitleByYear(final Integer publicationYear) {
        return titleRepository.findByPublicationYear(publicationYear);
    }
    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitle(final Long titleId) {
        titleRepository.deleteById(titleId);
    }
}
