package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="ppr_project_glance")
public class PprProjectGlance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ppr_project_glance_id")
    private Integer pprProjectGlanceId;

    @ManyToOne
    @JoinColumn(name="ppr_id")
    private MPpr ppr;

    @ManyToOne
    @JoinColumn(name="mw_id")
    private MicroWatershed microWatershed;

    @ManyToOne
    @JoinColumn(name="project_type_id")
    private ProjectType projectType;

    @ManyToOne
    @JoinColumn(name="pia_id")
    private MPiaDetails pia;

    @Column(name="selection_reason")
    private String selectionReason;

    @Column(name="project_area")
    private BigDecimal projectArea;

    @Column(name="proposed_area")
    private BigDecimal proposedArea;

    @Column(name="project_cost")
    private BigDecimal projectCost;

    @Column(name="comments")
    private String comments;

    @Column(name="status")
    private Character status;

    @OneToMany(mappedBy="projectGlance",cascade=CascadeType.ALL)
    private List<PprVillage> villages;

}