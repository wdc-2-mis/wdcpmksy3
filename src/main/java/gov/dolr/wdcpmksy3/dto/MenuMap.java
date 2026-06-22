package gov.dolr.wdcpmksy3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuMap {

    private int reg_id;
    private Integer submenu_id;
    private String submenu_name;
    private Integer parent_id;
    private String parentname;
    private String target;
	public int getReg_id() {
		return reg_id;
	}
	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	public Integer getSubmenu_id() {
		return submenu_id;
	}
	public void setSubmenu_id(Integer submenu_id) {
		this.submenu_id = submenu_id;
	}
	public String getSubmenu_name() {
		return submenu_name;
	}
	public void setSubmenu_name(String submenu_name) {
		this.submenu_name = submenu_name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
    
    
    
    
    
}