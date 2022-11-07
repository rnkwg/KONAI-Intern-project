package hello.hellospring.Batch.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Batch.Model.PostCount;
import hello.hellospring.Batch.Model.QPostCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class PostCountRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCountRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(PostCount.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<PostCount> getPostCountByMonth(LocalDateTime startDate, LocalDateTime endDate, String categoryName) {
        QPostCount postCount = QPostCount.postCount;
        List<PostCount> postCounts = jpaQueryFactory
                .select(postCount)
                .from(postCount)
                .where(postCount.date.between(startDate, endDate)
                        .and(postCount.categoryName.eq(categoryName)))
                .orderBy(postCount.date.asc())
                .fetch();

        return postCounts;
    }

}
