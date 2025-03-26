package fr.javalearn.game.dtos;

import fr.javalearn.game.entities.Difficulty;
import fr.javalearn.game.generic.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciceDto extends BaseDto implements Serializable {
    private String titre;
    private String description;
    private Difficulty difficulty;
    private List<TestCaseDto> testCases;
}
