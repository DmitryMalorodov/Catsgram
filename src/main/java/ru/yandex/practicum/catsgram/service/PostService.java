package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dto.post.NewPostRequest;
import ru.yandex.practicum.catsgram.dto.post.PostDto;
import ru.yandex.practicum.catsgram.dto.post.UpdatePostRequest;
import ru.yandex.practicum.catsgram.enums.SortOrder;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.PostMapper;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostDto getPostById(long postId) {
        return postRepository.findById(postId)
                .map(PostMapper::mapToPostDto)
                .orElseThrow(() -> new NotFoundException("Пост не найден с ID: " + postId));
    }

    public List<PostDto> getPosts(int from, int size, SortOrder sort) {
        return postRepository.findAll(from, size, sort)
                .stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    public PostDto createPost(NewPostRequest request) {
        if (userService.getUserById(request.getAuthorId()) == null) {
            throw new ConditionsNotMetException(String.format("Автор с id = <%d> не найден", request.getAuthorId()));
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new ConcurrentModificationException("Описание не может быть пустым");
        }

        Post post = PostMapper.mapToPost(request);

        post = postRepository.save(post);

        return PostMapper.mapToPostDto(post);
    }

    public PostDto updatePost(long postId, UpdatePostRequest request) {
        Post updatedPost = postRepository.findById(postId)
                .map(post -> PostMapper.updatePostFields(post, request))
                .orElseThrow(() -> new NotFoundException("Пост не найден"));
        updatedPost = postRepository.update(updatedPost);
        return PostMapper.mapToPostDto(updatedPost);
    }








    private final Map<Long, Post> posts = new HashMap<>();

    public Post findById(Long id) {
        Post post = posts.get(id);
        if (post != null) {
            return post;
        } else {
            throw new NotFoundException(String.format("Пост с id = <%d> не найден", id));
        }
    }
}
