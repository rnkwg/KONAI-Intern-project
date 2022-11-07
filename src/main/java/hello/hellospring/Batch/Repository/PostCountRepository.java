package hello.hellospring.Batch.Repository;

import hello.hellospring.Batch.Model.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCount, Long> {
}
