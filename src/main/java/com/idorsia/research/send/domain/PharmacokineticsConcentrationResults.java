package com.idorsia.research.send.domain;

import java.io.Serializable;

public class PharmacokineticsConcentrationResults implements Serializable {

	private static final long serialVersionUID = -5684691005557523933L;
	private String doseUnit;
	private String sampleWeightUnit;
	private String concentrationUnit;
	private String sampleVolumeUnit;
	private String compound;
	private Double sampleWeight;
	private String dilutionFactor;
	private String vialPlatePosition;
	private String plateNo;
	private String plateAttributes;
	private String concentrationRemark;
	private Double concentration;
	private String dilutionDetails;
	private String route;
	private String eLBDMPK;
	private Double sampleVolume;
	private Integer dose;
	public String getDoseUnit() {
		return doseUnit;
	}
	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}
	public String getSampleWeightUnit() {
		return sampleWeightUnit;
	}
	public void setSampleWeightUnit(String sampleWeightUnit) {
		this.sampleWeightUnit = sampleWeightUnit;
	}
	public String getConcentrationUnit() {
		return concentrationUnit;
	}
	public void setConcentrationUnit(String concentrationUnit) {
		this.concentrationUnit = concentrationUnit;
	}
	public String getSampleVolumeUnit() {
		return sampleVolumeUnit;
	}
	public void setSampleVolumeUnit(String sampleVolumeUnit) {
		this.sampleVolumeUnit = sampleVolumeUnit;
	}
	public String getCompound() {
		return compound;
	}
	public void setCompound(String compound) {
		this.compound = compound;
	}
	public Double getSampleWeight() {
		return sampleWeight;
	}
	public void setSampleWeight(Double sampleWeight) {
		this.sampleWeight = sampleWeight;
	}
	public String getDilutionFactor() {
		return dilutionFactor;
	}
	public void setDilutionFactor(String dilutionFactor) {
		this.dilutionFactor = dilutionFactor;
	}
	public String getVialPlatePosition() {
		return vialPlatePosition;
	}
	public void setVialPlatePosition(String vialPlatePosition) {
		this.vialPlatePosition = vialPlatePosition;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getPlateAttributes() {
		return plateAttributes;
	}
	public void setPlateAttributes(String plateAttributes) {
		this.plateAttributes = plateAttributes;
	}
	public String getConcentrationRemark() {
		return concentrationRemark;
	}
	public void setConcentrationRemark(String concentrationRemark) {
		this.concentrationRemark = concentrationRemark;
	}
	public Double getConcentration() {
		return concentration;
	}
	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}
	public String getDilutionDetails() {
		return dilutionDetails;
	}
	public void setDilutionDetails(String dilutionDetails) {
		this.dilutionDetails = dilutionDetails;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String geteLBDMPK() {
		return eLBDMPK;
	}
	public void seteLBDMPK(String eLBDMPK) {
		this.eLBDMPK = eLBDMPK;
	}
	public Double getSampleVolume() {
		return sampleVolume;
	}
	public void setSampleVolume(Double sampleVolume) {
		this.sampleVolume = sampleVolume;
	}
	public Integer getDose() {
		return dose;
	}
	public void setDose(Integer dose) {
		this.dose = dose;
	}

}