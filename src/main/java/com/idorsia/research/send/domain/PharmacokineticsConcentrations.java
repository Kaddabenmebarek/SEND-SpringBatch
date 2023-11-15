package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PharmacokineticsConcentrations implements Serializable {

	private static final long serialVersionUID = 2495744000718973496L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String poolId;
	private Integer pcSeq;
	private String pcGrpId;
	private String pcRefId;
	private String pcSpId;
	private String pcTestcd;
	private String pcTest;
	private String pcCat;
	private String pcScat;
	private BigDecimal pcOrres;
	private String pcOrresu;
	private BigDecimal pcStresc;
	private BigDecimal pcStresn;
	private String pcStresu;
	private String pcStat;
	private String pcReasnd;
	private String pcNam;
	private String pcSpec;
	private String pcSpCCnd;
	private String pcSpCFul;
	private String pcMethod;
	private String pcBlFl;
	private String pcFast;
	private String pcDrvFl;
	private String pcLLoq;
	private String pcExcLfl;
	private String pcReaSex;
	private Integer visitdy;
	private Timestamp pcDtc;
	private String sPcDtc;
	private String pcEnDtc;
	private Long pcDy;
	private Long pcEnDy;
	private String pcNomdy;
	private String pcNomLbl;
	private String pcTpt;
	private Integer pcTptNum;
	private String pcEltm;
	private String pcTptRef;
	private String pcRftDtc;
	private String pcEvlInt;
	private String attributeName;
	private Date startDate;
	private PharmacokineticsConcentrationResults results;

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

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public Integer getPcSeq() {
		return pcSeq;
	}

	public void setPcSeq(Integer pcSeq) {
		this.pcSeq = pcSeq;
	}

	public String getPcGrpId() {
		return pcGrpId;
	}

	public void setPcGrpId(String pcGrpId) {
		this.pcGrpId = pcGrpId;
	}

	public String getPcRefId() {
		return pcRefId;
	}

	public void setPcRefId(String pcRefId) {
		this.pcRefId = pcRefId;
	}

	public String getPcSpId() {
		return pcSpId;
	}

	public void setPcSpId(String pcSpId) {
		this.pcSpId = pcSpId;
	}

	public String getPcTestcd() {
		return pcTestcd;
	}

	public void setPcTestcd(String pcTestcd) {
		this.pcTestcd = pcTestcd;
	}

	public String getPcTest() {
		return pcTest;
	}

	public void setPcTest(String pcTest) {
		this.pcTest = pcTest;
	}

	public String getPcCat() {
		return pcCat;
	}

	public void setPcCat(String pcCat) {
		this.pcCat = pcCat;
	}

	public String getPcScat() {
		return pcScat;
	}

	public void setPcScat(String pcScat) {
		this.pcScat = pcScat;
	}

	public BigDecimal getPcOrres() {
		return pcOrres;
	}

	public void setPcOrres(BigDecimal pcOrres) {
		this.pcOrres = pcOrres;
	}

	public String getPcOrresu() {
		return pcOrresu;
	}

	public void setPcOrresu(String pcOrresu) {
		this.pcOrresu = pcOrresu;
	}

	public BigDecimal getPcStresc() {
		return pcStresc;
	}

	public void setPcStresc(BigDecimal pcStresc) {
		this.pcStresc = pcStresc;
	}

	public BigDecimal getPcStresn() {
		return pcStresn;
	}

	public void setPcStresn(BigDecimal pcStresn) {
		this.pcStresn = pcStresn;
	}

	public String getPcStresu() {
		return pcStresu;
	}

	public void setPcStresu(String pcStresu) {
		this.pcStresu = pcStresu;
	}

	public String getPcStat() {
		return pcStat;
	}

	public void setPcStat(String pcStat) {
		this.pcStat = pcStat;
	}

	public String getPcReasnd() {
		return pcReasnd;
	}

	public void setPcReasnd(String pcReasnd) {
		this.pcReasnd = pcReasnd;
	}

	public String getPcNam() {
		return pcNam;
	}

	public void setPcNam(String pcNam) {
		this.pcNam = pcNam;
	}

	public String getPcSpec() {
		return pcSpec;
	}

	public void setPcSpec(String pcSpec) {
		this.pcSpec = pcSpec;
	}

	public String getPcSpCCnd() {
		return pcSpCCnd;
	}

	public void setPcSpCCnd(String pcSpCCnd) {
		this.pcSpCCnd = pcSpCCnd;
	}

	public String getPcSpCFul() {
		return pcSpCFul;
	}

	public void setPcSpCFul(String pcSpCFul) {
		this.pcSpCFul = pcSpCFul;
	}

	public String getPcMethod() {
		return pcMethod;
	}

	public void setPcMethod(String pcMethod) {
		this.pcMethod = pcMethod;
	}

	public String getPcBlFl() {
		return pcBlFl;
	}

	public void setPcBlFl(String pcBlFl) {
		this.pcBlFl = pcBlFl;
	}

	public String getPcFast() {
		return pcFast;
	}

	public void setPcFast(String pcFast) {
		this.pcFast = pcFast;
	}

	public String getPcDrvFl() {
		return pcDrvFl;
	}

	public void setPcDrvFl(String pcDrvFl) {
		this.pcDrvFl = pcDrvFl;
	}

	public String getPcLLoq() {
		return pcLLoq;
	}

	public void setPcLLoq(String pcLLoq) {
		this.pcLLoq = pcLLoq;
	}

	public String getPcExcLfl() {
		return pcExcLfl;
	}

	public void setPcExcLfl(String pcExcLfl) {
		this.pcExcLfl = pcExcLfl;
	}

	public String getPcReaSex() {
		return pcReaSex;
	}

	public void setPcReaSex(String pcReaSex) {
		this.pcReaSex = pcReaSex;
	}

	public Integer getVisitdy() {
		return visitdy;
	}

	public void setVisitdy(Integer visitdy) {
		this.visitdy = visitdy;
	}

	public Timestamp getPcDtc() {
		return pcDtc;
	}

	public void setPcDtc(Timestamp pcDtc) {
		this.pcDtc = pcDtc;
	}

	public String getPcEnDtc() {
		return pcEnDtc;
	}

	public String getsPcDtc() {
		return sPcDtc;
	}

	public void setsPcDtc(String sPcDtc) {
		this.sPcDtc = sPcDtc;
	}

	public void setPcEnDtc(String pcEnDtc) {
		this.pcEnDtc = pcEnDtc;
	}

	public Long getPcDy() {
		return pcDy;
	}

	public void setPcDy(Long pcDy) {
		this.pcDy = pcDy;
	}

	public Long getPcEnDy() {
		return pcEnDy;
	}

	public void setPcEnDy(Long pcEnDy) {
		this.pcEnDy = pcEnDy;
	}

	public String getPcNomdy() {
		return pcNomdy;
	}

	public void setPcNomdy(String pcNomdy) {
		this.pcNomdy = pcNomdy;
	}

	public String getPcNomLbl() {
		return pcNomLbl;
	}

	public void setPcNomLbl(String pcNomLbl) {
		this.pcNomLbl = pcNomLbl;
	}

	public String getPcTpt() {
		return pcTpt;
	}

	public void setPcTpt(String pcTpt) {
		this.pcTpt = pcTpt;
	}

	public Integer getPcTptNum() {
		return pcTptNum;
	}

	public void setPcTptNum(Integer pcTptNum) {
		this.pcTptNum = pcTptNum;
	}

	public String getPcEltm() {
		return pcEltm;
	}

	public void setPcEltm(String pcEltm) {
		this.pcEltm = pcEltm;
	}

	public String getPcTptRef() {
		return pcTptRef;
	}

	public void setPcTptRef(String pcTptRef) {
		this.pcTptRef = pcTptRef;
	}

	public String getPcRftDtc() {
		return pcRftDtc;
	}

	public void setPcRftDtc(String pcRftDtc) {
		this.pcRftDtc = pcRftDtc;
	}

	public String getPcEvlInt() {
		return pcEvlInt;
	}

	public void setPcEvlInt(String pcEvlInt) {
		this.pcEvlInt = pcEvlInt;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public PharmacokineticsConcentrationResults getResults() {
		return results;
	}

	public void setResults(PharmacokineticsConcentrationResults results) {
		this.results = results;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PharmacokineticsConcentrations other = (PharmacokineticsConcentrations) obj;
		if (usubjId == null) {
			if (other.usubjId != null)
				return false;
		} else if (!usubjId.equals(other.usubjId))
			return false;
		return true;
	}
	
}
