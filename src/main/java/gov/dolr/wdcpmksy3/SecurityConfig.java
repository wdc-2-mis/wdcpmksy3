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
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(12);

    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/","/login", "/register","/emaillogin","/sendOtp","/verifyOtp","/getEmailandGenerateotp", "/customLogout", "/fetchMenu", 
                    "/loginSuccess", "/checkemail",  "/projectPropose", "/projectProposSave", "/gisDetails",
            		
                    
                    // Static resources
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/fonts/**",
                    "/webjars/**",
                    "/video/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            );
				/*
				 * .logout(logout -> logout .logoutUrl("/logout")
				 * .logoutSuccessUrl("/login?logout") .invalidateHttpSession(true) // destroys
				 * session .deleteCookies("JSESSIONID") // removes session cookie )
				
                .sessionManagement(session -> session
                    .maximumSessions(1)            // restricts concurrent sessions
                    .maxSessionsPreventsLogin(false)
            		)   */

        return http.build();
    }
    
   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register" , "/getEmailandGenerateotp", "/logout", "/fetchMenu", "/login" , "/emaillogin", "/checkemail"
                		, "/sendOtp", "/verifyOtp", "/").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }  */
}