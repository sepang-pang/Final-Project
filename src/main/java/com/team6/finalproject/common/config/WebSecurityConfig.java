package com.team6.finalproject.common.config;

import com.team6.finalproject.security.AuthenticationFilter;
import com.team6.finalproject.security.AuthorizationFilter;
import com.team6.finalproject.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig{

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthorizationFilter AuthorizationFilter() {
        return new AuthorizationFilter(userDetailsService);
    }

    @Bean
    public AuthenticationFilter AuthenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws  Exception {
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/images/**", "/js/**", "/webjars/**", "/css/**").permitAll()
                                .anyRequest().permitAll()

                );



        http.addFilterBefore(AuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(AuthorizationFilter(),AuthenticationFilter.class);





        return http.build();
    }


}
