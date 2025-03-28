package fr.javalearn.game.services;

import fr.javalearn.game.dtos.ExerciceDto;
import fr.javalearn.game.entities.Exercice;
import fr.javalearn.game.generic.services.GenericServiceImpl;
import fr.javalearn.game.mapper.ExerciceMapper;
import fr.javalearn.game.repositories.ExerciceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciceServiceImpl extends GenericServiceImpl<Exercice, ExerciceRepository, ExerciceDto, ExerciceMapper> implements ExerciceService {
    public ExerciceServiceImpl(ExerciceRepository repository, ExerciceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public List<ExerciceDto> findAllExerciceByDifficulty() {
        return toDto(repository.findAllByOrderByDifficultyAsc());
    }
}
