package com.woozuda.backend.security.config;

import com.woozuda.backend.account.service.CustomOAuth2UserService;
import com.woozuda.backend.security.jwt.JWTFilter;
import com.woozuda.backend.security.jwt.JWTUtil;
import com.woozuda.backend.security.jwt.LoginFilter;
import com.woozuda.backend.security.oauth2.CustomAuthenticationEntryPoint;
import com.woozuda.backend.security.oauth2.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    // AbstractAuthenticationProcessingFilter 원본 코드에서
    // authenticationmanager를 수동 설정해야 한다고 되어 있음 (The filter requires that you set the <tt>authenticationManager</tt> property. )
    // AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    // https://stackoverflow.com/questions/51986766/spring-security-getauthenticationmanager-returns-null-within-custom-filter
    private final AuthenticationConfiguration authenticationConfiguration;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //비밀번호 평문 -> 비문화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf, formlogin, httpbasic 필터를 비활성화
        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable());

        //ouath2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join", "/error", "/account/sample/alluser", "/favicon.ico").permitAll()
                        .requestMatchers("/account/sample/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(customAuthenticationEntryPoint));

        //UserNamePasswordAuthenticationFilter 자리에 커스텀 하게 만든 LoginFilter를 실행한다.
        //jwt 방식으로 구현하다 보니 , form login 을 비활성화했고, UserNamePasswordAuthenticationFilter 도 비활성화 되었음 (그래서 커스텀 구현이 필요)
        http
                //.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                //.addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
                //.addFilterAfter(new JWTFilter(jwtUtil), BasicAuthenticationFilter.class)
                //.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), BasicAuthenticationFilter.class);
                .addFilterAfter(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        //.addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
        //.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), OAuth2LoginAuthenticationFilter.class);


        //stateless 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()));


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("https://woozuda.swygbro.com","http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Set-Cookie"));
        //configuration.setExposedHeaders(Arrays.asList(AUTHORIZATION, SET_COOKIE, "TemporaryAuth"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
