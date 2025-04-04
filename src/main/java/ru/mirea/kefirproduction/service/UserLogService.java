package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.model.User;
import ru.mirea.kefirproduction.model.UserLog;
import ru.mirea.kefirproduction.repository.UserLogRepository;
import ru.mirea.kefirproduction.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLogService {

    private final UserLogRepository userLogRepository;
    private final UserService userService;

    public void logUserAction(String action, String userEmail) {
        UserLog userLog = UserLog.builder()
                .user(userService.getUserByEmail(userEmail))
                .action(action)
                .build();
        userLogRepository.save(userLog);
    }
}
