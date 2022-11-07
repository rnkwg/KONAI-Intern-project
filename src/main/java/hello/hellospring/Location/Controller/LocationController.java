package hello.hellospring.Location.Controller;

import hello.hellospring.Post.Model.Category;
import hello.hellospring.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
@Slf4j
public class LocationController {
    private final PostService postService;

    //    카테고리 별 위치 다각형 보기 폼으로 이동
    @GetMapping("/category")
    public String categoryForm(Model model) {
        List<Category> categoryList = postService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "location/category";
    }
}
