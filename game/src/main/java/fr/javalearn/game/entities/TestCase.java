package fr.javalearn.game.entities;

import fr.javalearn.game.generic.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class TestCase extends BaseEntity {
    private String input;
    private String output;
    @ManyToOne
    private Exercice exercice;
}
