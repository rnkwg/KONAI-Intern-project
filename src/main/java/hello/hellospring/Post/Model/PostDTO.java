package hello.hellospring.Post.Model;

import hello.hellospring.Location.Model.Location;
import hello.hellospring.Location.Model.LocationDTO;
import hello.hellospring.User.Model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class PostDTO {
    private long postId;

    private Category category;

    private long categoryId;

    private User user;

    private Location location;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDateTime createDate;

    private String createDateFormat;

    private LocalDateTime updateDate;

    private String updateDateFormat;

    private FileDTO file;

    private long fileId;

    private List<CommentDTO> commentList;

    public PostDTO(long categoryId, String title, String content) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }

    //    DTO -> entity
    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .category(category)
                .user(user)
                .location(location)
                .title(title)
                .content(content)
                .build();
    }

//    entity -> DTO
    public PostDTO(Post post) {
        postId = post.getPostId();
        categoryId = post.getCategory().getCategoryId();
        user = post.getUser();
        location = post.getLocation();
        title = post.getTitle();
        content = post.getContent();

        createDate = post.getCreateDate();
        createDateFormat = createDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        if(post.getUpdateDate() != null) {
            updateDate = new java.sql.Timestamp(post.getUpdateDate().getTime()).toLocalDateTime();
        }
        if(updateDate != null) {
            updateDateFormat = updateDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        }

        if(post.getCommentList() != null) {
            List<Comment> comments = post.getCommentList();
            List<CommentDTO> commentDTOs = new ArrayList<>();
            for (Comment comment : comments) {
                commentDTOs.add(new CommentDTO(comment));
            }
            commentList = commentDTOs;
        }

        if(post.getFile() != null) {
            file = new FileDTO(post.getFile());
            fileId = post.getFile().getFileId();
        }
    }
}
