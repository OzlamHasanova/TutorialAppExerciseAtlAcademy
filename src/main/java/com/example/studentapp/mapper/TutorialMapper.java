package com.example.studentapp.mapper;

import com.example.studentapp.model.dto.TutorialDTO;
import com.example.studentapp.model.entity.Tutorial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring")
public interface TutorialMapper {

    TutorialMapper INSTANCE = Mappers.getMapper(TutorialMapper.class);

    TutorialDTO tutorialToTutorialDTO(Tutorial tutorial);


    Tutorial tutorialDTOToTutorial(TutorialDTO tutorialDTO);

    List<TutorialDTO> tutorialsToTutorialDTOs(List<Tutorial> tutorials);

    List<Tutorial> tutorialDTOsToTutorials(List<TutorialDTO> tutorialDTOs);
}
