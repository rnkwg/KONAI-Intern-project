package hello.hellospring.Post.Controller;

import java.io.File;

import hello.hellospring.Post.Model.*;
import hello.hellospring.Post.Service.FileService;
import hello.hellospring.Post.Service.PostService;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostApiController {

    private final PostService postService;

    private final UserService userService;

    private final FileService fileService;

    private String path = "d:/imageSaveStorage/";

    /**
     * 사용자가 작성한 글 정보 받기 + 첨부파일
     * @param param
     * @param file
     * @param principal
     * @return
     */
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestParam HashMap<Object, Object> param,
                                        MultipartFile file, Principal principal) {
        User user = userService.getById(principal.getName());

        long postId = postService.writePost(param, user);
        Post post = postService.getPost(postId);

        if(file != null)
            fileService.saveFile(file, post);

        return ResponseEntity.ok().body("success");
    }

    /**
     * 사용자가 작성한 글 정보 받기 - image
     * @param multi
     * @return
     */
    @PostMapping("/write/image")
    public String writeImage(@RequestParam("image") MultipartFile multi) {
        String saveFileName = null;
        try {
            String uploadPath = path;
            String originFilename = multi.getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            ImageNameModel imageNameModel = new ImageNameModel();
            saveFileName = imageNameModel.GenSaveFileName(extName);

            if (!multi.isEmpty()) {
                File file = new File(uploadPath, saveFileName);
                multi.transferTo(file);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("[Error] " + e.getMessage());
        }
        return saveFileName;
    }

    /**
     * 첨부파일 다운로드
     * @param fileId
     * @return
     * @throws IOException
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FileDTO fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                //    첨부파일 한글이름 인코딩
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        new String(fileDto.getOriginName().getBytes("UTF-8"), "ISO-8859-1") + "\"")
                .body(resource);
    }

    //   글 삭제하기
    @GetMapping("/delete/postId/{postId}")
    public ResponseEntity delete(@PathVariable long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(postId);
    }

    //    글 수정하기
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam HashMap<Object, Object> param,
                                         MultipartFile file) {
        postService.updatePost(param);

        fileService.updateFile(file, Long.parseLong((String) param.get("postId")));

        return ResponseEntity.ok().body("success");
    }

    //    글 검색하기
    @GetMapping("/searchResult/searchType/{searchType}/keyword/{keyword}")
    public List<PostDTO> listBySearch(@RequestParam(value="searchType") String searchType, @RequestParam(value="keyword") String keyword) {
        List<PostDTO> postList = postService.searchPost(searchType, keyword);
        return postList;
    }

    //    카테고리 별 글 목록 보기
    @GetMapping("/listResult/categoryId/{categoryId}")
    public List<PostDTO> list(@PathVariable long categoryId, Principal principal) {
        User user = userService.getById(principal.getName());
        String userAddress = user.getLocationUser().getAddress();
        List<PostDTO> postList = postService.getAllPostListByCategoryId(categoryId, userAddress);
        return postList;
    }

    //    사용자 별 글 목록 보기
    @GetMapping("/listResult/userId/{userId}")
    public List<PostDTO> listByUser(@PathVariable long userId) {
        User user = userService.getByUserId(userId);
        List<PostDTO> postList = postService.getAllPostByUser(user);
        return postList;
    }
}
