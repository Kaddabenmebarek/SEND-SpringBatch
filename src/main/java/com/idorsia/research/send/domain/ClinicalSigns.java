package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ClinicalSigns implements Serializable {

	private static final long serialVersionUID = -2028640190789289839L;
	private String studyId; 	
	private String domain; 
	private String usubjId; 
	private String subjId;
	private String poolId; 
	private String focId; 
	private Integer clSeq; 
	private String clRefId;
	private String clgrpId; 
	private String clspId; 
	private String clTestCd; 
	private String clTest; 
	private String clCat; 
	private String clScat; 
	private String clBodSys; 
	private String clOrres; 
	private String clStresc; 
	private String clResCat; 
	private String clReasNd; 
	private String clLoc; 
	private String clEval; 
	private String clSev; 
	private String clExclFl; 
	private String clReaSex; 
	private String clUschFl; 
	private Long visitdy; 
	private Timestamp clDtc; 
	private String sClDtc;
	private Timestamp clEndtc; 
	private Long clDy; 
	private String clEndy; 
	private String clNomdy; 
	private String clNomlbl; 
	private String clTpt; 
	private Integer clTptnum; 
	private String clEltm; 
	private String clTptRef; 
	private Timestamp clRftDtc;
	private String attributeName;
	private Long stratification;
	private Date startDate;
	private String clstat;
	
	private String Observation;
	private String Timepoint;
	private String Localisation;
	
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
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getFocId() {
		return focId;
	}
	public void setFocId(String focId) {
		this.focId = focId;
	}
	public Integer getClSeq() {
		return clSeq;
	}
	public void setClSeq(Integer clSeq) {
		this.clSeq = clSeq;
	}
	public String getClgrpId() {
		return clgrpId;
	}
	public void setClgrpId(String clgrpId) {
		this.clgrpId = clgrpId;
	}
	public String getClspId() {
		return clspId;
	}
	public void setClspId(String clspId) {
		this.clspId = clspId;
	}
	public String getClTestCd() {
		return clTestCd;
	}
	public void setClTestCd(String clTestCd) {
		this.clTestCd = clTestCd;
	}
	public String getClTest() {
		return clTest;
	}
	public void setClTest(String clTest) {
		this.clTest = clTest;
	}
	public String getClCat() {
		return clCat;
	}
	public void setClCat(String clCat) {
		this.clCat = clCat;
	}
	public String getClScat() {
		return clScat;
	}
	public void setClScat(String clScat) {
		this.clScat = clScat;
	}
	public String getClBodSys() {
		return clBodSys;
	}
	public void setClBodSys(String clBodSys) {
		this.clBodSys = clBodSys;
	}
	public String getClOrres() {
		return clOrres;
	}
	public void setClOrres(String clOrres) {
		this.clOrres = clOrres;
	}
	public String getClStresc() {
		return clStresc;
	}
	public void setClStresc(String clStresc) {
		this.clStresc = clStresc;
	}
	public String getClResCat() {
		return clResCat;
	}
	public void setClResCat(String clResCat) {
		this.clResCat = clResCat;
	}
	public String getClReasNd() {
		return clReasNd;
	}
	public void setClReasNd(String clReasNd) {
		this.clReasNd = clReasNd;
	}
	public String getClLoc() {
		return clLoc;
	}
	public void setClLoc(String clLoc) {
		this.clLoc = clLoc;
	}
	public String getClEval() {
		return clEval;
	}
	public void setClEval(String clEval) {
		this.clEval = clEval;
	}
	public String getClSev() {
		return clSev;
	}
	public void setClSev(String clSev) {
		this.clSev = clSev;
	}
	public String getClExclFl() {
		return clExclFl;
	}
	public void setClExclFl(String clExclFl) {
		this.clExclFl = clExclFl;
	}
	public String getClReaSex() {
		return clReaSex;
	}
	public void setClReaSex(String clReaSex) {
		this.clReaSex = clReaSex;
	}
	public String getClUschFl() {
		return clUschFl;
	}
	public void setClUschFl(String clUschFl) {
		this.clUschFl = clUschFl;
	}
	public Long getVisitdy() {
		return visitdy;
	}
	public void setVisitdy(Long visitdy) {
		this.visitdy = visitdy;
	}
	public Timestamp getClDtc() {
		return clDtc;
	}
	public void setClDtc(Timestamp clDtc) {
		this.clDtc = clDtc;
	}
	public Timestamp getClEndtc() {
		return clEndtc;
	}
	public void setClEndtc(Timestamp clEndtc) {
		this.clEndtc = clEndtc;
	}
	public Long getClDy() {
		return clDy;
	}
	public void setClDy(Long clDy) {
		this.clDy = clDy;
	}
	public String getClEndy() {
		return clEndy;
	}
	public void setClEndy(String clEndy) {
		this.clEndy = clEndy;
	}
	public String getClNomdy() {
		return clNomdy;
	}
	public void setClNomdy(String clNomdy) {
		this.clNomdy = clNomdy;
	}
	public String getClNomlbl() {
		return clNomlbl;
	}
	public void setClNomlbl(String clNomlbl) {
		this.clNomlbl = clNomlbl;
	}
	public String getClTpt() {
		return clTpt;
	}
	public void setClTpt(String clTpt) {
		this.clTpt = clTpt;
	}
	public Integer getClTptnum() {
		return clTptnum;
	}
	public void setClTptnum(Integer clTptnum) {
		this.clTptnum = clTptnum;
	}
	public String getClEltm() {
		return clEltm;
	}
	public void setClEltm(String clEltm) {
		this.clEltm = clEltm;
	}
	public String getClTptRef() {
		return clTptRef;
	}
	public void setClTptRef(String clTptRef) {
		this.clTptRef = clTptRef;
	}
	public Timestamp getClRftDtc() {
		return clRftDtc;
	}
	public void setClRftDtc(Timestamp clRftDtc) {
		this.clRftDtc = clRftDtc;
	}
	public String getClRefId() {
		return clRefId;
	}
	public void setClRefId(String clRefId) {
		this.clRefId = clRefId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public Long getStratification() {
		return stratification;
	}
	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getsClDtc() {
		return sClDtc;
	}
	public void setsClDtc(String sClDtc) {
		this.sClDtc = sClDtc;
	}
	public String getObservation() {
		return Observation;
	}
	public void setObservation(String observation) {
		Observation = observation;
	}
	public String getTimepoint() {
		return Timepoint;
	}
	public void setTimepoint(String timepoint) {
		Timepoint = timepoint;
	}
	public String getLocalisation() {
		return Localisation;
	}
	public void setLocalisation(String localisation) {
		Localisation = localisation;
	}
	public String getClstat() {
		return clstat;
	}
	public void setClstat(String clstat) {
		this.clstat = clstat;
	}
	
	
}