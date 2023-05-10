package com.crud.library.service;

import com.crud.library.exception.CopyNotFoundException;
import com.crud.library.domain.Copy;
import com.crud.library.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyDbService {
    private final CopyRepository copyRepository;

    public List<Copy> getAllCopies() {
        return copyRepository.findAll();
    }

    public Copy getCopy(final Long copyId) throws CopyNotFoundException {
        return copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
    }

    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public void deleteCopy(final Long copyId) {
        copyRepository.deleteById(copyId);
    }


}
