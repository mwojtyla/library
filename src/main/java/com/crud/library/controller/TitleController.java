package com.crud.library.controller;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.CopyDbService;
import com.crud.library.service.TitleDbService;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/titles")
@RequiredArgsConstructor
public class TitleController {

    private final TitleDbService titleDbService;
    private final TitleService titleService;
    private final CopyDbService copyDbService;
    private final TitleMapper titleMapper;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getTitles() {
        List<Title> titleList = titleDbService.getAllTitles();
        return ResponseEntity.ok(titleMapper.mapToTitlesDtoList(titleList));
    }

    @GetMapping("{titleId}")
    public ResponseEntity<TitleDto> getTitle(@PathVariable Long titleId) throws TitleNotFoundException {
        return ResponseEntity.ok(titleMapper.mapToTitleDto(titleDbService.getTitle(titleId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        titleDbService.saveTitle(title);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TitleDto> updateTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        Title savedTitle = titleDbService.saveTitle(title);
        return ResponseEntity.ok(titleMapper.mapToTitleDto(savedTitle));
    }

    @DeleteMapping("{titleId}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long titleId) throws TitleNotFoundException {
        titleDbService.getTitle(titleId).getCopies().stream()
                .forEach(c -> copyDbService.deleteCopy(c.getId()));
        titleDbService.deleteTitle(titleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/amountAvailableCopies/{title}")
    public ResponseEntity<Integer> getAmountAvailableCopiesOfTitle(@PathVariable String title) {
        Title titles = titleDbService.getTitleByTitle(title);
        int amountAvailableCopiesOfTitle = titleService.getAmountAvailableCopiesOfTitle(titles);
        return ResponseEntity.ok(amountAvailableCopiesOfTitle);
    }

    @GetMapping("/availableCopies/{title}")
    public ResponseEntity<List<Long>> getAvailableCopiesOfTitle(@PathVariable String title) {
        Title titles = titleDbService.getTitleByTitle(title);
        List<Long> listOfAvailableCopiesId = titleService.getListOfAvailableCopiesIdOfTitle(titles);
        return ResponseEntity.ok(listOfAvailableCopiesId);
    }

    @GetMapping("/titleByYear/{publicationYear}")
    public ResponseEntity<List<TitleDto>> getTitlesByPublicationYear(@PathVariable Integer publicationYear) {
        return ResponseEntity.ok(titleMapper.mapToTitlesDtoList(titleDbService.getTitleByYear(publicationYear)));
    }
}
