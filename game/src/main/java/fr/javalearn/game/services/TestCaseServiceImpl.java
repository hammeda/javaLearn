package fr.javalearn.game.services;

import fr.javalearn.game.dtos.TestCaseDto;
import fr.javalearn.game.entities.TestCase;
import fr.javalearn.game.generic.services.GenericServiceImpl;
import fr.javalearn.game.mapper.TestCaseMapper;
import fr.javalearn.game.repositories.TestCaseRepository;
import org.springframework.stereotype.Service;


@Service
public class TestCaseServiceImpl extends GenericServiceImpl<TestCase, TestCaseRepository, TestCaseDto, TestCaseMapper> implements TestCaseService {
    public TestCaseServiceImpl(TestCaseRepository repository, TestCaseMapper mapper) {
        super(repository, mapper);
    }
}
