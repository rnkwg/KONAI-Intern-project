package hello.hellospring.Post.Repository;

import hello.hellospring.Post.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Post findByPostId(long postId);

    List<Post> findByCategoryCategoryIdOrderByPostIdAsc(long categoryId);

    List<Post> findByTitleContainingOrderByPostId(String keyword);

    List<Post> findByContentContainingOrderByPostId(String keyword);

    List<Post> findByUserNickname(String keyword);

    List<Post> findByCategoryCategoryIdAndLocationDongAddressOrderByPostIdDesc(long categoryId, String dongAddress);

    List<Post> findByCategoryCategoryIdAndLocationDongAddressNotOrderByPostIdDesc(long categoryId, String dongAddress);
}
