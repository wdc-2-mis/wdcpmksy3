package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MSanctionRepository;
import gov.dolr.wdcpmksy3.dto.SLDashboardDTO;
import gov.dolr.wdcpmksy3.dto.SLDashboardStatusDTO;
import gov.dolr.wdcpmksy3.repository.MDistrictRepository;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;



@Service
public class LoginDashboardService {

    private final MDistrictRepository districtRepository;
    private final MPprRepository pprRepository;
    private final MSanctionRepository sanctionRepository;
    private final UserMapRepository userMapRepository;

    public LoginDashboardService(
            MDistrictRepository districtRepository,
            MPprRepository pprRepository,
            MSanctionRepository sanctionRepository,
            UserMapRepository userMapRepository) {

        this.districtRepository = districtRepository;
        this.pprRepository = pprRepository;
        this.sanctionRepository = sanctionRepository;
        this.userMapRepository = userMapRepository;
    }

    @Transactional(readOnly = true)
    public SLDashboardDTO getDashboardData(Integer stcode) {

        Long totalDistricts =
                districtRepository.countDistrictsByState(stcode);

        Long totalProjects =
                pprRepository.countActiveProjectsByState(stcode);

        Long totalPiaAssigned =
                userMapRepository.countActivePiaByState(stcode);

        BigDecimal totalAreaCovered =
                sanctionRepository.getTotalAreaCoveredByState(stcode);

        return new SLDashboardDTO(
                totalDistricts,
                totalProjects,
                totalPiaAssigned,
                totalAreaCovered
        );
    }

    @Transactional(readOnly = true)
    public SLDashboardStatusDTO getDashboardStatus(Integer stcode) {

        Long pprPending =
                pprRepository.countPendingPprByState(stcode);

        Long pprApproved =
                pprRepository.countApprovedPprByState(stcode);

       
        Long dprPending = 0L;
        Long dprApproved = 0L;

        return new SLDashboardStatusDTO(
                pprPending,
                pprApproved,
                dprPending,
                dprApproved
        );
    }
}
