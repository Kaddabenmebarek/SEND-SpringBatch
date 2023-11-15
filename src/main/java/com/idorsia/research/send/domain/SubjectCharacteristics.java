package com.idorsia.research.send.domain;

import java.io.Serializable;

public class SubjectCharacteristics implements Serializable {

	private static final long serialVersionUID = 1199428157735835406L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String scSeq;
	private String scGrpId;
	private String scTestcd;
	private String scTest;
	private String scOrres;
	private String scOrresu;
	private String scStresc;
	private String scStresn;
	private String scStresu;
	private String scDtc;
	private String scDy;

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

	public String getScSeq() {
		return scSeq;
	}

	public void setScSeq(String scSeq) {
		this.scSeq = scSeq;
	}

	public String getScGrpId() {
		return scGrpId;
	}

	public void setScGrpId(String scGrpId) {
		this.scGrpId = scGrpId;
	}

	public String getScTestcd() {
		return scTestcd;
	}

	public void setScTestcd(String scTestcd) {
		this.scTestcd = scTestcd;
	}

	public String getScTest() {
		return scTest;
	}

	public void setScTest(String scTest) {
		this.scTest = scTest;
	}

	public String getScOrres() {
		return scOrres;
	}

	public void setScOrres(String scOrres) {
		this.scOrres = scOrres;
	}

	public String getScOrresu() {
		return scOrresu;
	}

	public void setScOrresu(String scOrresu) {
		this.scOrresu = scOrresu;
	}

	public String getScStresc() {
		return scStresc;
	}

	public void setScStresc(String scStresc) {
		this.scStresc = scStresc;
	}

	public String getScStresn() {
		return scStresn;
	}

	public void setScStresn(String scStresn) {
		this.scStresn = scStresn;
	}

	public String getScStresu() {
		return scStresu;
	}

	public void setScStresu(String scStresu) {
		this.scStresu = scStresu;
	}

	public String getScDtc() {
		return scDtc;
	}

	public void setScDtc(String scDtc) {
		this.scDtc = scDtc;
	}

	public String getScDy() {
		return scDy;
	}

	public void setScDy(String scDy) {
		this.scDy = scDy;
	}

}