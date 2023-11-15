package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class VitalSigns implements Serializable {

	private static final long serialVersionUID = 3476397584272685470L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private Integer vsSeq;
	private String vsGrpId;
	private String vsSpId;
	private String vsTestcd;
	private String vsTest;
	private String vsCat;
	private String vsScat;
	private String vsPos;
	private BigDecimal vsOrres;
	private String vsOrresu;
	private BigDecimal vsStresc;
	private BigDecimal vsStresn;
	private String vsStresu;
	private String vsStat;
	private String vsReasnd;
	private String vsLoc;
	private String vsCstate;
	private String vsBlFl;
	private String vsDrvFl;
	private String vsExclFl;
	private String vsReaSex;
	private String vsUschFl;
	private Integer visitdy;
	private Timestamp vsDtc;
	private String sVsDtc;
	private Timestamp vsEnDtc;
	private Long vsDy;
	private Integer vsEnDy;
	private String vsNomDy;
	private String vsNomLbl;
	private String vsTpt;
	private Integer vsTptNum;
	private String vsEltm;
	private String vsTptRef;
	private String vsRftDtc;
	private Date startDate;
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

	public Integer getVsSeq() {
		return vsSeq;
	}

	public void setVsSeq(Integer vsSeq) {
		this.vsSeq = vsSeq;
	}

	public String getVsGrpId() {
		return vsGrpId;
	}

	public void setVsGrpId(String vsGrpId) {
		this.vsGrpId = vsGrpId;
	}

	public String getVsSpId() {
		return vsSpId;
	}

	public void setVsSpId(String vsSpId) {
		this.vsSpId = vsSpId;
	}

	public String getVsTestcd() {
		return vsTestcd;
	}

	public void setVsTestcd(String vsTestcd) {
		this.vsTestcd = vsTestcd;
	}

	public String getVsTest() {
		return vsTest;
	}

	public void setVsTest(String vsTest) {
		this.vsTest = vsTest;
	}

	public String getVsCat() {
		return vsCat;
	}

	public void setVsCat(String vsCat) {
		this.vsCat = vsCat;
	}

	public String getVsScat() {
		return vsScat;
	}

	public void setVsScat(String vsScat) {
		this.vsScat = vsScat;
	}

	public String getVsPos() {
		return vsPos;
	}

	public void setVsPos(String vsPos) {
		this.vsPos = vsPos;
	}

	public BigDecimal getVsOrres() {
		return vsOrres;
	}

	public void setVsOrres(BigDecimal vsOrres) {
		this.vsOrres = vsOrres;
	}

	public String getVsOrresu() {
		return vsOrresu;
	}

	public void setVsOrresu(String vsOrresu) {
		this.vsOrresu = vsOrresu;
	}

	public BigDecimal getVsStresc() {
		return vsStresc;
	}

	public void setVsStresc(BigDecimal vsStresc) {
		this.vsStresc = vsStresc;
	}

	public BigDecimal getVsStresn() {
		return vsStresn;
	}

	public void setVsStresn(BigDecimal vsStresn) {
		this.vsStresn = vsStresn;
	}

	public String getVsStresu() {
		return vsStresu;
	}

	public void setVsStresu(String vsStresu) {
		this.vsStresu = vsStresu;
	}

	public String getVsStat() {
		return vsStat;
	}

	public void setVsStat(String vsStat) {
		this.vsStat = vsStat;
	}

	public String getVsReasnd() {
		return vsReasnd;
	}

	public void setVsReasnd(String vsReasnd) {
		this.vsReasnd = vsReasnd;
	}

	public String getVsLoc() {
		return vsLoc;
	}

	public void setVsLoc(String vsLoc) {
		this.vsLoc = vsLoc;
	}

	public String getVsCstate() {
		return vsCstate;
	}

	public void setVsCstate(String vsCstate) {
		this.vsCstate = vsCstate;
	}

	public String getVsBlFl() {
		return vsBlFl;
	}

	public void setVsBlFl(String vsBlFl) {
		this.vsBlFl = vsBlFl;
	}

	public String getVsDrvFl() {
		return vsDrvFl;
	}

	public void setVsDrvFl(String vsDrvFl) {
		this.vsDrvFl = vsDrvFl;
	}

	public String getVsExclFl() {
		return vsExclFl;
	}

	public void setVsExclFl(String vsExclFl) {
		this.vsExclFl = vsExclFl;
	}

	public String getVsReaSex() {
		return vsReaSex;
	}

	public void setVsReaSex(String vsReaSex) {
		this.vsReaSex = vsReaSex;
	}

	public String getVsUschFl() {
		return vsUschFl;
	}

	public void setVsUschFl(String vsUschFl) {
		this.vsUschFl = vsUschFl;
	}

	public Integer getVisitdy() {
		return visitdy;
	}

	public void setVisitdy(Integer visitdy) {
		this.visitdy = visitdy;
	}

	public Timestamp getVsDtc() {
		return vsDtc;
	}

	public void setVsDtc(Timestamp vsDtc) {
		this.vsDtc = vsDtc;
	}

	public String getsVsDtc() {
		return sVsDtc;
	}

	public void setsVsDtc(String sVsDtc) {
		this.sVsDtc = sVsDtc;
	}

	public Timestamp getVsEnDtc() {
		return vsEnDtc;
	}

	public void setVsEnDtc(Timestamp vsEnDtc) {
		this.vsEnDtc = vsEnDtc;
	}

	public Long getVsDy() {
		return vsDy;
	}

	public void setVsDy(Long vsDy) {
		this.vsDy = vsDy;
	}

	public Integer getVsEnDy() {
		return vsEnDy;
	}

	public void setVsEnDy(Integer vsEnDy) {
		this.vsEnDy = vsEnDy;
	}

	public String getVsNomDy() {
		return vsNomDy;
	}

	public void setVsNomDy(String vsNomDy) {
		this.vsNomDy = vsNomDy;
	}

	public String getVsNomLbl() {
		return vsNomLbl;
	}

	public void setVsNomLbl(String vsNomLbl) {
		this.vsNomLbl = vsNomLbl;
	}

	public String getVsTpt() {
		return vsTpt;
	}

	public void setVsTpt(String vsTpt) {
		this.vsTpt = vsTpt;
	}

	public Integer getVsTptNum() {
		return vsTptNum;
	}

	public void setVsTptNum(Integer vsTptNum) {
		this.vsTptNum = vsTptNum;
	}

	public String getVsEltm() {
		return vsEltm;
	}

	public void setVsEltm(String vsEltm) {
		this.vsEltm = vsEltm;
	}

	public String getVsTptRef() {
		return vsTptRef;
	}

	public void setVsTptRef(String vsTptRef) {
		this.vsTptRef = vsTptRef;
	}

	public String getVsRftDtc() {
		return vsRftDtc;
	}

	public void setVsRftDtc(String vsRftDtc) {
		this.vsRftDtc = vsRftDtc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
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
		result = prime * result + ((subjId == null) ? 0 : subjId.hashCode());
		result = prime * result + ((vsDtc == null) ? 0 : vsDtc.hashCode());
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
		VitalSigns other = (VitalSigns) obj;
		if (subjId == null) {
			if (other.subjId != null)
				return false;
		} else if (!subjId.equals(other.subjId))
			return false;
		if (vsDtc == null) {
			if (other.vsDtc != null)
				return false;
		} else if (!vsDtc.equals(other.vsDtc))
			return false;
		return true;
	}

}
