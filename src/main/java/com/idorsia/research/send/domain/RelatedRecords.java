package com.idorsia.research.send.domain;

import java.io.Serializable;

public class RelatedRecords implements Serializable {

	private static final long serialVersionUID = 4937322716564257909L;
	private String studyId;
	private String rDomain;
	private String usubjId;
	private String poolId;
	private String idVar;
	private String idVarVal;
	private String relType;
	private String relId;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getrDomain() {
		return rDomain;
	}

	public void setrDomain(String rDomain) {
		this.rDomain = rDomain;
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

	public String getIdVar() {
		return idVar;
	}

	public void setIdVar(String idVar) {
		this.idVar = idVar;
	}

	public String getIdVarVal() {
		return idVarVal;
	}

	public void setIdVarVal(String idVarVal) {
		this.idVarVal = idVarVal;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

}
