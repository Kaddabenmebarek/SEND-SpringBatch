package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Disposition implements Serializable {

	private static final long serialVersionUID = -4296977442775861198L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private Integer dsSeq;
	private String dsTerm;
	private String dsDecod;
	private String dsUschFl;
	private Integer visitdy;
	private Timestamp dsStdtc;
	private String sDsStdtc;
	private Long dsStdy;
	private String dsNomdy;
	private String dsNomLbl;
	private Timestamp stageStartDate;
	private String biosampleState;
	private Long startification;

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

	public Integer getDsSeq() {
		return dsSeq;
	}

	public void setDsSeq(Integer dsSeq) {
		this.dsSeq = dsSeq;
	}

	public String getDsTerm() {
		return dsTerm;
	}

	public void setDsTerm(String dsTerm) {
		this.dsTerm = dsTerm;
	}

	public String getDsDecod() {
		return dsDecod;
	}

	public void setDsDecod(String dsDecod) {
		this.dsDecod = dsDecod;
	}

	public String getDsUschFl() {
		return dsUschFl;
	}

	public void setDsUschFl(String dsUschFl) {
		this.dsUschFl = dsUschFl;
	}

	public Integer getVisitdy() {
		return visitdy;
	}

	public void setVisitdy(Integer visitdy) {
		this.visitdy = visitdy;
	}

	public Timestamp getDsStdtc() {
		return dsStdtc;
	}

	public void setDsStdtc(Timestamp dsStdtc) {
		this.dsStdtc = dsStdtc;
	}

	public String getsDsStdtc() {
		return sDsStdtc;
	}

	public void setsDsStdtc(String sDsStdtc) {
		this.sDsStdtc = sDsStdtc;
	}

	public Long getDsStdy() {
		return dsStdy;
	}

	public void setDsStdy(Long dsStdy) {
		this.dsStdy = dsStdy;
	}

	public String getDsNomdy() {
		return dsNomdy;
	}

	public void setDsNomdy(String dsNomdy) {
		this.dsNomdy = dsNomdy;
	}

	public String getDsNomLbl() {
		return dsNomLbl;
	}

	public void setDsNomLbl(String dsNomLbl) {
		this.dsNomLbl = dsNomLbl;
	}

	public Timestamp getStageStartDate() {
		return stageStartDate;
	}

	public void setStageStartDate(Timestamp stageStartDate) {
		this.stageStartDate = stageStartDate;
	}

	public String getBiosampleState() {
		return biosampleState;
	}

	public void setBiosampleState(String biosampleState) {
		this.biosampleState = biosampleState;
	}

	public Long getStartification() {
		return startification;
	}

	public void setStartification(Long startification) {
		this.startification = startification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((dsStdtc == null) ? 0 : dsStdtc.hashCode());
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
		Disposition other = (Disposition) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (dsStdtc == null) {
			if (other.dsStdtc != null)
				return false;
		} else if (!dsStdtc.equals(other.dsStdtc))
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