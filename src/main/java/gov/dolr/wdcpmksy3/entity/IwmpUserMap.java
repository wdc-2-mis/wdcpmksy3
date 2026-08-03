package gov.dolr.wdcpmksy3.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "iwmp_user_map")
public class IwmpUserMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Integer mapId;
       
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
    private IwmpUserReg user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    private MState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode")
    private MDistrict district;

    @Column(name = "creator_date")
    private LocalDate creatorDate;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip")
    private String requestIp;

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public IwmpUserReg getUser() {
		return user;
	}

	public void setUser(IwmpUserReg user) {
		this.user = user;
	}

	public MState getState() {
		return state;
	}

	public void setState(MState state) {
		this.state = state;
	}

	public MDistrict getDistrict() {
		return district;
	}

	public void setDistrict(MDistrict district) {
		this.district = district;
	}

	public LocalDate getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(LocalDate creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public LocalDate getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
    
    
    
    
    
    
}