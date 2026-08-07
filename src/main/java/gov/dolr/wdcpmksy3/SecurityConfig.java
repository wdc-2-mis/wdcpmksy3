package gov.dolr.wdcpmksy3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

                        "/", "/login", "/loginSuccess", "/getEmailandGenerateotp", "/verifyOtp", "/piaPjtNotLocatiaon", "/institutionalStructurePPR1", "/saveInstitutionalStructurePPR1", "/updateInstitutionalStructurePPR1", "/viewPdfPreliminaryPPR4A","/editPreliminaryPPR4A", "/updatePreliminaryPPR4A", "/detailsOfSLNA", "/saveDetailsOfSLNA", 
                        "/deleteDetailsOfSLNA", "/completeDetailsOfSLNA", "/updateDetailsOfSLNA", "/getDetailsOfSLNAById", "/register", "/register/**", "/technicalsupport", "/customLogout", "/download/**","/viewPdfInstitutionalStructure", "/deleteInstitutionalStructurePPR1", "/completeInstitutionalStructurePPR1", "/editInstitutionalStructurePPR1",
                        "/slnaFunctionariesPPR3", "/saveSLNAFunctionariesPPR3", "/deleteSLNAFunctionariesPPR3", "/editSLNAFunctionariesPPR3", "/completeSLNAFunctionariesPPR3","/updateSLNAFunctionariesPPR3", "/preliminaryPPR4A", "/savePreliminaryPPR4A", "/deletePreliminaryPPR4A", "/completePreliminaryPPR4A","/checkDistrictExists", 
                        "/wcdcFunctionariesPPR4B", "/saveWCDCFunctionariesPPR4B", "/UpdateWCDCFunctionariesPPR4B", "/deleteWCDCFunctionariesPPR4B", "/editWCDCFunctionariesPPR4B", "/completeWCDCFunctionariesPPR4B", "/pprDistrict", "/savePPRDistrict", "/updatePPRDistrict", "/completePPRDistrict/**", "/deletePPRDistrict/**", "/pprProposedProjectDetails", 
                        "/getMicroWatershedCodeByMwId", "/getProjAndMicroWaterDetailsByDcode", "/savePrioritizedListOfProposedProject", "/agroClimateConditionPPR10", "/getProjectsByDistrictPPR10", "/getVillagesByProjectPPR10", "/saveAgroClimateConditionPPR10", "/deleteAgroClimateConditionPPR10", "/completeAgroClimateConditionPPR10", "/areaCoveredUnderWP", 
                        "/districtStats/**", "/microWatershedArea/**", "/saveAreaWP",  "/preliminaryPPR8" , "/getBlockByProjectPPR8", "/updateAreaWP", "/completeAreaConveredWP", "/deleteAreaConveredWP", "/profile", "/changePassword", "/progressdashboard", "/sl/**",
                       
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