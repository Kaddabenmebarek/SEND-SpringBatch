package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class TumorFindings implements Serializable {

	private static final long serialVersionUID = -6470208579193665322L;
	private String studyId;
	private String domain;
	private String usubjId;
	private Integer tfSeq;
	private String tfGrpId;
	private String tfRefId;
	private String tfSpId;
	private String tfTestcd;
	private String tfTest;
	private String tfOrres;
	private String tfStresc;
	private String tfResCat;
	private String tfNam;
	private String tfSpec;
	private String tfAntReg;
	private String tfSpccNd;
	private String tfLat;
	private String tfDir;
	private String tfMethod;
	private String tfEval;
	private String tfDthrel;
	private Timestamp tfDtc;
	private String sTfDtc;
	private Integer tfDy;
	private Long tfDetect;

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

	public Integer getTfSeq() {
		return tfSeq;
	}

	public void setTfSeq(Integer tfSeq) {
		this.tfSeq = tfSeq;
	}

	public String getTfGrpId() {
		return tfGrpId;
	}

	public void setTfGrpId(String tfGrpId) {
		this.tfGrpId = tfGrpId;
	}

	public String getTfRefId() {
		return tfRefId;
	}

	public void setTfRefId(String tfRefId) {
		this.tfRefId = tfRefId;
	}

	public String getTfSpId() {
		return tfSpId;
	}

	public void setTfSpId(String tfSpId) {
		this.tfSpId = tfSpId;
	}

	public String getTfTestcd() {
		return tfTestcd;
	}

	public void setTfTestcd(String tfTestcd) {
		this.tfTestcd = tfTestcd;
	}

	public String getTfTest() {
		return tfTest;
	}

	public void setTfTest(String tfTest) {
		this.tfTest = tfTest;
	}

	public String getTfOrres() {
		return tfOrres;
	}

	public void setTfOrres(String tfOrres) {
		this.tfOrres = tfOrres;
	}

	public String getTfStresc() {
		return tfStresc;
	}

	public void setTfStresc(String tfStresc) {
		this.tfStresc = tfStresc;
	}

	public String getTfResCat() {
		return tfResCat;
	}

	public void setTfResCat(String tfResCat) {
		this.tfResCat = tfResCat;
	}

	public String getTfNam() {
		return tfNam;
	}

	public void setTfNam(String tfNam) {
		this.tfNam = tfNam;
	}

	public String getTfSpec() {
		return tfSpec;
	}

	public void setTfSpec(String tfSpec) {
		this.tfSpec = tfSpec;
	}

	public String getTfAntReg() {
		return tfAntReg;
	}

	public void setTfAntReg(String tfAntReg) {
		this.tfAntReg = tfAntReg;
	}

	public String getTfSpccNd() {
		return tfSpccNd;
	}

	public void setTfSpccNd(String tfSpccNd) {
		this.tfSpccNd = tfSpccNd;
	}

	public String getTfLat() {
		return tfLat;
	}

	public void setTfLat(String tfLat) {
		this.tfLat = tfLat;
	}

	public String getTfDir() {
		return tfDir;
	}

	public void setTfDir(String tfDir) {
		this.tfDir = tfDir;
	}

	public String getTfMethod() {
		return tfMethod;
	}

	public void setTfMethod(String tfMethod) {
		this.tfMethod = tfMethod;
	}

	public String getTfEval() {
		return tfEval;
	}

	public void setTfEval(String tfEval) {
		this.tfEval = tfEval;
	}

	public String getTfDthrel() {
		return tfDthrel;
	}

	public void setTfDthrel(String tfDthrel) {
		this.tfDthrel = tfDthrel;
	}

	public Timestamp getTfDtc() {
		return tfDtc;
	}

	public void setTfDtc(Timestamp tfDtc) {
		this.tfDtc = tfDtc;
	}
	
	public String getsTfDtc() {
		return sTfDtc;
	}

	public void setsTfDtc(String sTfDtc) {
		this.sTfDtc = sTfDtc;
	}

	public Integer getTfDy() {
		return tfDy;
	}

	public void setTfDy(Integer tfDy) {
		this.tfDy = tfDy;
	}

	public Long getTfDetect() {
		return tfDetect;
	}

	public void setTfDetect(Long tfDetect) {
		this.tfDetect = tfDetect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
		result = prime * result + ((tfDtc == null) ? 0 : tfDtc.hashCode());
		result = prime * result + ((tfOrres == null) ? 0 : tfOrres.hashCode());
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
		TumorFindings other = (TumorFindings) obj;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		if (tfDtc == null) {
			if (other.tfDtc != null)
				return false;
		} else if (!tfDtc.equals(other.tfDtc))
			return false;
		if (tfOrres == null) {
			if (other.tfOrres != null)
				return false;
		} else if (!tfOrres.equals(other.tfOrres))
			return false;
		if (usubjId == null) {
			if (other.usubjId != null)
				return false;
		} else if (!usubjId.equals(other.usubjId))
			return false;
		return true;
	}

}
