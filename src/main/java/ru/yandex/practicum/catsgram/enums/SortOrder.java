package ru.yandex.practicum.catsgram.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum SortOrder {
    ASCENDING("ascending", "asc"),
    DESCENDING("descending", "desc");

    private final String fullName;
    private final String shortName;

    public static SortOrder from(String order) {
        return switch (order.toLowerCase()) {
            case "ascending", "asc" -> ASCENDING;
            case "descending", "desc" -> DESCENDING;
            default -> null;
        };
    }

    public static List<String> getAllNames() {
        return Arrays.stream(values())
                .flatMap(order -> Stream.of(order.getFullName(), order.getShortName()))
                .toList();
    }
}