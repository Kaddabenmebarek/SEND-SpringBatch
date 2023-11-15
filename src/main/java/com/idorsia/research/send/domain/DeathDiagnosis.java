package com.idorsia.research.send.domain;

import java.io.Serializable;

public class DeathDiagnosis implements Serializable {

	private static final long serialVersionUID = 1601124119566849015L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String ddSeq;
	private String ddTestCd;
	private String ddTest;
	private String ddOrres;
	private String ddStresc;
	private String ddResCat;
	private String ddEval;
	private String ddDtc;
	private String ddDy;

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

	public String getDdSeq() {
		return ddSeq;
	}

	public void setDdSeq(String ddSeq) {
		this.ddSeq = ddSeq;
	}

	public String getDdTestCd() {
		return ddTestCd;
	}

	public void setDdTestCd(String ddTestCd) {
		this.ddTestCd = ddTestCd;
	}

	public String getDdTest() {
		return ddTest;
	}

	public void setDdTest(String ddTest) {
		this.ddTest = ddTest;
	}

	public String getDdOrres() {
		return ddOrres;
	}

	public void setDdOrres(String ddOrres) {
		this.ddOrres = ddOrres;
	}

	public String getDdStresc() {
		return ddStresc;
	}

	public void setDdStresc(String ddStresc) {
		this.ddStresc = ddStresc;
	}

	public String getDdResCat() {
		return ddResCat;
	}

	public void setDdResCat(String ddResCat) {
		this.ddResCat = ddResCat;
	}

	public String getDdEval() {
		return ddEval;
	}

	public void setDdEval(String ddEval) {
		this.ddEval = ddEval;
	}

	public String getDdDtc() {
		return ddDtc;
	}

	public void setDdDtc(String ddDtc) {
		this.ddDtc = ddDtc;
	}

	public String getDdDy() {
		return ddDy;
	}

	public void setDdDy(String ddDy) {
		this.ddDy = ddDy;
	}

}
