package hello.hellospring.config.auth;

import hello.hellospring.User.Model.User;
import hello.hellospring.User.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    시큐리티 session(내부 Authentication (내부 User))
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id);
        if(user != null) {
//            return new org.springframework.security.core.userdetails.User(
//                    user.getId(), user.getPassword(), new ArrayList<>();
            return new PrincipalDetails(user);
        }
        return null;
    }
}
