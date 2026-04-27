package ru.yandex.practicum.catsgram.model;

import lombok.*;

@Data
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString
public class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private String filePath;
}
