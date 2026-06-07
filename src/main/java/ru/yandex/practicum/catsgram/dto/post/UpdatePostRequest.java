package ru.yandex.practicum.catsgram.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String description;

    public boolean hasDescription() {
        return ! (description == null || description.isBlank());
    }
}
