package fr.javalearn.game.dtos;

import fr.javalearn.game.generic.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto extends BaseDto {
    private String input;
    private String output;
}
