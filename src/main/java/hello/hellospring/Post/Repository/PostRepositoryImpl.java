package hello.hellospring.Post.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Post.Model.Post;
import hello.hellospring.Post.Model.QPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Slf4j
public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

//    글의 title 변경
    public void updatePostTitle(long postId, String title) {
        QPost post = QPost.post;
        jpaQueryFactory
                .update(post)
                .set(post.title, title)
                .where(post.postId.eq(postId))
                .execute();
    }

//    글의 content 변경
    public void updatePostContent(long postId, String content) {
        QPost post = QPost.post;
        jpaQueryFactory
                .update(post)
                .set(post.content, content)
                .where(post.postId.eq(postId))
                .execute();
    }

//    글의 updateDate 변경
    public void updatePostUpdateDate(long postId, Date updateDate) {
        QPost post = QPost.post;
        jpaQueryFactory
                .update(post)
                .set(post.updateDate, updateDate)
                .where(post.postId.eq(postId))
                .execute();
    }
}
