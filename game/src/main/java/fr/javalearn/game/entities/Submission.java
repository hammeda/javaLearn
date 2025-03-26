package fr.javalearn.game.entities;

import fr.javalearn.game.generic.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submissions")
public class Submission extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercice exercice;

    @Column(nullable = false, length = 10000)
    private String codeSubmitted;

    private boolean passed;

    private String feedback;

    private LocalDateTime submissionDate = LocalDateTime.now();

}

