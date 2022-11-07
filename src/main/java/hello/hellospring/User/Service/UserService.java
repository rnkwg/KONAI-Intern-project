package hello.hellospring.User.Service;

import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.Location.Repository.LocationUserRepository;
import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Repository.UserRepository;
import hello.hellospring.User.Repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public final UserRepositoryImpl userRepositoryImpl;

    public final LocationUserRepository locationUserRepository;

    //dto -> entity 해서 레파지토리에 보낸다
    @Transactional
    public Long join(UserDTO userDTO) {
//        사용자가 입력한 password 암호화
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        userDTO.setRole("ROLE_USER");
        User user = userDTO.toEntity();

//        user 에 locationUser 넣어주기
        LocationUser locationUser = getLocationUser(userDTO.getLocationUserId());
        user.setLocationUser(locationUser);

        userRepository.save(user);

        return user.getUserId();
    }

    //사용자가 입력한 아이디랑 동일한 아이디가 있는지 확인하기
    public long validateUserId(String id) {
        return userRepository.countById(id);
    }

    //사용자가 입력한 닉네임이랑 동일한 닉네임이 있는지 확인하기
    public long validateUserNickname(String nickname) {
        return userRepository.countByNickname(nickname);
    }

    //    userId로 user
    public User getByUserId(long userId) {
        User user = userRepository.findByUserId(userId);
        return user;
    }

//    id로 user
    public User getById(String id) {
        User user = userRepository.findById(id);
        return user;
    }

    public UserDTO getUserDtoById(String id) {
        User user = userRepository.findById(id);
        return new UserDTO(user);
    }

    /**
     * 사용자 정보 수정하기
     * @param userDTO
     */
    @Transactional
    public void updateUser(UserDTO userDTO) {
//        password 변경
        if(userDTO.getPassword() != "") {
            userRepositoryImpl.updateUserPassword(userDTO.getId(), userDTO.getPassword());
        }
//        nickname 변경
        if (userDTO.getNickname() != "") {
            userRepositoryImpl.updateUserNickname(userDTO.getId(), userDTO.getNickname());
        }
//        location 변경
        if(userDTO.getLocationUserId() != 0) {
            LocationUser locationUser = locationUserRepository.findByLocationUserId(userDTO.getLocationUserId());
            userRepositoryImpl.updateUserLocation(userDTO.getId(), locationUser);
        }
    }

//    사용자 마이페이지에서 password 변경하기
//    @Transactional
//    public void updateUserPassword(UserDTO userDTO) {
//       userRepositoryImpl.updateUserPassword(userDTO.getId(), userDTO.getPassword());
//
////        세션 변경
////        Authentication authentication = authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(originUser.getId(), originUser.getPassword()));
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    /**
     * locationUser 전체 가져오기 회원가입 창에서 출력하기 위함
     * @return
     */
    public List<LocationUser> getAllLocationUser() {
        return locationUserRepository.findAll();
    }

    /**
     * locationUserId로 locationUser 가져오기
     * @param id
     * @return
     */
    public LocationUser getLocationUser(long id) {
        return locationUserRepository.findByLocationUserId(id);
    }
}
