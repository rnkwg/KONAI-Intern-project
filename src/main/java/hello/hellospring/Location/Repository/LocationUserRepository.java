package hello.hellospring.Location.Repository;

import hello.hellospring.Location.Model.LocationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationUserRepository extends JpaRepository<LocationUser, Long> {
    LocationUser findByLocationUserId(long locationUserId);
}
