package fr.javalearn.game.entities;

import fr.javalearn.game.generic.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class Exercice extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String titre;
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    @Column(nullable = false)
    private String resultatAttendu;

    @ElementCollection
    @CollectionTable(name = "exercise_test_cases", joinColumns = @JoinColumn(name = "exercise_id"))
    private List<TestCase> testCases;
}
