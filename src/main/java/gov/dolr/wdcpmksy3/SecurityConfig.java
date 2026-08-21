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

                  		"/preliminaryPPR15","/testppr15","/saveDraftPPR15","/getVillagesByProject1","/getMicroWatershedsByProject1","/getDraftsPPR15","/getDraftsPPR15", "/updatePPR15","/deletePreliminaryPPR15",
                  	    "/completePreliminaryPPR15","/", "/login", "/loginSuccess", "/getEmailandGenerateotp", "/verifyOtp", "/piaPjtNotLocatiaon", "/institutionalStructurePPR1", "/saveInstitutionalStructurePPR1", "/updateInstitutionalStructurePPR1", "/viewPdfPreliminaryPPR4A","/editPreliminaryPPR4A", "/updatePreliminaryPPR4A", "/detailsOfSLNA", "/saveDetailsOfSLNA", 
                        "/deleteDetailsOfSLNA", "/completeDetailsOfSLNA", "/updateDetailsOfSLNA", "/getDetailsOfSLNAById", "/register", "/register/**", "/technicalsupport", "/customLogout", "/download/**","/viewPdfInstitutionalStructure", "/deleteInstitutionalStructurePPR1", "/completeInstitutionalStructurePPR1", "/editInstitutionalStructurePPR1",
                        "/slnaFunctionariesPPR3", "/saveSLNAFunctionariesPPR3", "/deleteSLNAFunctionariesPPR3", "/editSLNAFunctionariesPPR3", "/completeSLNAFunctionariesPPR3","/updateSLNAFunctionariesPPR3", "/preliminaryPPR4A", "/savePreliminaryPPR4A", "/deletePreliminaryPPR4A", "/completePreliminaryPPR4A","/checkDistrictExists", "/areaCoveredUnderWP",
                        "/wcdcFunctionariesPPR4B", "/saveWCDCFunctionariesPPR4B", "/UpdateWCDCFunctionariesPPR4B", "/deleteWCDCFunctionariesPPR4B", "/editWCDCFunctionariesPPR4B", "/completeWCDCFunctionariesPPR4B", "/pprDistrict", "/savePPRDistrict", "/updatePPRDistrict", "/completePPRDistrict/**", "/deletePPRDistrict/**", "/pprProposedProjectDetails", 
                        "/getMicroWatershedCodeByMwId", "/getProjAndMicroWaterDetailsByDcode", "/savePrioritizedListOfProposedProject", "/getPprProposedProjectForEdit", "/updatePprProposedProject", "/viewSavedCriteriaDetails", "/deletePprProposedProject", "/completePprProposedProject", "/checkPprProposedProjectExists",
                        "/pprProjectAtGlance", "/getGPlistbyBlock", "/getVillageListByGcode", "/savePprProjectAtGlance", "/getPprProjectGlanceById", "/updatePprProjectAtGlance", "/deletePprProProjectGlance", "/getVillageDetailsByVcode", "/completePprProProjectGlance",
                        "/agroClimateConditionPPR10", "/getProjectsByDistrictPPR10", "/getVillagesByProjectPPR10", "/saveAgroClimateConditionPPR10", "/deleteAgroClimateConditionPPR10", "/completeAgroClimateConditionPPR10", "/editAgroClimateConditionPPR10", "/getAgroClimateConditionPPR10Id", 
                        "/districtStats/**", "/microWatershedArea/**", "/saveAreaWP",  "/preliminaryPPR8" , "/getBlockByProjectPPR8", "/saveDraftPPR8", "/updateDraftPPR8", "/getPPR8ById" ,"/getAllDraftPPR8", "/updateAreaWP", "/completeAreaConveredWP", "/deleteAreaConveredWP", "/profile", "/changePassword", "/progressdashboard", "/sl/**",  "/pprLandPatternArea", "/getProjectsByDistrictLandPattern", "/getMicroWatershedsByProject", 
                        "/getVillagesByProject", "/getLandPatternVillageStatus", "/savePPRLandPatternArea", "/getLandPatternAreaByDistrict", "/updatePPRLandPatternArea", "/deletePPRLandPatternArea", "/completePPRLandPatternArea", "/getPprProposedProjectForEdit", "/updatePprProposedProject","/completeDraftPPR8","/deleteDraftPPR8", "/dtlFloodDroughtArea", "/saveFloodDrought", "/updateFloodDroughtArea", "/completeFloodDrought", "/deleteFloodDrought",
                        "/livelihoodSummaryPPR13", "/getBlocksByDistrictPPR13","/saveLivelihoodSummaryPPR13", "/editLivelihoodSummaryPPR13","/completeLivelihoodSummaryPPR13","/deleteLivelihoodSummaryPPR13", "/drinkingWaterStatus", "/saveDrinkingWaterStatus", "/editDrinkingWaterStatus", "/deleteDrinkingWaterStatus", "/completeDrinkingWaterStatus", "/pprSoilErosion", "/getErosionTypes", "/getSoilErosionByDistrict", "/savePPRSoilErosion", 
                        "/updatePPRSoilErosion", "/deletePPRSoilErosion", "/completePPRSoilErosion", "/pprWaterOutcomes", "/savegroundWaterDepthArea", "/getWaterOutcomesByDistrict", "/updateWaterOutcome", "/completeWaterOutcome", "/deleteWaterOutcome", "/pendingUCPPR19", "/savePendingUCPPR19", "/deletePendingUCPPR19", "/completePendingUCPPR19", "/editPendingUCPPR19", "/unspentBalancePPR20", "/saveUnspentBalancePPR20", "/editUnspentBalancePPR20", "/deleteUnspentBalancePPR20", "/completeUnspentBalancePPR20",
                        "/pprEmploymentGeneration", "/getProjectsByDistrictEmployment", "/getMicroWatershedsByProjectEmployment", "/getVillagesByProjectEmployment", "/getEmploymentVillageStatus", "/getEmploymentGenerationByDistrict", "/savePPREmployment", "/updatePPREmployment", "/deletePPREmployment", "/completePPREmployment", 
                        
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