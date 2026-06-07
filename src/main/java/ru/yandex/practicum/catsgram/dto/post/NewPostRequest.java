package ru.yandex.practicum.catsgram.dto.post;

import lombok.Data;

@Data
public class NewPostRequest {
    private long authorId;
    private String description;
}
