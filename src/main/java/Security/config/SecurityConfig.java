package Security.config;

import Security.controller.AuthenticationSuccessHandler;
import Security.model.MyUserDetailService;
import jakarta.security.auth.message.config.AuthConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    MyUserDetailService userDetailService;

    // This is Chapter Two of Role based Authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(registry -> {
                        registry.requestMatchers("/home", "/register/**").permitAll();
                        registry.requestMatchers("admin/**").hasRole("ADMIN");
                        registry.requestMatchers("user/**").hasRole("USER");
                        registry.anyRequest().authenticated();
                    })
                    .formLogin(httpSecurityFormLoginConfigurer ->
                    {
                        httpSecurityFormLoginConfigurer.loginPage("/login")
                                .successHandler(new AuthenticationSuccessHandler())
                                .permitAll();
                    })
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // This is Chapter Three  In memory Role based Authorization

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails normalUser = User.builder()
//                .username("gc")
//                .password("1234")
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User.builder()
//                .username("admin")
//                .password("5678")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authConfigProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


