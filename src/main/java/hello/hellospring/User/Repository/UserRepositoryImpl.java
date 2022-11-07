package hello.hellospring.User.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.User.Model.QUser;
import hello.hellospring.User.Model.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory, PasswordEncoder passwordEncoder) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.passwordEncoder = passwordEncoder;
    }

    //    사용자의 password 변경
    public void updateUserPassword(String id, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        QUser user = QUser.user;
        jpaQueryFactory
                .update(user)
                .set(user.password, encodedPassword)
                .where(user.id.eq(id))
                .execute();
    }


    //    사용자의 nickname 변경
    public void updateUserNickname(String id, String nickname) {
        QUser user = QUser.user;
        jpaQueryFactory
                .update(user)
                .set(user.nickname, nickname)
                .where(user.id.eq(id))
                .execute();
    }

    //    사용자의 location 변경
    public void updateUserLocation(String id, LocationUser locationUser) {
        QUser user = QUser.user;
        jpaQueryFactory
                .update(user)
                .set(user.locationUser, locationUser)
                .where(user.id.eq(id))
                .execute();
    }
}
