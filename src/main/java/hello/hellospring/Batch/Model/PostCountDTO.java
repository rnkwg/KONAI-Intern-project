package hello.hellospring.Batch.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCountDTO {

    private LocalDateTime date;

    private String categoryName;

    private long count;

//    Entity -> DTO
    public PostCountDTO(PostCount postCount) {
        date = postCount.getDate();
        categoryName = postCount.getCategoryName();
        count = postCount.getCount();
    }
}
