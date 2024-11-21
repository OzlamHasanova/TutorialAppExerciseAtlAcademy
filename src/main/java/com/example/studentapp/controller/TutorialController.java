package com.example.studentapp.controller;

import com.example.studentapp.model.dto.TutorialDTO;
import com.example.studentapp.service.TutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorials")
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService tutorialService;

    @GetMapping
    public ResponseEntity<List<TutorialDTO>> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialDTO> tutorials = tutorialService.getAllTutorials(title);
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorialDTO> getTutorialById(@PathVariable("id") long id) {
        TutorialDTO tutorial = tutorialService.getTutorialById(id);
        if (tutorial != null) {
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createTutorial(@RequestBody TutorialDTO tutorial) {
        String response = tutorialService.createTutorial(tutorial);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialDTO tutorial) {
        String response = tutorialService.updateTutorial(id, tutorial);
        if (response.startsWith("Cannot")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
        String response = tutorialService.deleteTutorial(id);
        if (response.startsWith("Cannot find")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTutorials() {
        String response = tutorialService.deleteAllTutorials();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<List<TutorialDTO>> findByPublished() {
        List<TutorialDTO> tutorials = tutorialService.findByPublished();
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
