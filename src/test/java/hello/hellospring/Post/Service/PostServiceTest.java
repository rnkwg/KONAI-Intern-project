package hello.hellospring.Post.Service;

import hello.hellospring.Post.Model.PostDTO;
import hello.hellospring.Post.Repository.PostRepository;
import hello.hellospring.Post.Repository.PostRepositoryImpl;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Service.UserService;
import hello.hellospring.config.auth.PrincipalDetails;
import hello.hellospring.config.auth.PrincipalDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class PostServiceTest {
    @MockBean
    PostService postService;

    @MockBean
    UserService userService;

//    @Test
//    @DisplayName("글 작성하기")
//    @Transactional
//    public void write() {
////        given
//        Mockito.when(userService.getById("testUser"))
//                .thenReturn(new User("testUser", "testPassword12", "testNickname", "ROLE_USER"));
//        User testUser = userService.getById("testUser");
//
//        HashMap<Object, Object> param = new HashMap<>();
//        param.put("lat", "127");
//        param.put("lng", "40");
//        param.put("address", "서울 영등포구 테스트동");
//        param.put("dongAddress", "테스트동");
//
//        param.put("title", "제목");
//        param.put("content", "내용");
//        param.put("categoryId", "1");
//
////        when
//        long postId = postService.writePost(param, testUser);
//
////        then
//        PostDTO resultPost = postService.getPostDTO(postId);
//        log.info("resultPost" + resultPost.toString());
//        assertThat(resultPost.getTitle()).isEqualTo(param.get("title"));
//        assertThat(resultPost.getContent()).isEqualTo(param.get("content"));
//    }

    @Test
    @DisplayName("글 검색하기")
    @Transactional
    public void search() {
//        given
        PostDTO testPost = new PostDTO(1, "제목", "내용");
        List<PostDTO> testPostList = new ArrayList<>();
        testPostList.add(testPost);

        Mockito.when(postService.searchPost("title", "제목"))
                .thenReturn(testPostList);

//        when
        List<PostDTO> resultPost = postService.searchPost("title", "제목");

//        then
        assertThat(resultPost.get(0).getTitle()).isEqualTo("제목");
        assertThat(resultPost.get(0).getContent()).isEqualTo("내용");
    }
}
