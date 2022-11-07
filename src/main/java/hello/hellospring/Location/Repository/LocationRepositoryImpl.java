package hello.hellospring.Location.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.Location.Model.Location;
import hello.hellospring.Location.Model.QLocation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class LocationRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public LocationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Location.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

//     글의 위치정보 변경
    public void updateLocation(long locationId, Location newLocation) {
        QLocation location = QLocation.location;
        jpaQueryFactory
                .update(location)
                .set(location.lat, newLocation.getLat())
                .set(location.lng, newLocation.getLng())
                .set(location.address, newLocation.getAddress())
                .set(location.dongAddress, newLocation.getDongAddress())
                .where(location.locationId.eq(locationId))
                .execute();
    }
}
