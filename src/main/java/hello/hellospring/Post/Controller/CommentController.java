package hello.hellospring.Post.Controller;

import hello.hellospring.Post.Model.CommentDTO;
import hello.hellospring.Post.Service.CommentService;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    //    사용자가 작성한 댓글 정보 받기
    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO, Principal principal) {
        User user = userService.getById(principal.getName());
        commentDTO.setUser(user);
        commentService.writeComment(commentDTO);
        return ResponseEntity.ok().body(commentDTO);
    }

//    댓글 삭제하기
    @GetMapping("/delete/commentId/{commentId}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(commentId);
    }

//    댓글 수정하기
    @PostMapping("/update/commentId/{commentId}")
    @ResponseBody
    public ResponseEntity update(@RequestBody CommentDTO commentDTO, @PathVariable long commentId) {
        commentDTO.setCommentId(commentId);
        commentService.updateComment(commentDTO);
        return ResponseEntity.ok(commentId);
    }

//    사용자가 작성한 댓글 목록 보기
    @GetMapping("/list/user")
    public String listByUser(Principal principal, ModelMap model) {
        User user = userService.getById(principal.getName());
        List<CommentDTO> commentList = commentService.getAllCommentByUser(user);
        model.put("commentList", commentList);
        return "post/commentList";
    }
}
