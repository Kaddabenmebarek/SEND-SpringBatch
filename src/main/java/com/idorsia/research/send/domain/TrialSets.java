package com.idorsia.research.send.domain;

import java.io.Serializable;

public class TrialSets implements Serializable {

	private static final long serialVersionUID = 6388960743282457508L;
	private String studyId;
	private String domain;
	private String setCd;
	private String set;
	private Integer txSeq;
	private String txParmCd;
	private String txParm;
	private String txVal;

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

	public String getSetCd() {
		return setCd;
	}

	public void setSetCd(String setCd) {
		this.setCd = setCd;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public Integer getTxSeq() {
		return txSeq;
	}

	public void setTxSeq(Integer txSeq) {
		this.txSeq = txSeq;
	}

	public String getTxParmCd() {
		return txParmCd;
	}

	public void setTxParmCd(String txParmCd) {
		this.txParmCd = txParmCd;
	}

	public String getTxParm() {
		return txParm;
	}

	public void setTxParm(String txParm) {
		this.txParm = txParm;
	}

	public String getTxVal() {
		return txVal;
	}

	public void setTxVal(String txVal) {
		this.txVal = txVal;
	}

}
