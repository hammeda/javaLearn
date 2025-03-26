package fr.javalearn.game.services;

import fr.javalearn.game.dtos.SubmissionDto;
import fr.javalearn.game.entities.Submission;
import fr.javalearn.game.generic.services.GenericServiceImpl;
import fr.javalearn.game.mapper.SubmissionMapper;
import fr.javalearn.game.repositories.SubmissionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl extends GenericServiceImpl<Submission, SubmissionRepository, SubmissionDto, SubmissionMapper> implements SubmissionService {
    public SubmissionServiceImpl(SubmissionRepository repository, SubmissionMapper mapper) {
        super(repository, mapper);
    }
}