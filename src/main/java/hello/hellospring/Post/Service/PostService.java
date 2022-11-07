package hello.hellospring.Post.Service;

import hello.hellospring.Location.Model.Location;
import hello.hellospring.Location.Service.LocationService;
import hello.hellospring.Post.Model.*;
import hello.hellospring.Post.Repository.CategoryRepository;
import hello.hellospring.Post.Repository.PostRepository;
import hello.hellospring.Post.Repository.PostRepositoryImpl;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    public final PostRepository postRepository;
    public final CategoryRepository categoryRepository;

    public final PostRepositoryImpl postRepositoryImpl;

    private final UserService userService;

    private final LocationService locationService;


    //   카테고리 전체 가져오기 메인에서 출력하기 위함
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

//   카테고리 id로 카테고리 가져오기
    public Category getCategory(long id){
        return categoryRepository.findByCategoryId(id);
    }

//    전체 글 목록 가져오기
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(new PostDTO(post));
        }
        return postDTOs;
    }

//    카테고리 별 글 목록 가져오기
    public List<PostDTO> getAllPostByCategoryId(long id) {
        List<Post> posts = postRepository.findByCategoryCategoryIdOrderByPostIdAsc(id);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(new PostDTO(post));
        }
        return postDTOs;
    }

    //    카테고리 별 글 목록 가져오기 사용자의 선호 동에 해당하는 글 먼저 출력함
    public List<PostDTO> getAllPostListByCategoryId(long id, String userAddress) {
        List<Post> postsEqualAddress = postRepository.findByCategoryCategoryIdAndLocationDongAddressOrderByPostIdDesc(id, userAddress);
        List<Post> postsENotEqualAddress = postRepository.findByCategoryCategoryIdAndLocationDongAddressNotOrderByPostIdDesc(id, userAddress);

        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : postsEqualAddress) {
            postDTOs.add(new PostDTO(post));
        }
        for (Post post : postsENotEqualAddress) {
            postDTOs.add(new PostDTO(post));
        }

        return postDTOs;
    }


    //    글 가져오기
    public Post getPost(long id) {
        Post post = postRepository.findByPostId(id);
        return post;
    }

//    글 선택 시, 글 DTO 가져오기
    public PostDTO getPostDTO(long id) {
        Post post = postRepository.findByPostId(id);
        PostDTO postDTO = new PostDTO(post);
        postDTO.setCategory(getCategory(postDTO.getCategoryId()));
        return postDTO;
    }

    //    글 작성하기
    @Transactional
    public long writePost(HashMap<Object, Object> param, User user) {
        Location location = new Location();
        location.setLat(new BigDecimal((String) param.get("lat")));
        location.setLng(new BigDecimal((String) param.get("lng")));
        location.setAddress((String) param.get("address"));
        location.setDongAddress((String) param.get("dongAddress"));
        locationService.saveLocation(location);

        Post post = new Post();
        post.setUser(user);
        post.setTitle((String) param.get("title"));
        post.setContent((String) param.get("content"));
        post.setLocation(location);

        //post 에 category 넣어주기
        Category category = getCategory(Long.parseLong((String) param.get("categoryId")));
        post.setCategory(category);

        return postRepository.save(post).getPostId();
    }

//   글 삭제하기
    @Transactional
    public void deletePost(long postId) {
//        나중에 예외처리 할 때 사용
//        Comment comment = commentRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        long locationId = getPost(postId).getLocation().getLocationId();
        postRepository.deleteById(postId);
        locationService.deleteLocation(locationId);
    }

//    글 검색하기
    @Transactional
    public List<PostDTO> searchPost(String searchType, String keyword) {
        List<Post> posts = null;
        switch (searchType) {
            case "title":
                posts = postRepository.findByTitleContainingOrderByPostId(keyword);
                break;
            case "content":
                posts = postRepository.findByContentContainingOrderByPostId(keyword);
                break;
            case "nickname":
                posts = postRepository.findByUserNickname(keyword);
                break;
        }
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(new PostDTO(post));
        }
        return postDTOs;
    }

//    글 수정하기
    @Transactional
    public void updatePost(HashMap<Object, Object> param) {
        Date updateDate = new Date();

        Location location = new Location();
        location.setLocationId(Long.parseLong((String) param.get("locationId")));
        location.setLat(new BigDecimal((String) param.get("lat")));
        location.setLng(new BigDecimal((String) param.get("lng")));
        location.setAddress((String) param.get("address"));
        location.setDongAddress((String) param.get("dongAddress"));
        locationService.updateLocation(location);

        postRepositoryImpl.updatePostTitle(Long.parseLong((String) param.get("postId")), (String) param.get("title"));
        postRepositoryImpl.updatePostContent(Long.parseLong((String) param.get("postId")), (String) param.get("content"));
        postRepositoryImpl.updatePostUpdateDate(Long.parseLong((String) param.get("postId")), updateDate);
    }

//    사용자가 작성한 글 목록 보기
    public List<PostDTO> getAllPostByUser(User user) {
        long userId = user.getUserId();
        List<Post> posts =  userService.getByUserId(userId).getPostList();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(new PostDTO(post));
        }
        return postDTOs;
    }
}
