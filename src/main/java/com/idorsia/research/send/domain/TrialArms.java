package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class TrialArms implements Serializable {

	private static final long serialVersionUID = 382263665046648720L;
	private String studyId;
	private String domain;
	private String armCd;
	private String arm;
	private String taEtOrd;
	private String etCd;
	private String element;
	private String taBranch;
	private String taTrans;
	private String epoch;
	private Integer ppgInstanceId;
	private Integer spiInstanceId;
	private Timestamp administrationDate;

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

	public String getTaEtOrd() {
		return taEtOrd;
	}

	public void setTaEtOrd(String taEtOrd) {
		this.taEtOrd = taEtOrd;
	}

	public String getEtCd() {
		return etCd;
	}

	public void setEtCd(String etCd) {
		this.etCd = etCd;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getTaBranch() {
		return taBranch;
	}

	public void setTaBranch(String taBranch) {
		this.taBranch = taBranch;
	}

	public String getTaTrans() {
		return taTrans;
	}

	public void setTaTrans(String taTrans) {
		this.taTrans = taTrans;
	}

	public String getEpoch() {
		return epoch;
	}

	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}

	public Integer getPpgInstanceId() {
		return ppgInstanceId;
	}

	public void setPpgInstanceId(Integer ppgInstanceId) {
		this.ppgInstanceId = ppgInstanceId;
	}

	public Integer getSpiInstanceId() {
		return spiInstanceId;
	}

	public void setSpiInstanceId(Integer spiInstanceId) {
		this.spiInstanceId = spiInstanceId;
	}

	public Timestamp getAdministrationDate() {
		return administrationDate;
	}

	public void setAdministrationDate(Timestamp administrationDate) {
		this.administrationDate = administrationDate;
	}

}
