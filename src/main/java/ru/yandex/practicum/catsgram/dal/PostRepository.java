package ru.yandex.practicum.catsgram.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.catsgram.enums.SortOrder;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository extends BaseRepository<Post> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM posts ORDER BY post_date ? LIMIT ? OFFSET ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM posts WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO posts(author_id, description, post_date)" +
            "VALUES (?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE posts SET description = ? WHERE id = ?";

    public PostRepository(JdbcTemplate jdbc, RowMapper<Post> mapper) {
        super(jdbc, mapper);
    }

    public Optional<Post> findById(long postId) {
        return findOne(FIND_BY_ID_QUERY, postId);
    }

    public List<Post> findAll(int from, int size, SortOrder sort) {
        return findMany(
                FIND_ALL_QUERY,
                sort.getShortName(),
                size,
                from
        );
    }

    public Post save(Post post) {
        long id = insert(
                INSERT_QUERY,
                post.getAuthorId(),
                post.getDescription(),
                post.getPostDate()
        );
        post.setId(id);
        return post;
    }

    public Post update(Post post) {
        update(
                UPDATE_QUERY,
                post.getDescription(),
                post.getId()
        );
        return post;
    }
}