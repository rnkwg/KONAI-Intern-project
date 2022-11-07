package hello.hellospring.Post.Controller;

import hello.hellospring.Post.Model.Category;
import hello.hellospring.Post.Model.PostDTO;
import hello.hellospring.Post.Service.PostService;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostService postService;
    private final UserService userService;

//    글쓰기 폼으로 이동
    @GetMapping("/write")
    public String writeForm(Model model) {
        List<Category> categoryList = postService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "post/write";
    }

//   글 보기
    @GetMapping("/view/postId/{postId}")
    public String view(@PathVariable long postId, ModelMap model) {
        PostDTO postDTO = postService.getPostDTO(postId);
        model.put("postDto", postDTO);
        return "post/view";
    }

    //    update.html 글 수정하기 폼으로 이동
    @GetMapping("/update/postId/{postId}")
    public String updateForm(@PathVariable long postId, Model model) {
        PostDTO postDTO = postService.getPostDTO(postId);
        model.addAttribute("postDto", postDTO);
        return "post/update";
    }

//    사용자가 작성한 글 목록 보기
    @GetMapping("/list/user")
    public String listByUser(Principal principal, ModelMap model) {
        User user = userService.getById(principal.getName());
        model.put("userId", user.getUserId());
        return "post/userList";
    }

    //    카테고리 별 글 목록 보기 이동
    @GetMapping("/list/categoryId/{categoryId}")
    public String list(@PathVariable long categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "post/list";
    }

    /**
     * 검색하는 폼으로 이동하기
     */
    @GetMapping("/search")
    public String search() {
        return "post/searchList";
    }

    /**
     * 차트 페이지로 이동하기
     * @return
     */
    @GetMapping("/chart")
    public String chart() {
        return "post/chart";
    }
}
