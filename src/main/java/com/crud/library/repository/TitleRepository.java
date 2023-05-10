package com.crud.library.repository;

import com.crud.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TitleRepository extends CrudRepository<Title, Long> {
    @Override
    List<Title> findAll();
    Optional<Title> findById(Long id);
    List<Title> findByPublicationYear(Integer publicationYear);

    Title findByTitle(String title);

    Title save(Title title);

    void deleteById(Long id);

}
