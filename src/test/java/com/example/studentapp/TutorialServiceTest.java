package com.example.studentapp;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.studentapp.exception.TutorialNotFoundException;
import com.example.studentapp.mapper.TutorialMapper;
import com.example.studentapp.model.dto.TutorialDTO;
import com.example.studentapp.model.entity.Tutorial;
import com.example.studentapp.repository.TutorialRepo;
import com.example.studentapp.service.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class TutorialServiceTest {

    @Mock
    private TutorialRepo tutorialRepository;

    @Mock
    private TutorialMapper tutorialMapper;

    @InjectMocks
    private TutorialService tutorialService;

    private Tutorial tutorial;
    private TutorialDTO tutorialDTO;

    @BeforeEach
    void setUp() {

        tutorial = new Tutorial();
        tutorial.setTitle("Test Title");
        tutorial.setDescription("Test Description");
        tutorial.setPublished(true);

        tutorialDTO = TutorialDTO.builder()
                .title("Test Title")
                .description("Test Description")
                .published(true)
                .build();
    }

    @Test
    void testGetAllTutorialsNoTitle() {
        when(tutorialRepository.findAll()).thenReturn(Arrays.asList(tutorial));

        List<TutorialDTO> result = tutorialService.getAllTutorials(null);

        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        verify(tutorialRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTutorialsWithTitle() {
        String title = "Test";
        when(tutorialRepository.findAllByTitleContaining(title)).thenReturn(Arrays.asList(tutorial));

        List<TutorialDTO> result = tutorialService.getAllTutorials(title);

        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        verify(tutorialRepository, times(1)).findAllByTitleContaining(title);
    }

    @Test
    void testGetTutorialByIdFound() {
        long id = 1L;
        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
        when(tutorialMapper.tutorialToTutorialDTO(tutorial)).thenReturn(tutorialDTO);

        TutorialDTO result = tutorialService.getTutorialById(id);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(tutorialRepository, times(1)).findById(id);
    }

    @Test
    void testGetTutorialByIdNotFound() {
        long id = 1L;
        when(tutorialRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TutorialNotFoundException.class, () -> {
            tutorialService.getTutorialById(id);
        });

        assertTrue(exception.getMessage().contains("Tutorial with id=" + id + " not found."));
        verify(tutorialRepository, times(1)).findById(id);
    }

    @Test
    void testCreateTutorial() {
        when(tutorialMapper.tutorialDTOToTutorial(tutorialDTO)).thenReturn(tutorial);
        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);

        String result = tutorialService.createTutorial(tutorialDTO);

        assertEquals("Tutorial was created successfully.", result);
        verify(tutorialRepository, times(1)).save(tutorial);
        verify(tutorialMapper, times(1)).tutorialDTOToTutorial(tutorialDTO);
    }
}
