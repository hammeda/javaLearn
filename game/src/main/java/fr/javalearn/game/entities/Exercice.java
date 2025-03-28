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

    @Column(nullable = false, unique = true)
    private int difficulty;

    @Column(nullable = false)
    private String nomCours;

    @Column(nullable = false)
    private String descriptionCours;

    @Column(nullable = false)
    private String exempleCours;

    @Column(nullable = false)
    private String retourExempleCours;

    @Column(nullable = false)
    private String correction;

    @OneToMany(mappedBy="exercice")
    private List<TestCase> testCases;
}
