package gov.dolr.wdcpmksy3.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.WdcpmksyProjectLocation;
import gov.dolr.wdcpmksy3.repository.WdcpmksyProjectLocationRepository;
import jakarta.transaction.Transactional;


@Service
public class ProjectLocationService {

    private final WdcpmksyProjectLocationRepository locationRepository;
    private final MPprRepository projectRepository;
    private final VillageRepository villageRepository;

    public ProjectLocationService(
            WdcpmksyProjectLocationRepository locationRepository,
            MPprRepository projectRepository,
            VillageRepository villageRepository) {

        this.locationRepository = locationRepository;
        this.projectRepository = projectRepository;
        this.villageRepository = villageRepository;
    }


    // --------------------------------------------------
    // 1. SAVE SELECTED VILLAGES AS DRAFT
    // --------------------------------------------------

    @Transactional
    public void saveProjectLocationDraft(
            Integer pprId,
            List<Integer> villageCodes,
            String userid,
            String requestIp) {

        MPpr project = projectRepository.findById(pprId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found."));

        if (project.getDistrict() == null) {
            throw new IllegalArgumentException(
                    "Project district is not available.");
        }

        // Once completed, no more records can be added.
        if (locationRepository.existsByProject_PprIdAndStatus(pprId, 'C')) {
            throw new IllegalStateException(
                    "Project location is already completed. No changes are allowed.");
        }

        if (villageCodes == null || villageCodes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please select at least one village.");
        }

        // Remove duplicate codes from the submitted selection.
        Set<Integer> selectedCodes = new LinkedHashSet<>(villageCodes);

        List<MVillage> villages =
                villageRepository.findAllById(selectedCodes);

        if (villages.size() != selectedCodes.size()) {
            throw new IllegalArgumentException(
                    "One or more selected villages are invalid.");
        }

        List<WdcpmksyProjectLocation> locationsToSave =
                new ArrayList<>();

        for (MVillage village : villages) {

            if (village.getGramPanchayat() == null ||
                    village.getGramPanchayat().getBlock() == null ||
                    village.getGramPanchayat().getBlock().getDistrict() == null) {

                throw new IllegalArgumentException(
                        "Invalid village location details.");
            }

            // Verify village belongs to the project's district.
            if (!project.getDistrict().getDcode().equals(
                    village.getGramPanchayat()
                            .getBlock()
                            .getDistrict()
                            .getDcode())) {

                throw new IllegalArgumentException(
                        "A selected village does not belong to the project's district.");
            }

            // Do not insert a village already saved for this project.
            boolean alreadyExists =
                    locationRepository.existsByProject_PprIdAndVillage_Vcode(
                            pprId, village.getVcode());

            if (alreadyExists) {
                continue;
            }

            WdcpmksyProjectLocation location =
                    new WdcpmksyProjectLocation();

            location.setProject(project);
            location.setVillage(village);
            location.setStatus('D');

            location.setCreatedBy(userid);
            location.setCreatedDt(LocalDateTime.now());

            location.setLastUpdatedBy(userid);
            location.setLastUpdatedDate(LocalDate.now());

            location.setRequestIp(requestIp);

            locationsToSave.add(location);
        }

        if (locationsToSave.isEmpty()) {
            throw new IllegalArgumentException(
                    "All selected villages are already saved.");
        }

        locationRepository.saveAll(locationsToSave);
    }


    // --------------------------------------------------
    // 2. DELETE ONE DRAFT LOCATION
    // --------------------------------------------------

    @Transactional
    public void deleteProjectLocation(
            Integer pprId,
            Integer locationId) {

        WdcpmksyProjectLocation location =
                locationRepository
                        .findByLocationIdAndProject_PprId(
                                locationId, pprId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Project location record not found."));

        // Do not allow deletion after completion.
        if (locationRepository.existsByProject_PprIdAndStatus(pprId, 'C')) {
            throw new IllegalStateException(
                    "Project location is already completed. Deletion is not allowed.");
        }

        if (!Character.valueOf('D').equals(location.getStatus())) {
            throw new IllegalStateException(
                    "Only draft records can be deleted.");
        }

        locationRepository.delete(location);
    }


    // --------------------------------------------------
    // 3. COMPLETE ALL DRAFT LOCATIONS
    // --------------------------------------------------

    @Transactional
    public void completeProjectLocation(
            Integer pprId,
            String userid,
            String requestIp) {

        List<WdcpmksyProjectLocation> existing =
                locationRepository.findByProject_PprId(pprId);

        if (existing.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please add at least one project location before completing.");
        }

        // Completion can happen only once.
        if (existing.stream().anyMatch(
                location -> Character.valueOf('C')
                        .equals(location.getStatus()))) {

            throw new IllegalStateException(
                    "Project location has already been completed.");
        }

        int updated = locationRepository.completeDraftLocations(
                pprId,
                userid,
                LocalDate.now(),
                requestIp
        );

        if (updated == 0) {
            throw new IllegalStateException(
                    "No draft project locations found to complete.");
        }
    }
}
