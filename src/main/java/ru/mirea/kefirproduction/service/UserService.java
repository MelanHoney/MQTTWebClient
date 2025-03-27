package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dto.UserRegistrationDto;
import ru.mirea.kefirproduction.model.User;
import ru.mirea.kefirproduction.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User registerUser(UserRegistrationDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getRawPassword()))
                .role(dto.getRole())
                .build();
        System.out.println(passwordEncoder.matches(dto.getRawPassword(), user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
