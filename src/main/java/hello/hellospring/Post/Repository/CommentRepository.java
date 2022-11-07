package hello.hellospring.Post.Repository;

import hello.hellospring.Post.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(long commentId);

    List<Comment> findByPostPostId(long postId);

    void deleteCommentByCommentId(long commentId);
}
