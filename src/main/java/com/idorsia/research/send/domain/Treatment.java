package com.idorsia.research.send.domain;

import java.io.Serializable;

public class Treatment implements Serializable {
	
	private static final long serialVersionUID = -1943325284967209800L;
	private String studyId;
	private String compound;
	private String dose;
	private String application;
	private String unit;
	private String compound2;
	private String dose2;
	private String application2;
	private String unit2;
	private String phase;
	
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public String getCompound() {
		return compound;
	}
	public void setCompound(String compound) {
		this.compound = compound;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCompound2() {
		return compound2;
	}
	public void setCompound2(String compound2) {
		this.compound2 = compound2;
	}
	public String getDose2() {
		return dose2;
	}
	public void setDose2(String dose2) {
		this.dose2 = dose2;
	}
	public String getApplication2() {
		return application2;
	}
	public void setApplication2(String application2) {
		this.application2 = application2;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	
	

}
