package com.idorsia.research.send.domain;

import java.io.Serializable;

public class Comments implements Serializable {
	
	private static final long serialVersionUID = -7648710251303152655L;
	private String studyId; 
	private String domain;
	private String rDomain; 
	private String usubjId; 
	private String poolId; 
	private String coSeq; 
	private String idVar; 
	private String idVarVal; 
	private String coRef; 
	private String coVal; 
	private String coEval; 
	private String coDtc; 
	private String coDy;
	
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
	public String getCoSeq() {
		return coSeq;
	}
	public void setCoSeq(String coSeq) {
		this.coSeq = coSeq;
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
	public String getCoRef() {
		return coRef;
	}
	public void setCoRef(String coRef) {
		this.coRef = coRef;
	}
	public String getCoVal() {
		return coVal;
	}
	public void setCoVal(String coVal) {
		this.coVal = coVal;
	}
	public String getCoEval() {
		return coEval;
	}
	public void setCoEval(String coEval) {
		this.coEval = coEval;
	}
	public String getCoDtc() {
		return coDtc;
	}
	public void setCoDtc(String coDtc) {
		this.coDtc = coDtc;
	}
	public String getCoDy() {
		return coDy;
	}
	public void setCoDy(String coDy) {
		this.coDy = coDy;
	}

}
