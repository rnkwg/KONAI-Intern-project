package hello.hellospring.User.Model;

import hello.hellospring.Location.Model.LocationUser;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private long userId;

    @NotBlank(message = "id를 입력하세요")
    @Size(min=4, max=10, message = "4~10자로 입력하세요")
    private String id;

    @NotBlank(message = "password를 입력하세요")
    @Size(min=4, max=10, message = "4~10자로 입력하세요")
    private String password;

    @NotBlank(message = "nickname을 입력하세요")
    @Size(min=4, max=10, message = "4~10자로 입력하세요")
    private String nickname;

    private String role;

    private LocationUser locationUser;

    private long locationUserId;

    public UserDTO() {};

    public UserDTO(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    //     DTO -> Entity
    public User toEntity() {
        return User.builder()
                    .id(id)
                    .password(password)
                    .nickname(nickname)
                    .locationUser(locationUser)
                    .role(role)
                    .build();
    }

    //    Entity ->  DTO
    public UserDTO(User user) {
        userId = user.getUserId();
        id = user.getId();
        password = user.getPassword();
        nickname = user.getNickname();
        locationUser = user.getLocationUser();
        locationUserId = user.getLocationUser().getLocationUserId();
        role = user.getRole();
    }
}
