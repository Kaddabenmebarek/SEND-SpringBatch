package com.idorsia.research.send.domain;

import java.io.Serializable;

public class PoolDefinition implements Serializable {

	private static final long serialVersionUID = 2557990391277520425L;
	private String studyId;
	private String poolId;
	private String usubjId;
	private String subjId;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
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
	
}
