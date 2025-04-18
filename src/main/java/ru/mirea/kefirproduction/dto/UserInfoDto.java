package ru.mirea.kefirproduction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.kefirproduction.types.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String name;
    private Role role;
}
