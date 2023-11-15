package com.idorsia.research.send.domain;

import java.io.Serializable;

public class TrialSummary implements Serializable {

	private static final long serialVersionUID = -2475015795158123578L;
	private String studyId;
	private String domain;
	private String tsSeq;
	private String tsGrpId;
	private String tsParmCd;
	private String tsParm;
	private String tsVal;
	private String tsValNf;

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

	public String getTsSeq() {
		return tsSeq;
	}

	public void setTsSeq(String tsSeq) {
		this.tsSeq = tsSeq;
	}

	public String getTsGrpId() {
		return tsGrpId;
	}

	public void setTsGrpId(String tsGrpId) {
		this.tsGrpId = tsGrpId;
	}

	public String getTsParmCd() {
		return tsParmCd;
	}

	public void setTsParmCd(String tsParmCd) {
		this.tsParmCd = tsParmCd;
	}

	public String getTsParm() {
		return tsParm;
	}

	public void setTsParm(String tsParm) {
		this.tsParm = tsParm;
	}

	public String getTsVal() {
		return tsVal;
	}

	public void setTsVal(String tsVal) {
		this.tsVal = tsVal;
	}

	public String getTsValNf() {
		return tsValNf;
	}

	public void setTsValNf(String tsValNf) {
		this.tsValNf = tsValNf;
	}

}
