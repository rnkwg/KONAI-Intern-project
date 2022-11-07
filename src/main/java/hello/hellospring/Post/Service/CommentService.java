package hello.hellospring.Post.Service;

import hello.hellospring.Post.Model.*;
import hello.hellospring.Post.Repository.CommentRepository;
import hello.hellospring.Post.Repository.CommentRepositoryImpl;
import hello.hellospring.User.Model.User;
import hello.hellospring.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    public final CommentRepository commentRepository;

    public final CommentRepositoryImpl commentRepositoryImpl;

    public final PostService postService;

    private final UserService userService;


    //    댓글 가져오기
    public CommentDTO getComment(long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        return new CommentDTO(comment);
    }

//    글에 대한 댓글 목록 가져오기
    public List<CommentDTO> getAllComment(long postId) {
        List<Comment> comments = commentRepository.findByPostPostId(postId);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOs.add(new CommentDTO(comment));
        }
        return commentDTOs;
    }

//    댓글 작성하기
    @Transactional
    public void writeComment(CommentDTO commentDTO) {
        Post post = postService.getPost(commentDTO.getPostId());
        commentDTO.setPost(post);
        commentRepository.save(commentDTO.toEntity());
    }

    /**
     * 댓글 수정하기
     * @param commentDTO
     */
    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        commentRepositoryImpl.updateComment(commentDTO.getCommentId(), commentDTO.getContent());
    }
//    댓글 삭제하기
    @Transactional
    public void deleteComment(long commentId) {
        commentRepository.deleteCommentByCommentId(commentId);
    }

    //    사용자가 작성한 댓글 목록 보기
    public List<CommentDTO> getAllCommentByUser(User user) {
        long userId = user.getUserId();
        List<Comment> comments =  userService.getByUserId(userId).getCommentList();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(new CommentDTO(comment));
        }
        return commentDTOS;
    }
}
