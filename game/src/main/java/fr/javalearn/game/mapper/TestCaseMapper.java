package fr.javalearn.game.mapper;

import fr.javalearn.game.dtos.TestCaseDto;
import fr.javalearn.game.entities.TestCase;
import fr.javalearn.game.generic.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TestCaseMapper extends GenericMapper<TestCaseDto, TestCase> {
}
