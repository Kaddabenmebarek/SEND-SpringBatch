package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.util.Date;

public class Exposure extends DateDuration implements Serializable {

	private static final long serialVersionUID = -2646429996307111215L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private String poolId;
	private String focId;
	private Integer exSeq;
	private String exTrt;
	private Double exDose;
	private String exDosTxt;
	private String exDosU;
	private String exDosFrm;
	private String exDosFrq;
	private String exRoute;
	private String exLot;
	private String exLoc;
	private String exMethod;
	private String exTrtv;
	private String exVamt;
	private String exVamtu;
	private String exAdj;
	private Date exStdtc;
	private String sExStdtc;
	private Date exEnDtc;
	private Long exStDy;
	private Long exEnDy;
	private String exDur;
	private String exTpt;
	private String exTptNum;
	private Integer exEltm;
	private String exTptref;
	private String exRftDtc;
	private String name;
	private Integer ppgId;
	private Integer spiId;
	private Double amountVal;
	private String amountUnit;
	private Date startDate;
	private Long stratification;
	private String rRule;
	private String diseaseName;

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

	public Integer getExSeq() {
		return exSeq;
	}

	public void setExSeq(Integer exSeq) {
		this.exSeq = exSeq;
	}

	public String getExTrt() {
		return exTrt;
	}

	public void setExTrt(String exTrt) {
		this.exTrt = exTrt;
	}

	public Double getExDose() {
		return exDose;
	}

	public void setExDose(Double exDose) {
		this.exDose = exDose;
	}

	public String getExDosTxt() {
		return exDosTxt;
	}

	public void setExDosTxt(String exDosTxt) {
		this.exDosTxt = exDosTxt;
	}

	public String getExDosU() {
		return exDosU;
	}

	public void setExDosU(String exDosU) {
		this.exDosU = exDosU;
	}

	public String getExDosFrm() {
		return exDosFrm;
	}

	public void setExDosFrm(String exDosFrm) {
		this.exDosFrm = exDosFrm;
	}

	public String getExDosFrq() {
		return exDosFrq;
	}

	public void setExDosFrq(String exDosFrq) {
		this.exDosFrq = exDosFrq;
	}

	public String getExRoute() {
		return exRoute;
	}

	public void setExRoute(String exRoute) {
		this.exRoute = exRoute;
	}

	public String getExLot() {
		return exLot;
	}

	public void setExLot(String exLot) {
		this.exLot = exLot;
	}

	public String getExLoc() {
		return exLoc;
	}

	public void setExLoc(String exLoc) {
		this.exLoc = exLoc;
	}

	public String getExMethod() {
		return exMethod;
	}

	public void setExMethod(String exMethod) {
		this.exMethod = exMethod;
	}

	public String getExTrtv() {
		return exTrtv;
	}

	public void setExTrtv(String exTrtv) {
		this.exTrtv = exTrtv;
	}

	public String getExVamt() {
		return exVamt;
	}

	public void setExVamt(String exVamt) {
		this.exVamt = exVamt;
	}

	public String getExVamtu() {
		return exVamtu;
	}

	public void setExVamtu(String exVamtu) {
		this.exVamtu = exVamtu;
	}

	public String getExAdj() {
		return exAdj;
	}

	public void setExAdj(String exAdj) {
		this.exAdj = exAdj;
	}

	public Date getExStdtc() {
		return exStdtc;
	}

	public void setExStdtc(Date exStdtc) {
		this.exStdtc = exStdtc;
	}

	public String getsExStdtc() {
		return sExStdtc;
	}

	public void setsExStdtc(String sExStdtc) {
		this.sExStdtc = sExStdtc;
	}

	public Date getExEnDtc() {
		return exEnDtc;
	}

	public void setExEnDtc(Date exEnDtc) {
		this.exEnDtc = exEnDtc;
	}

	public Long getExStDy() {
		return exStDy;
	}

	public void setExStDy(Long exStDy) {
		this.exStDy = exStDy;
	}

	public Long getExEnDy() {
		return exEnDy;
	}

	public void setExEnDy(Long exEnDy) {
		this.exEnDy = exEnDy;
	}

	public String getExDur() {
		return exDur;
	}

	public void setExDur(String exDur) {
		this.exDur = exDur;
	}

	public String getExTpt() {
		return exTpt;
	}

	public void setExTpt(String exTpt) {
		this.exTpt = exTpt;
	}

	public String getExTptNum() {
		return exTptNum;
	}

	public void setExTptNum(String exTptNum) {
		this.exTptNum = exTptNum;
	}

	public Integer getExEltm() {
		return exEltm;
	}

	public void setExEltm(Integer exEltm) {
		this.exEltm = exEltm;
	}

	public String getExTptref() {
		return exTptref;
	}

	public void setExTptref(String exTptref) {
		this.exTptref = exTptref;
	}

	public String getExRftDtc() {
		return exRftDtc;
	}

	public void setExRftDtc(String exRftDtc) {
		this.exRftDtc = exRftDtc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPpgId() {
		return ppgId;
	}

	public void setPpgId(Integer ppgId) {
		this.ppgId = ppgId;
	}

	public Integer getSpiId() {
		return spiId;
	}

	public void setSpiId(Integer spiId) {
		this.spiId = spiId;
	}

	public Double getAmountVal() {
		return amountVal;
	}

	public void setAmountVal(Double amountVal) {
		this.amountVal = amountVal;
	}

	public String getAmountUnit() {
		return amountUnit;
	}

	public void setAmountUnit(String amountUnit) {
		this.amountUnit = amountUnit;
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

	public String getrRule() {
		return rRule;
	}

	public void setrRule(String rRule) {
		this.rRule = rRule;
	}
	
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((exDosFrm == null) ? 0 : exDosFrm.hashCode());
		result = prime * result + ((exDosFrq == null) ? 0 : exDosFrq.hashCode());
		result = prime * result + ((exDosU == null) ? 0 : exDosU.hashCode());
		result = prime * result + ((exDose == null) ? 0 : exDose.hashCode());
		result = prime * result + ((exRoute == null) ? 0 : exRoute.hashCode());
		result = prime * result + ((exStDy == null) ? 0 : exStDy.hashCode());
		result = prime * result + ((exTrt == null) ? 0 : exTrt.hashCode());
		result = prime * result + ((exTrtv == null) ? 0 : exTrtv.hashCode());
		result = prime * result + ((exVamt == null) ? 0 : exVamt.hashCode());
		result = prime * result + ((exVamtu == null) ? 0 : exVamtu.hashCode());
		result = prime * result + ((sExStdtc == null) ? 0 : sExStdtc.hashCode());
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
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
		Exposure other = (Exposure) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (exDosFrm == null) {
			if (other.exDosFrm != null)
				return false;
		} else if (!exDosFrm.equals(other.exDosFrm))
			return false;
		if (exDosFrq == null) {
			if (other.exDosFrq != null)
				return false;
		} else if (!exDosFrq.equals(other.exDosFrq))
			return false;
		if (exDosU == null) {
			if (other.exDosU != null)
				return false;
		} else if (!exDosU.equals(other.exDosU))
			return false;
		if (exDose == null) {
			if (other.exDose != null)
				return false;
		} else if (!exDose.equals(other.exDose))
			return false;
		if (exRoute == null) {
			if (other.exRoute != null)
				return false;
		} else if (!exRoute.equals(other.exRoute))
			return false;
		if (exStDy == null) {
			if (other.exStDy != null)
				return false;
		} else if (!exStDy.equals(other.exStDy))
			return false;
		if (exTrt == null) {
			if (other.exTrt != null)
				return false;
		} else if (!exTrt.equals(other.exTrt))
			return false;
		if (exTrtv == null) {
			if (other.exTrtv != null)
				return false;
		} else if (!exTrtv.equals(other.exTrtv))
			return false;
		if (exVamt == null) {
			if (other.exVamt != null)
				return false;
		} else if (!exVamt.equals(other.exVamt))
			return false;
		if (exVamtu == null) {
			if (other.exVamtu != null)
				return false;
		} else if (!exVamtu.equals(other.exVamtu))
			return false;
		if (sExStdtc == null) {
			if (other.sExStdtc != null)
				return false;
		} else if (!sExStdtc.equals(other.sExStdtc))
			return false;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		if (usubjId == null) {
			if (other.usubjId != null)
				return false;
		} else if (!usubjId.equals(other.usubjId))
			return false;
		return true;
	}

	
	
}