package fr.javalearn.game.dtos;

import fr.javalearn.game.entities.Exercice;
import fr.javalearn.game.entities.User;
import fr.javalearn.game.generic.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto extends BaseDto {
    private User user;
    private Exercice exercice;
    private String codeSubmitted;
    private boolean passed;
    private String feedback;
    private LocalDateTime submissionDate;

}
