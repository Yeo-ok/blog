package com.it.blog.config;

import com.it.blog.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    //스프링 시큐리티 관련 설정 파일
    private final UserDetailService userDetailService;

    //스프링 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http
//                .authorizeRequests() //인증 인가 설정
//                .requestMatchers("/login", "signup", "/user").permitAll()//전체 접근 허가
//                .anyRequest().authenticated()//anyReq > 위에서 설정한 url 외의 전체 설정
//                                             //authenticated > 인가는 필요없지만 인증은 성공된 상태여야함
//                .and()
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login") // 로그인 페이지 경로설
//                        .defaultSuccessUrl("/articles") // 로그인 성공후에 이동할 경로
//                )
//                        .logout(logout -> logout
//                                .logoutSuccessUrl("/login") // 로그아웃 완료되었을 때 이동할 경로
//                        .invalidateHttpSession(true)
//                        ).csrf(AbstractHttpConfigurer::disable) // 로그 아웃 이후에 세션을 전체 삭제할지 여부
//                .build();
//
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
                .requestMatchers("/login", "/signup", "/user", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    //인증 관리자 관련설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http
//            , BCryptPasswordEncoder bCryptPasswordEncoder
//            , UserDetailService userDetailService) throws Exception{
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //사용자 정보를 가져올 서비스르 설정(반드시 UserDetailisService 상속받은 클래스여 야함
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //비밀번호 암호화하기 위한 인코더
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    //패스워드 이놐더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
