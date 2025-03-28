package fr.javalearn.game.services;

import fr.javalearn.game.dtos.ExerciceDto;
import fr.javalearn.game.generic.services.GenericService;

import java.util.List;


public interface ExerciceService extends GenericService<ExerciceDto> {
    List<ExerciceDto> findAllExerciceByDifficulty();
}
