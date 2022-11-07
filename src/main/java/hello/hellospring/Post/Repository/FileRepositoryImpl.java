package hello.hellospring.Post.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Post.Model.File;
import hello.hellospring.Post.Model.QFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public FileRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(File.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

//    글의 첨부파일 변경
    public void updateFile(long postId, File newFile) {
        QFile file = QFile.file;
        jpaQueryFactory
                .update(file)
                .set(file.originName, newFile.getOriginName())
                .set(file.name, newFile.getName())
                .set(file.path, newFile.getPath())
                .where(file.post.postId.eq(postId))
                .execute();
    }
}
