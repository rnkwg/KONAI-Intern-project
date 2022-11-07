package hello.hellospring.Location.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.hellospring.Post.Model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "LOCATION_SEQ_GENERATOR",
        sequenceName = "LOCATION_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name="LOCATION")
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "LOCATION_SEQ_GENERATOR")
    @Column(name="LOCATION_ID")
    private long locationId;

    @OneToOne(mappedBy = "location", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Post post;

    @Column(name="LATITUDE")
    private BigDecimal lat;

    @Column(name="LONGITUDE")
    private BigDecimal lng;

    private String address;

    @Column(name="DONG")
    private String dongAddress;

    public Location() {}

    @Builder
    public Location(long locationId, Post post, BigDecimal lat, BigDecimal lng, String address, String dongAddress) {
        this.locationId = locationId;
        this.post = post;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.dongAddress = dongAddress;
    }
}
