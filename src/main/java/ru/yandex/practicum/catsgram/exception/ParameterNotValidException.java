package ru.yandex.practicum.catsgram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParameterNotValidException extends IllegalArgumentException {
    private String parameter;
    private String reason;
}
