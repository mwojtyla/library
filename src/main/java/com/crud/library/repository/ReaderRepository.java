package com.crud.library.repository;

import com.crud.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {
   @Override
   List<Reader> findAll();

   Optional<Reader> findById(Long id);

   Reader save(Reader reader);

   void deleteById(Long id);
}
