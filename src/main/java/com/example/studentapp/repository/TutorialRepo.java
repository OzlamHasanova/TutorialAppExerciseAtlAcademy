package com.example.studentapp.repository;

import com.example.studentapp.model.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepo extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findAllByTitleContaining(String title);
    List<Tutorial> findAllByPublished(boolean published);
}
