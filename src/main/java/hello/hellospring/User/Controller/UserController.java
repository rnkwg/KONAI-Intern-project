package hello.hellospring.User.Controller;

import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    //    login.html 로그인 폼으로 이동
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    //    join.html 회원가입 폼으로 이동
    @GetMapping("/join")
    public String joinForm(Model model) {
        List<LocationUser> locationUserList = userService.getAllLocationUser();
        model.addAttribute("locationUserList", locationUserList);
        return "user/join";
    }

//    myPage.html 마이페이지 폼으로 이동
    @GetMapping("/myPage")
    public String myPageForm(Principal principal, Model model) {
        UserDTO userDTO = userService.getUserDtoById(principal.getName());
        List<LocationUser> locationUserList = userService.getAllLocationUser();
        model.addAttribute("userDto", userDTO);
        model.addAttribute("locationUserList", locationUserList);
        return "user/myPage";
    }
}
