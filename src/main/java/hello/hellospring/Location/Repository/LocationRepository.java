package hello.hellospring.Location.Repository;

import hello.hellospring.Location.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
