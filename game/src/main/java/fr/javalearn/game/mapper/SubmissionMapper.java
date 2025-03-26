package fr.javalearn.game.mapper;

import fr.javalearn.game.dtos.SubmissionDto;
import fr.javalearn.game.entities.Submission;
import fr.javalearn.game.generic.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubmissionMapper extends GenericMapper<SubmissionDto, Submission> {
}