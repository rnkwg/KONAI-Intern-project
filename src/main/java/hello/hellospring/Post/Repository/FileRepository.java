package hello.hellospring.Post.Repository;

import hello.hellospring.Post.Model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
