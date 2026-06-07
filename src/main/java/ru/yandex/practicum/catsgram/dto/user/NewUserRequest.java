package ru.yandex.practicum.catsgram.dto.user;

import lombok.Data;

@Data
public class NewUserRequest {
    private String username;
    private String email;
    private String password;
}
