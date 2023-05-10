package com.crud.library.repository;

import com.crud.library.domain.Copy;
import com.crud.library.domain.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class TitleRepositoryTestSuite {

    @Autowired
    private TitleRepository titleRepository;

    private Title createTitle1() {
        Copy copy = new Copy();
        Title title = new Title();
        title.setTitle("Message in a bottle");
        title.setAuthor("Nicholas Sparks");
        title.setPublicationYear(1998);
        title.getCopies().add(copy);
        copy.setTitle(title);
        return title;
    }

    private Title createTitle2() {
        Copy copy = new Copy();
        Title title = new Title();
        title.setTitle("The Choice");
        title.setAuthor("Nicholas Sparks");
        title.setPublicationYear(2007);
        title.getCopies().add(copy);
        copy.setTitle(title);
        return title;
    }

    @Test
    void testFindAllTitles() {
        // Given
        Title title1 = createTitle1();
        Title title2 = createTitle2();
        titleRepository.save(title1);
        titleRepository.save(title2);
        // When
        List<Title> resultList = titleRepository.findAll();
        // Then
        assertEquals(2, resultList.size());
    }

    @Test
    void testFindTitleById() {
        // Given
        Title title = createTitle1();
        titleRepository.save(title);
        // When
        Optional<Title> titleReceived = titleRepository.findById(title.getId());
        // Then
        assertTrue(titleReceived.isPresent());
        assertEquals("Message in a bottle", titleReceived.get().getTitle());
    }

    @Test
    void testFindTitleByPublicationYear() {
        // Given
        Title title = createTitle1();
        titleRepository.save(title);
        // When
        List<Title> resultList = titleRepository.findByPublicationYear(1998);
        // Then
        assertEquals(1, resultList.size());
    }

    @Test
    void testFindTitleByTitle() {
        // Given
        Title title = createTitle1();
        titleRepository.save(title);
        // When
        Title titleReceived = titleRepository.findByTitle("Message in a bottle");
        // Then
        assertTrue(titleRepository.existsById(titleReceived.getId()));
        assertEquals(title.getTitle(), titleReceived.getTitle());
    }

    @Test
    void testTitleSave() {
        // Given
        Title title = createTitle1();
        // When
        titleRepository.save(title);
        Long titleId = title.getId();
        // Then
        Optional<Title> readTitle = titleRepository.findById(titleId);
        assertTrue(readTitle.isPresent());
        assertNotEquals(0, titleId);
    }

    @Test
    void testDeleteTitleById() {
        // Given
        Title title = createTitle1();
        titleRepository.save(title);
        Long titleId = title.getId();
        // When
        titleRepository.deleteById(titleId);
        // Then
        assertEquals(Optional.empty(), titleRepository.findById(titleId));
        assertFalse(titleRepository.existsById(titleId));
    }
}
