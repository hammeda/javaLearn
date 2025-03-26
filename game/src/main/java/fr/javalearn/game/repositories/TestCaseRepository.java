package fr.javalearn.game.repositories;

import fr.javalearn.game.entities.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
