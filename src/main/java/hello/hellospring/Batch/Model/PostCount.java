package hello.hellospring.Batch.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "POSTCOUNT_SEQ_GENERATOR",
        sequenceName = "POSTCOUNT_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="POSTCOUNT")
public class PostCount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTCOUNT_SEQ_GENERATOR")
    @Column(name="POSTCOUNT_ID")
    private long postCountId;

    @Column(name="BATCH_DATE")
    private LocalDateTime date;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="POST_COUNT")
    private long count;

    public PostCount() {
    }

}
