package hello.hellospring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**",
                "/assets/**",
                "/favicon.ico",
                "/resources/**",
                "/error",
                "/docs/**",
                "/static/**",
                "/js/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/user/login",
                        "/user/join",
                        "/",
                        "/user/idCheck",
                        "/user/nicknameCheck",
                        "/js/**",
                        "/user/myPage",
                        "/image/**"
                ).permitAll()
//                    .antMatchers("/post/**").rememberMe()
//                    .antMatchers("/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/user/login") //????????? page
                .usernameParameter("id") //id??? username?????? ??????
                .loginProcessingUrl("/login") //id??? password??? ????????? url
                .defaultSuccessUrl("/") //????????? ????????? ??????????????? ??????
                .permitAll()
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // ???????????? ????????? ??????????????? ??????
                .invalidateHttpSession(true) // ?????? ?????????
                .permitAll();

    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select id, password, enabled "
//                        + "from userinfo "
//                        + "where id = ?")
//                .authoritiesByUsernameQuery("select id, role "
//                        + "from userinfo "
//                        + "where id = ?");
//
////                .authoritiesByUsernameQuery("select u.id, r.name "
////                        + "from user_role ur inner join userinfo u on ur.user_id = u.user_id "
////                        + "inner join role r on ur.role_id = r.role_id "
////                        + "where u.id = ?");
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}