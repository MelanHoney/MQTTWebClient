package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.MachineType;

import java.util.Optional;

@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, Long> {
    Optional<MachineType> findByName(String name);
}
