package com.example.studentapp.service;

import com.example.studentapp.exception.TutorialNotFoundException;
import com.example.studentapp.mapper.TutorialMapper;
import com.example.studentapp.model.dto.TutorialDTO;
import com.example.studentapp.model.entity.Tutorial;
import com.example.studentapp.repository.TutorialRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepo tutorialRepository;
    private final TutorialMapper tutorialMapper;

    //    @Cacheable(value = "tutorial", key = "#title")
//    @Scheduled(fixedRate = 5000)
    public List<TutorialDTO> getAllTutorials(String title) {
        log.info("Fetching all tutorials with title: {}", title);
        List<Tutorial> tutorials = (title == null) ?
                tutorialRepository.findAll() :
                tutorialRepository.findAllByTitleContaining(title);
        return tutorials.stream()
                .map(tutorial -> TutorialDTO.builder()
                        .title(tutorial.getTitle())
                        .description(tutorial.getDescription())
                        .published(tutorial.isPublished())
                        .build())
                .collect(Collectors.toList());
    }

    @Cacheable(value = "tutorial", key = "#id")
    public TutorialDTO getTutorialById(long id) {
        log.info("Fetching tutorial by id: {}", id);
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial with id=" + id + " not found."));
        return tutorialMapper.tutorialToTutorialDTO(tutorial);
    }

    public String createTutorial(@Valid TutorialDTO tutorialDTO) {
        log.info("Creating new tutorial with title: {}", tutorialDTO.getTitle());
        Tutorial tutorial = tutorialMapper.tutorialDTOToTutorial(tutorialDTO);
        tutorialRepository.save(tutorial);
        return "Tutorial was created successfully.";
    }

    public String updateTutorial(long id, @Valid TutorialDTO tutorialDTO) {
        log.info("Updating tutorial with id: {}", id);
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial with id=" + id + " not found."));
        tutorial.setTitle(tutorialDTO.getTitle());
        tutorial.setDescription(tutorialDTO.getDescription());
        tutorial.setPublished(tutorialDTO.isPublished());
        tutorialRepository.save(tutorial);
        return "Tutorial was updated successfully.";
    }

    public String deleteTutorial(long id) {
        log.info("Deleting tutorial with id: {}", id);
        if (!tutorialRepository.existsById(id)) {
            throw new TutorialNotFoundException("Cannot find Tutorial with id=" + id);
        }
        tutorialRepository.deleteById(id);
        return "Tutorial was deleted successfully.";
    }

    public String deleteAllTutorials() {
        log.info("Deleting all tutorials");
        tutorialRepository.deleteAll();
        return "All tutorials deleted successfully.";
    }

    public List<TutorialDTO> findByPublished() {
        log.info("Fetching all published tutorials");
        List<Tutorial> tutorials = tutorialRepository.findAllByPublished(true);
        return tutorialMapper.tutorialsToTutorialDTOs(tutorials);
    }

}
