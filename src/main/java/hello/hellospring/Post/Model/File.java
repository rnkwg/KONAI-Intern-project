package hello.hellospring.Post.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "FILE_SEQ_GENERATOR",
        sequenceName = "FILE_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="ATTACHEDFILE")
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "FILE_SEQ_GENERATOR")
    @Column(name="FILE_ID")
    private long fileId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="POST_ID")
    private Post post;

//    @Column(name="EXT")
//    private String ext;

    @Column(name="NAME")
    private String name;

    @Column(name="ORIGIN_NAME")
    private String originName;

    @Column(name="PATH")
    private String path;

    public File() {}

    @Builder
    public File(long fileId, Post post, String name, String originName, String path) {
        this.fileId = fileId;
        this.post = post;
        this.name = name;
        this.originName = originName;
        this.path = path;
    }
}
