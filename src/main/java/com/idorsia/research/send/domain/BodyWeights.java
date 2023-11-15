package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BodyWeights extends DateDuration implements Serializable{

	private static final long serialVersionUID = -7380179014783070089L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private Integer bwSec;
	private String bwRefId;
	private String bwTestCD;
	private String bwTest;
	private String bwOrres;
	private String bwOrresu;
	private String bwSTresc;
	private String bwSTresn;
	private String bwSTresu;
	private String bwStat;
	private String bwExclFl;
	private String bwReaSex;
	private String bwUschFl;
	private String bwSBlfl;// to remove
	private String visidity;
	private Timestamp bwDtc;
	private Timestamp startDate;
	private String sbwDtc;
	private Long bwDy;
	private String bwnomdy;
	private String bwnomlbl;
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

	public Integer getBwSec() {
		return bwSec;
	}

	public void setBwSec(Integer bwSec) {
		this.bwSec = bwSec;
	}

	public String getBwRefId() {
		return bwRefId;
	}

	public void setBwRefId(String bwRefId) {
		this.bwRefId = bwRefId;
	}

	public String getBwTestCD() {
		return bwTestCD;
	}

	public void setBwTestCD(String bwTestCD) {
		this.bwTestCD = bwTestCD;
	}

	public String getBwTest() {
		return bwTest;
	}

	public void setBwTest(String bwTest) {
		this.bwTest = bwTest;
	}

	public String getBwOrres() {
		return bwOrres;
	}

	public void setBwOrres(String bwOrres) {
		this.bwOrres = bwOrres;
	}

	public String getBwOrresu() {
		return bwOrresu;
	}

	public void setBwOrresu(String bwOrresu) {
		this.bwOrresu = bwOrresu;
	}

	public String getBwSTresc() {
		return bwSTresc;
	}

	public void setBwSTresc(String bwSTresc) {
		this.bwSTresc = bwSTresc;
	}

	public String getBwSTresn() {
		return bwSTresn;
	}

	public void setBwSTresn(String bwSTresn) {
		this.bwSTresn = bwSTresn;
	}

	public String getBwSTresu() {
		return bwSTresu;
	}

	public void setBwSTresu(String bwSTresu) {
		this.bwSTresu = bwSTresu;
	}

	public String getBwStat() {
		return bwStat;
	}

	public void setBwStat(String bwStat) {
		this.bwStat = bwStat;
	}

	public String getBwExclFl() {
		return bwExclFl;
	}

	public void setBwExclFl(String bwExclFl) {
		this.bwExclFl = bwExclFl;
	}

	public String getBwReaSex() {
		return bwReaSex;
	}

	public void setBwReaSex(String bwReaSex) {
		this.bwReaSex = bwReaSex;
	}

	public String getBwUschFl() {
		return bwUschFl;
	}

	public void setBwUschFl(String bwUschFl) {
		this.bwUschFl = bwUschFl;
	}

	public String getBwSBlfl() {
		return bwSBlfl;
	}

	public void setBwSBlfl(String bwSBlfl) {
		this.bwSBlfl = bwSBlfl;
	}

	public String getVisidity() {
		return visidity;
	}

	public void setVisidity(String visidity) {
		this.visidity = visidity;
	}

	public Timestamp getBwDtc() {
		return bwDtc;
	}

	public void setBwDtc(Timestamp bwDtc) {
		this.bwDtc = bwDtc;
	}

	public String getSbwDtc() {
		return sbwDtc;
	}

	public void setSbwDtc(String sbwDtc) {
		this.sbwDtc = sbwDtc;
	}

	public Long getBwDy() {
		return bwDy;
	}

	public void setBwDy(Long bwDy) {
		this.bwDy = bwDy;
	}

	public String getBwnomdy() {
		return bwnomdy;
	}

	public void setBwnomdy(String bwnomdy) {
		this.bwnomdy = bwnomdy;
	}

	public String getBwnomlbl() {
		return bwnomlbl;
	}

	public void setBwnomlbl(String bwnomlbl) {
		this.bwnomlbl = bwnomlbl;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Long getStratification() {
		return stratification;
	}

	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bwDtc == null) ? 0 : bwDtc.hashCode());
		result = prime * result + ((bwOrres == null) ? 0 : bwOrres.hashCode());
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
		BodyWeights other = (BodyWeights) obj;
		if (bwDtc == null) {
			if (other.bwDtc != null)
				return false;
		} else if (!bwDtc.equals(other.bwDtc))
			return false;
		if (bwOrres == null) {
			if (other.bwOrres != null)
				return false;
		} else if (!bwOrres.equals(other.bwOrres))
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