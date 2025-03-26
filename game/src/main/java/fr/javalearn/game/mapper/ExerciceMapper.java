package fr.javalearn.game.mapper;

import fr.javalearn.game.dtos.ExerciceDto;
import fr.javalearn.game.entities.Exercice;
import fr.javalearn.game.generic.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExerciceMapper extends GenericMapper<ExerciceDto, Exercice> {
}
