package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.ActuatorState;

@Repository
public interface ActuatorStateRepository extends JpaRepository<ActuatorState, Long> {
}
