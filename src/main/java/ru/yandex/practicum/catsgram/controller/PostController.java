package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.dto.post.NewPostRequest;
import ru.yandex.practicum.catsgram.dto.post.PostDto;
import ru.yandex.practicum.catsgram.dto.post.UpdatePostRequest;
import ru.yandex.practicum.catsgram.enums.SortOrder;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPostById(@PathVariable("postId") long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto createPost(@RequestBody NewPostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @PutMapping("/{postId}")
    public PostDto updatePost(@PathVariable("postId") long postId, @RequestBody UpdatePostRequest request) {
        return postService.updatePost(postId, request);
    }

    @GetMapping
    public Collection<PostDto> findAll(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort) {
        SortOrder sortOrder = SortOrder.from(sort);
        if (size <= 0) {
            throw new ParameterNotValidException("size", "Некорректный размер выборки. Размер должен быть больше нуля");
        }
        if (from < 0) {
            throw new ParameterNotValidException("from", "Некорректный номер начала выборки. Номер должен быть равен или больше нуля");
        }
        if (sortOrder == null) {
            throw new ParameterNotValidException("sort", "Некорректный параметр сортировки. Параметр должен быть одним из - " +
                    SortOrder.getAllNames());
        }
        return postService.getPosts(from, size, sortOrder);
    }
}
