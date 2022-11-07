package hello.hellospring.Location.Model;

import hello.hellospring.Location.Repository.LocationUserRepository;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="LOCATION_USER")
public class LocationUser implements Serializable {
    @Id
    @Column(name="LOCATION_USER_ID")
    private long locationUserId;

    private String address;

    private String dong;

    public LocationUser() {

    }

    public LocationUser(long locationUserId, String address, String dong) {
        this.locationUserId = locationUserId;
        this.address = address;
        this.dong = dong;
    }

    //    @OneToOne(mappedBy = "locationUser", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
//    @JsonIgnore
//    private User user;
}
