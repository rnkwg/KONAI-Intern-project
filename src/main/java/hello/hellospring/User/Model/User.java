package hello.hellospring.User.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.Post.Model.Comment;
import hello.hellospring.Post.Model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="USERINFO")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR")
    @Column(name = "USER_ID")
    private long userId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="LOCATION_USER_ID")
    private LocationUser locationUser;

    private String id;

    private String password;
    private String nickname;

    private String role;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("postId asc")
    @JsonIgnore
    private Set<Post> postList;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("commentId asc")
    @JsonIgnore
    private Set<Comment> commentList;

    public User() {
    }

    @Builder
    public User(LocationUser locationUser, String id, String password, String nickname, String role) {
        this.locationUser = locationUser;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public User(String id, String password, String nickname, String role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", locationUser=" + locationUser +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", postList=" + postList +
                ", commentList=" + commentList +
                '}';
    }
}
