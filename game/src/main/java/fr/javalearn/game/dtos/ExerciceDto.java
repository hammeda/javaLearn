package fr.javalearn.game.dtos;

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
    private int difficulty;
    private String nomCours;
    private String descriptionCours;
    private String exempleCours;
    private String retourExempleCours;
    private String correction;
    private List<TestCaseDto> testCases;
}
