package gov.dolr.wdcpmksy3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(

                        "/", "/login", "/loginSuccess", "/getEmailandGenerateotp", "/verifyOtp", "/piaPjtNotLocatiaon", "/ppr1", "/saveInstitutionalStructure",  
                        "/register", "/register/**", "/technicalsupport", "/customLogout",
                        
                        

                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/video/**"
                       

                ).permitAll()

                .anyRequest().authenticated()

            )

            .formLogin(form -> form

                    .loginPage("/login")

                    .permitAll()

            )

            .logout(logout -> logout

                    .logoutUrl("/logout")

                    .logoutSuccessUrl("/login?logout")

                    .invalidateHttpSession(true)

                    .deleteCookies("JSESSIONID")

                    .clearAuthentication(true)

            )
            
            .sessionManagement(session -> session

                    .sessionFixation(fix -> fix.migrateSession())

                    .maximumSessions(1)

                    .maxSessionsPreventsLogin(false)

            );

        return http.build();

    }

}