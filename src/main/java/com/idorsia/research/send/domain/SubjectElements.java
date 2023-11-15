package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.util.Date;

public class SubjectElements implements Serializable {

	private static final long serialVersionUID = 6512104766704438002L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String subjId;
	private String seSec;
	private String etCd;
	private String element;
	private Date sestDtc;
	private Date seenDtc;
	private String sestDtcAsString;
	private String seenDtcAsString;
	private String seUpDes;
	private EtcdElement etcdElement;

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

	public String getSeSec() {
		return seSec;
	}

	public void setSeSec(String seSec) {
		this.seSec = seSec;
	}

	public String getEtCd() {
		return etCd;
	}

	public void setEtCd(String etCd) {
		this.etCd = etCd;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public Date getSestDtc() {
		return sestDtc;
	}

	public void setSestDtc(Date sestDtc) {
		this.sestDtc = sestDtc;
	}

	public Date getSeenDtc() {
		return seenDtc;
	}

	public void setSeenDtc(Date seenDtc) {
		this.seenDtc = seenDtc;
	}

	public String getSeUpDes() {
		return seUpDes;
	}

	public void setSeUpDes(String seUpDes) {
		this.seUpDes = seUpDes;
	}

	public String getSestDtcAsString() {
		return sestDtcAsString;
	}

	public void setSestDtcAsString(String sestDtcAsString) {
		this.sestDtcAsString = sestDtcAsString;
	}

	public String getSeenDtcAsString() {
		return seenDtcAsString;
	}

	public void setSeenDtcAsString(String seenDtcAsString) {
		this.seenDtcAsString = seenDtcAsString;
	}

	public EtcdElement getEtcdElement() {
		return etcdElement;
	}

	public void setEtcdElement(EtcdElement etcdElement) {
		this.etcdElement = etcdElement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((seenDtc == null) ? 0 : seenDtc.hashCode());
		result = prime * result + ((sestDtc == null) ? 0 : sestDtc.hashCode());
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
		SubjectElements other = (SubjectElements) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (seenDtc == null) {
			if (other.seenDtc != null)
				return false;
		} else if (!seenDtc.equals(other.seenDtc))
			return false;
		if (sestDtc == null) {
			if (other.sestDtc != null)
				return false;
		} else if (!sestDtc.equals(other.sestDtc))
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