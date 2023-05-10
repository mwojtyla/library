package com.crud.library.controller;

import com.crud.library.domain.*;
import com.crud.library.exception.CopyNotFoundException;
import com.crud.library.exception.TitleNotFoundException;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.service.CopyDbService;
import com.crud.library.service.RentalDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/copies")
@RequiredArgsConstructor
public class CopyController {

    private final CopyDbService copyDbService;
    private final RentalDbService rentalDbService;
    private final CopyMapper copyMapper;


    @GetMapping
    public ResponseEntity<List<CopyDto>> getCopies() {
        List<Copy> copiesList = copyDbService.getAllCopies();
        return ResponseEntity.ok(copyMapper.mapToCopiesDtoList(copiesList));
    }

    @GetMapping(value = "{copyId}")
    public ResponseEntity<CopyDto> getCopy(@PathVariable Long copyId) throws CopyNotFoundException {
        return ResponseEntity.ok(copyMapper.mapToCopyDto(copyDbService.getCopy(copyId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCopy(@RequestBody CopyDto CopyDto) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(CopyDto);
        copyDbService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    public ResponseEntity<CopyDto> updateCopy(@RequestBody CopyDto copyDto) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        Copy savedCopy = copyDbService.saveCopy(copy);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }

    @DeleteMapping(value = "{copyId}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long copyId) throws CopyNotFoundException {
        copyDbService.getCopy(copyId).getTitle().setCopies(null);
        copyDbService.getCopy(copyId).getRentals().stream()
                .forEach(r -> rentalDbService.deleteRental(r.getId()));
        copyDbService.deleteCopy(copyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{copyId}/{status}")
    public ResponseEntity<CopyDto> updateCopyStatus(@PathVariable(name = "copyId") Long copyId, @PathVariable(name = "status") String status) throws CopyNotFoundException {
        Copy copy = copyDbService.getCopy(copyId);
        copy.setStatus(status);
        Copy savedCopy = copyDbService.saveCopy(copy);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }
}
