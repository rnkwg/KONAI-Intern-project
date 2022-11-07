package hello.hellospring.Location.Model;

import hello.hellospring.Post.Model.Post;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class LocationDTO {
    private long locationId;

    private Post post;

    private BigDecimal lat;

    private BigDecimal lng;

    private String address;

    private String dongAddress;

//    DTO -> entity
    public Location toEntity() {
        return Location.builder()
                        .locationId(locationId)
                        .post(post)
                        .lat(lat)
                        .lng(lng)
                        .address(address)
                        .dongAddress(dongAddress)
                        .build();
    }

//    entity -> DTO
    public LocationDTO(Location location) {
        locationId = location.getLocationId();
        post = location.getPost();
        lat = location.getLat();
        lng = location.getLng();
        address = location.getAddress();
        dongAddress = location.getDongAddress();
    }
}
