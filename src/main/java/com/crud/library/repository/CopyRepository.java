package com.crud.library.repository;

import com.crud.library.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
    @Override
    List<Copy> findAll();

    Optional<Copy> findById(Long id);

    Copy save(Copy copy);

    void deleteById(Long id);
}
