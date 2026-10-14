package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_dpr_head", schema = "public")
public class MDprHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "head_type_id")
    private Integer headTypeId;

    @Column(name = "head_type", length = 100)
    private String headType;

    public Integer getHeadTypeId() {
        return headTypeId;
    }

    public void setHeadTypeId(Integer headTypeId) {
        this.headTypeId = headTypeId;
    }

    public String getHeadType() {
        return headType;
    }

    public void setHeadType(String headType) {
        this.headType = headType;
    }
}