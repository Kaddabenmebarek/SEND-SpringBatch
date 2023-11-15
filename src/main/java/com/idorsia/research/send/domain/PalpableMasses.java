package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PalpableMasses implements Serializable {

	private static final long serialVersionUID = -5915586542316189154L;
	
	private String studyId;
	private String domain;
	private String usubjId;
	private Integer pmSeq;
	private String pmGrpId;
	private String pmSpId;
	private String pmTestcd;
	private String pmTest;
	private BigDecimal pmOrres;
	private String pmOrresu;
	private BigDecimal pmStresc;
	private BigDecimal pmStresn;
	private String pmStresu;
	private String pmStat;
	private String pmReasnd;
	private String pmLoc;
	private String pmEval;
	private String pmUschFl;
	private Integer visitdy;
	private Timestamp pmDtc;
	private String sPmDtc;
	private Long pmDy;
	private Long pmNomDy;
	private String pmNomLbl;
	private String testName;
	private Double measure;
	private String attributeName;
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

	public Integer getPmSeq() {
		return pmSeq;
	}

	public void setPmSeq(Integer pmSeq) {
		this.pmSeq = pmSeq;
	}

	public String getPmGrpId() {
		return pmGrpId;
	}

	public void setPmGrpId(String pmGrpId) {
		this.pmGrpId = pmGrpId;
	}

	public String getPmSpId() {
		return pmSpId;
	}

	public void setPmSpId(String pmSpId) {
		this.pmSpId = pmSpId;
	}

	public String getPmTestcd() {
		return pmTestcd;
	}

	public void setPmTestcd(String pmTestcd) {
		this.pmTestcd = pmTestcd;
	}

	public String getPmTest() {
		return pmTest;
	}

	public void setPmTest(String pmTest) {
		this.pmTest = pmTest;
	}

	public BigDecimal getPmOrres() {
		return pmOrres;
	}

	public void setPmOrres(BigDecimal pmOrres) {
		this.pmOrres = pmOrres;
	}

	public String getPmOrresu() {
		return pmOrresu;
	}

	public void setPmOrresu(String pmOrresu) {
		this.pmOrresu = pmOrresu;
	}

	public BigDecimal getPmStresc() {
		return pmStresc;
	}

	public void setPmStresc(BigDecimal pmStresc) {
		this.pmStresc = pmStresc;
	}

	public BigDecimal getPmStresn() {
		return pmStresn;
	}

	public void setPmStresn(BigDecimal pmStresn) {
		this.pmStresn = pmStresn;
	}

	public String getPmStresu() {
		return pmStresu;
	}

	public void setPmStresu(String pmStresu) {
		this.pmStresu = pmStresu;
	}

	public String getPmStat() {
		return pmStat;
	}

	public void setPmStat(String pmStat) {
		this.pmStat = pmStat;
	}

	public String getPmReasnd() {
		return pmReasnd;
	}

	public void setPmReasnd(String pmReasnd) {
		this.pmReasnd = pmReasnd;
	}

	public String getPmLoc() {
		return pmLoc;
	}

	public void setPmLoc(String pmLoc) {
		this.pmLoc = pmLoc;
	}

	public String getPmEval() {
		return pmEval;
	}

	public void setPmEval(String pmEval) {
		this.pmEval = pmEval;
	}

	public String getPmUschFl() {
		return pmUschFl;
	}

	public void setPmUschFl(String pmUschFl) {
		this.pmUschFl = pmUschFl;
	}

	public Integer getVisitdy() {
		return visitdy;
	}

	public void setVisitdy(Integer visitdy) {
		this.visitdy = visitdy;
	}

	public Timestamp getPmDtc() {
		return pmDtc;
	}

	public void setPmDtc(Timestamp pmDtc) {
		this.pmDtc = pmDtc;
	}

	public Long getPmDy() {
		return pmDy;
	}

	public String getsPmDtc() {
		return sPmDtc;
	}

	public void setsPmDtc(String sPmDtc) {
		this.sPmDtc = sPmDtc;
	}

	public void setPmDy(Long pmDy) {
		this.pmDy = pmDy;
	}

	public Long getPmNomDy() {
		return pmNomDy;
	}

	public void setPmNomDy(Long pmNomDy) {
		this.pmNomDy = pmNomDy;
	}

	public String getPmNomLbl() {
		return pmNomLbl;
	}

	public void setPmNomLbl(String pmNomLbl) {
		this.pmNomLbl = pmNomLbl;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
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

	public Long getStratification() {
		return stratification;
	}

	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}

}
