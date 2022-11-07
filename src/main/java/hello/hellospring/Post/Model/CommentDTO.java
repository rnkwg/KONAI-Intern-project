package hello.hellospring.Post.Model;

import hello.hellospring.User.Model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private long commentId;

    private Post post;

    private long postId;

    private User user;

    private String content;

    private LocalDateTime createDate;

    private String createDateFormat;

//    DTO -> Entity
    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .post(post)
                .user(user)
                .content(content)
                .build();
    }

//    Entity -> DTO
    public CommentDTO(Comment comment) {
        commentId = comment.getCommentId();
        post = comment.getPost();
        postId = comment.getPost().getPostId();
        user = comment.getUser();
        content = comment.getContent();
        createDate = comment.getCreateDate();
        createDateFormat = createDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
