package hello.hellospring.User.Controller;

import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserApiController {
    private final UserService userService;

    //    회원가입 시 사용자가 입력한 정보를 받는다
    @PostMapping("/join")
    public long join(@RequestBody UserDTO userDTO) {
        try {
            return userService.join(userDTO);
        } catch (Exception e) {
            throw e;
        }
    }

    //    중복된 id가 있는 회원 X
    @PostMapping("/idCheck")
    public int idCheck(@RequestParam String id) {
        try {
            int idCheck = (int) userService.validateUserId(id);
            return idCheck;
        } catch(Exception e) {
            throw e;
        }
    }

    //    중복된 nickname이 있는 회원 X
    @PostMapping("/nicknameCheck")
    public int nicknameCheck(@RequestParam("nickname") String nickname) throws Exception {
        try {
            int nicknameCheck = (int) userService.validateUserNickname(nickname);
            return nicknameCheck;
        } catch (Exception e) {
            throw e;
        }
    }

    //    사용자 정보 수정
    @PostMapping("/myPage")
    public ResponseEntity<UserDTO> myPage(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }
}
