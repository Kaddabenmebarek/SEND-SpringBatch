package com.idorsia.research.send.domain;

import java.io.Serializable;

public class Demographics implements Serializable {
	
	private static final long serialVersionUID = -3513631000877609267L;
	private String studyId; 
	private String domain; 
	private String usubjId; 
	private String subjId; 
	private String rfStDtc; 
	private String rfEnDtc; 
	private String rfXstDtc; 
	private String rfXenDtc; 
	private String siteId; 
	private String brthDtc; 
	private String deliveryDtc;
	private String age; 
	private String ageTxt; 
	private String ageU; 
	private String sex; 
	private String species; 
	private String strain; 
	private String sbstrain; 
	private String armCd; 
	private String arm; 
	private String setCd;
	
	private String metadata;
	private String studyStartingDate;
	
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUsubjId() {
		return usubjId;
	}
	public void setUsubjId(String usubjId) {
		this.usubjId = usubjId;
	}
	public String getSubjId() {
		return subjId;
	}
	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}
	public String getRfStDtc() {
		return rfStDtc;
	}
	public void setRfStDtc(String rfStDtc) {
		this.rfStDtc = rfStDtc;
	}
	public String getRfEnDtc() {
		return rfEnDtc;
	}
	public void setRfEnDtc(String rfEnDtc) {
		this.rfEnDtc = rfEnDtc;
	}
	public String getRfXstDtc() {
		return rfXstDtc;
	}
	public void setRfXstDtc(String rfXstDtc) {
		this.rfXstDtc = rfXstDtc;
	}
	public String getRfXenDtc() {
		return rfXenDtc;
	}
	public void setRfXenDtc(String rfXenDtc) {
		this.rfXenDtc = rfXenDtc;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getBrthDtc() {
		return brthDtc;
	}
	public void setBrthDtc(String brthDtc) {
		this.brthDtc = brthDtc;
	}
	public String getDeliveryDtc() {
		return deliveryDtc;
	}
	public void setDeliveryDtc(String deliveryDtc) {
		this.deliveryDtc = deliveryDtc;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAgeTxt() {
		return ageTxt;
	}
	public void setAgeTxt(String ageTxt) {
		this.ageTxt = ageTxt;
	}
	public String getAgeU() {
		return ageU;
	}
	public void setAgeU(String ageU) {
		this.ageU = ageU;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getStrain() {
		return strain;
	}
	public void setStrain(String strain) {
		this.strain = strain;
	}
	public String getSbstrain() {
		return sbstrain;
	}
	public void setSbstrain(String sbstrain) {
		this.sbstrain = sbstrain;
	}
	public String getArmCd() {
		return armCd;
	}
	public void setArmCd(String armCd) {
		this.armCd = armCd;
	}
	public String getArm() {
		return arm;
	}
	public void setArm(String arm) {
		this.arm = arm;
	}
	public String getSetCd() {
		return setCd;
	}
	public void setSetCd(String setCd) {
		this.setCd = setCd;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public String getStudyStartingDate() {
		return studyStartingDate;
	}
	public void setStudyStartingDate(String studyStartingDate) {
		this.studyStartingDate = studyStartingDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rfEnDtc == null) ? 0 : rfEnDtc.hashCode());
		result = prime * result + ((rfStDtc == null) ? 0 : rfStDtc.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result + ((strain == null) ? 0 : strain.hashCode());
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
		result = prime * result + ((subjId == null) ? 0 : subjId.hashCode());
		result = prime * result + ((usubjId == null) ? 0 : usubjId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Demographics other = (Demographics) obj;
		if (rfEnDtc == null) {
			if (other.rfEnDtc != null)
				return false;
		} else if (!rfEnDtc.equals(other.rfEnDtc))
			return false;
		if (rfStDtc == null) {
			if (other.rfStDtc != null)
				return false;
		} else if (!rfStDtc.equals(other.rfStDtc))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (strain == null) {
			if (other.strain != null)
				return false;
		} else if (!strain.equals(other.strain))
			return false;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		if (subjId == null) {
			if (other.subjId != null)
				return false;
		} else if (!subjId.equals(other.subjId))
			return false;
		if (usubjId == null) {
			if (other.usubjId != null)
				return false;
		} else if (!usubjId.equals(other.usubjId))
			return false;
		return true;
	}
	

}