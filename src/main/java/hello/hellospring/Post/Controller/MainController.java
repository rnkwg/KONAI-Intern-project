package hello.hellospring.Post.Controller;

import hello.hellospring.Post.Model.Category;
import hello.hellospring.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public String main(Model model) {
        List<Category> categoryList = postService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "main/mainPage";
    }
}
