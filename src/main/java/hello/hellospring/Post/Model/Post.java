package hello.hellospring.Post.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.hellospring.Location.Model.Location;
import hello.hellospring.User.Model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "POST_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="POST")
public class Post extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR")
    @Column(name="POST_ID")
    private long postId;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    @JsonIgnore
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    private String title;
    private String content;

    @Column(name="UPDATE_DATE")
    private Date updateDate;

    @OneToMany(mappedBy="post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("commentId asc")
    @JsonIgnore
    private List<Comment> commentList;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private File file;

    public Post() {
    }

    @Builder
    public Post(long postId, Category category, User user, Location location, String title,
                String content, List<Comment> commentList, File file) {
        this.postId = postId;
        this.category = category;
        this.user = user;
        this.location = location;
        this.title = title;
        this.content = content;
        this.commentList = commentList;
        this.file = file;
    }
}
