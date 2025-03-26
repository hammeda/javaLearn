package fr.javalearn.game.repositories;

import fr.javalearn.game.entities.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciceRepository extends JpaRepository<Exercice, Long> {
}
