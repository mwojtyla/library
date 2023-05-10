package com.crud.library.service;

import com.crud.library.domain.Status;
import com.crud.library.domain.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TitleService {
    public List<Long> getListOfAvailableCopiesIdOfTitle(Title title) {
        return title.getCopies().stream()
                .filter(c->c.getStatus().equals(Status.AVAILABLE))
                .map(i->i.getId())
                .collect(Collectors.toList());
    }

    public int getAmountAvailableCopiesOfTitle(Title title) {
        return title.getCopies().stream()
                .filter(c->c.getStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList())
                .size();
    }
}
