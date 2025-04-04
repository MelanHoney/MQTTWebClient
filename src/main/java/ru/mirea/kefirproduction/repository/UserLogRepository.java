package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.UserLog;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
