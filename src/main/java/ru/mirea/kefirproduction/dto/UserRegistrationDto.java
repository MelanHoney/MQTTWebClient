package ru.mirea.kefirproduction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.kefirproduction.types.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDto {
    private String email;
    private String rawPassword;
    private String name;
    private Role role;
}
