package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.OpcData;

@Repository
public interface OpcDataRepository extends JpaRepository<OpcData, Long> {
}
