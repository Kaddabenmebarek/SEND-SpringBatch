package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class BodyWeightGain extends DateDuration implements Serializable{

	private static final long serialVersionUID = 1875108717407245826L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private Integer bgSeq;
	private String bgTestCd;
	private String bgTest;
	private BigDecimal bgOrres;
	private String bgOrresu;
	private String bgStresc;
	private String bgStresn;
	private String bgStresu;
	private String bgStat;
	private String bgReasNd;
	private String bgExclFl;
	private String bgReaSex;
	private Timestamp bgDtc;
	private Timestamp bgEnDtc;
	private Timestamp startDate;
	private String sbgDtc;
	private String sbgEnDtc;
	private Long bgDy;
	private Long bgEndy;
	private List<Date> bodyWeightDateOfMeasure;
	private Long stratification;

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

	public Integer getBgSeq() {
		return bgSeq;
	}

	public void setBgSeq(Integer bgSeq) {
		this.bgSeq = bgSeq;
	}

	public String getBgTestCd() {
		return bgTestCd;
	}

	public void setBgTestCd(String bgTestCd) {
		this.bgTestCd = bgTestCd;
	}

	public String getBgTest() {
		return bgTest;
	}

	public void setBgTest(String bgTest) {
		this.bgTest = bgTest;
	}

	public BigDecimal getBgOrres() {
		return bgOrres;
	}

	public void setBgOrres(BigDecimal bgOrres) {
		this.bgOrres = bgOrres;
	}

	public String getBgOrresu() {
		return bgOrresu;
	}

	public void setBgOrresu(String bgOrresu) {
		this.bgOrresu = bgOrresu;
	}

	public String getBgStresc() {
		return bgStresc;
	}

	public void setBgStresc(String bgStresc) {
		this.bgStresc = bgStresc;
	}

	public String getBgStresn() {
		return bgStresn;
	}

	public void setBgStresn(String bgStresn) {
		this.bgStresn = bgStresn;
	}

	public String getBgStresu() {
		return bgStresu;
	}

	public void setBgStresu(String bgStresu) {
		this.bgStresu = bgStresu;
	}

	public String getBgStat() {
		return bgStat;
	}

	public void setBgStat(String bgStat) {
		this.bgStat = bgStat;
	}

	public String getBgReasNd() {
		return bgReasNd;
	}

	public void setBgReasNd(String bgReasNd) {
		this.bgReasNd = bgReasNd;
	}

	public String getBgExclFl() {
		return bgExclFl;
	}

	public void setBgExclFl(String bgExclFl) {
		this.bgExclFl = bgExclFl;
	}

	public String getBgReaSex() {
		return bgReaSex;
	}

	public void setBgReaSex(String bgReaSex) {
		this.bgReaSex = bgReaSex;
	}

	public Timestamp getBgDtc() {
		return bgDtc;
	}

	public void setBgDtc(Timestamp bgDtc) {
		this.bgDtc = bgDtc;
	}

	public Timestamp getBgEnDtc() {
		return bgEnDtc;
	}

	public void setBgEnDtc(Timestamp bgEnDtc) {
		this.bgEnDtc = bgEnDtc;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getSbgDtc() {
		return sbgDtc;
	}

	public void setSbgDtc(String sbgDtc) {
		this.sbgDtc = sbgDtc;
	}

	public String getSbgEnDtc() {
		return sbgEnDtc;
	}

	public void setSbgEnDtc(String sbgEnDtc) {
		this.sbgEnDtc = sbgEnDtc;
	}

	public Long getBgDy() {
		return bgDy;
	}

	public void setBgDy(Long bgDy) {
		this.bgDy = bgDy;
	}

	public Long getBgEndy() {
		return bgEndy;
	}

	public void setBgEndy(Long bgEndy) {
		this.bgEndy = bgEndy;
	}

	public List<Date> getBodyWeightDateOfMeasure() {
		return bodyWeightDateOfMeasure;
	}

	public void setBodyWeightDateOfMeasure(List<Date> bodyWeightDateOfMeasure) {
		this.bodyWeightDateOfMeasure = bodyWeightDateOfMeasure;
	}

	public Long getStratification() {
		return stratification;
	}

	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}

	public BodyWeightGain copy() {
		try {
			return (BodyWeightGain) super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bgDtc == null) ? 0 : bgDtc.hashCode());
		result = prime * result + ((bgOrres == null) ? 0 : bgOrres.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
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
		BodyWeightGain other = (BodyWeightGain) obj;
		if (bgDtc == null) {
			if (other.bgDtc != null)
				return false;
		} else if (!bgDtc.equals(other.bgDtc))
			return false;
		if (bgOrres == null) {
			if (other.bgOrres != null)
				return false;
		} else if (!bgOrres.equals(other.bgOrres))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
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