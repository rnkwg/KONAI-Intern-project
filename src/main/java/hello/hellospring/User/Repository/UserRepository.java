package hello.hellospring.User.Repository;

import hello.hellospring.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    //    id와 일치하는 user 수 세기(회원가입 시 중복 id 예외 처리)
    long countById(String id);

    //    nickname과 일치하는 user 수 세기(회원가입 시 중복 nickname 예외 처리)
    long countByNickname(String nickname);

    //    id로 User 찾기
    User findById(String id);

//    userId로 User 찾기
    User findByUserId(long userId);
}