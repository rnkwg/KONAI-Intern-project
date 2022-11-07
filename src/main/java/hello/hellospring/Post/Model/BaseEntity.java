package hello.hellospring.Post.Model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, name="CREATE_DATE")
    private LocalDateTime createDate;

//    @Column(name="UPDATE_DATE")
//    @LastModifiedDate
//    private LocalDateTime updateDate;
}