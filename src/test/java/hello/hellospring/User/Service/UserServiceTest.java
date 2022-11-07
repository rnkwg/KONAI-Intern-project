package hello.hellospring.User.Service;

import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.Location.Repository.LocationUserRepository;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Repository.UserRepository;
import hello.hellospring.User.Repository.UserRepositoryImpl;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

//@ExtendWith(SpringExtension.class)
//@Import(UserService.class)
@SpringBootTest
//@Transactional
public class UserServiceTest {
//    @Autowired
//    UserService userService;
//
//    @Test
//    @DisplayName("회원가입")
//    @Transactional
//    public void join() {
////        given
//        UserDTO testUser = new UserDTO();
//        testUser.setId("testUser");
//        testUser.setPassword("testUser1234");
//        testUser.setNickname("testUserUser");
//        testUser.setRole("ROLE_USER");
//        testUser.setLocationUserId(1L);
//
////        when
//        userService.join(testUser);
//
////        then
//        UserDTO resultUser = userService.getUserDtoById(testUser.getId());
//        assertThat(resultUser.getId()).isEqualTo(testUser.getId());
//        assertThat(resultUser.getPassword()).isEqualTo(testUser.getPassword());
//        assertThat(resultUser.getNickname()).isEqualTo(testUser.getNickname());
//        assertThat(resultUser.getRole()).isEqualTo(testUser.getRole());
//    }
//
//    @Test
//    @DisplayName("회원 정보 수정")
//    @Transactional
//    public void update() {
////        given
//        join();
//        User testUser = userService.getById("testUser");
//        testUser.setPassword("testUser12341234");
//        testUser.setNickname("testUserUserUser");
//        UserDTO testUserDTO = new UserDTO(testUser);
//
////        when
//        userService.updateUser(testUserDTO);
//
////        then
//        UserDTO resultUser = userService.getUserDtoById(testUser.getId());
//        System.out.println(resultUser.getNickname());
//        System.out.println(testUser.getNickname());
//        assertThat(resultUser.getNickname()).isEqualTo(testUser.getNickname());
//    }
}
