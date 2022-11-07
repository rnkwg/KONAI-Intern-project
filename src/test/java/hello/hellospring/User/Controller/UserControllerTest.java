package hello.hellospring.User.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.hellospring.Location.Model.LocationUser;
import hello.hellospring.User.Model.UserDTO;
import hello.hellospring.User.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @MockBean
//    UserService userService;
//
//    @Test
//    @DisplayName("마이페이지 이동하기")
//    void goMyPage() throws Exception {
//        //given
//        given(userService.getUserDtoById("testId")).willReturn(
//                new UserDTO("testId", "testPassword1234", "testNickname"));
//        UserDTO testUser = userService.getUserDtoById("testId");
//        List<LocationUser> locationUserList = userService.getAllLocationUser();
//
//        //when
//
//
//        //then
//        mockMvc.perform(get("/myPage") //MockMvc를 통해 /hello 주소로 GET 요청
//                .param("userDTO", String.valueOf(testUser)))
//                .andExpect(status().isOk()); //200 상태
//    }

//    @Test
//    @DisplayName("Product 데이터 가져오기 테스트")
//    void getProductTest() throws Exception {
//
//        // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
//        given(productService.getProduct("12315")).willReturn(
//                new ProductDto("15871", "pen", 5000, 2000));
//
//        String productId = "12315";
//
//        // andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
//        mockMvc.perform(
//                        get("/api/v1/product-api/product/" + productId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productId").exists()) // json path의 depth가 깊어지면 .을 추가하여 탐색할 수 있음 (ex : $.productId.productIdName)
//                .andExpect(jsonPath("$.productName").exists())
//                .andExpect(jsonPath("$.productPrice").exists())
//                .andExpect(jsonPath("$.productStock").exists())
//                .andDo(print());
//
//        // verify : 해당 객체의 메소드가 실행되었는지 체크해줌
//        verify(productService).getProduct("12315");
//    }
}
