package hello.hellospring.Post.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.hellospring.User.Model.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@SequenceGenerator(
        name = "COMMENT_SEQ_GENERATOR",
        sequenceName = "COMMENT_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="COMMENTS")
public class Comment extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "COMMENT_SEQ_GENERATOR")
    @Column(name="COMMENT_ID")
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    private String content;

    public Comment() {

    }

    @Builder
    public Comment(long commentId, Post post, User user, String content) {
        this.commentId = commentId;
        this.post = post;
        this.user = user;
        this.content = content;
    }
}
