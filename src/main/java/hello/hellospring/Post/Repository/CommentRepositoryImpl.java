package hello.hellospring.Post.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Post.Model.Comment;
import hello.hellospring.Post.Model.QComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //    댓글의 내용 변경
    public void updateComment(long commentId, String content) {
        QComment comment = QComment.comment;
        jpaQueryFactory
                .update(comment)
                .set(comment.content, content)
                .where(comment.commentId.eq(commentId))
                .execute();
    }
}
