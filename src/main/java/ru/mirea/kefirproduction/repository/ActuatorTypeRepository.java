package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.ActuatorType;

import java.util.Optional;

@Repository
public interface ActuatorTypeRepository extends JpaRepository<ActuatorType, Long> {
    Optional<ActuatorType> findByName(String typeName);
}
