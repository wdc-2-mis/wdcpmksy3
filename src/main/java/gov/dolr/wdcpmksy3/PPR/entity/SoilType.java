package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "m_soil_type")
public class SoilType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_type_id")
    private Integer soilTypeId;

    @Column(name = "soil_name", length = 50)
    private String soilName;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;
}