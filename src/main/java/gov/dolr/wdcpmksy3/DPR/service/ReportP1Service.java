package gov.dolr.wdcpmksy3.DPR.service;


import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.DPR.repository.ReportP1Repository;
import gov.dolr.wdcpmksy3.dto.CodeName;
import gov.dolr.wdcpmksy3.dto.ReportP1Row;
import gov.dolr.wdcpmksy3.dto.ReportP1StateSummary;

@Service
@Transactional(readOnly = true)
public class ReportP1Service {

    private final ReportP1Repository repo;

    public ReportP1Service(ReportP1Repository repo) { this.repo = repo; }

    /** null / 0 / negative (the "All" option) means no filter. */
    private static Integer norm(Integer v) { return (v == null || v <= 0) ? null : v; }

    public List<ReportP1Row> getReport(Integer state, Integer district, Integer fy) {
        return repo.findSanctionedProjects(norm(state), norm(district), norm(fy));
    }
    public List<CodeName> states()                 { return repo.findAllStates(); }
    public List<CodeName> districts(Integer state) { return repo.findDistricts(norm(state)); }
    public List<CodeName> finYears()               { return repo.findFinYears(); }

    public <T> BigDecimal sum(List<T> rows, Function<T, BigDecimal> f) {
        return rows.stream().map(f).filter(Objects::nonNull)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<ReportP1StateSummary> getStateSummary() {
        return repo.findStateSummary();
    }
}
