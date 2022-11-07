package hello.hellospring.Location.Controller;

import hello.hellospring.Location.Model.LocationDTO;
import hello.hellospring.Location.Service.LocationService;
import hello.hellospring.Post.Model.PostDTO;
import hello.hellospring.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/location")
@Slf4j
@RestController
public class LocationApiController {

    private final LocationService locationService;

    private final PostService postService;

    //    카테고리에 해당하는 글들의 위치 좌표 가져오기
    @GetMapping("/categoryResult/categoryId/{categoryId}")
    public List<LocationDTO> getLocation(@RequestParam(value="categoryId") long categoryId) {
        List<PostDTO> postList = postService.getAllPostByCategoryId(categoryId);
        List<LocationDTO> locationList = locationService.getAllLocationByCategory(postList);
        return locationList;
    }
}
