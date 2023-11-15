package com.idorsia.research.send.domain;

import java.io.Serializable;

public class SendTerminology implements Serializable {

	private static final long serialVersionUID = -8695441206322015978L;

	private String code;
	private String codelistCode;
	private String codelistExtensible;
	private String codelistName;
	private String cDISCSubmissionValue;
	private String cDISCSynonym;
	private String cDISCDefinition;
	private String nCIPreferredTerm;
	private String dateVersion;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodelistCode() {
		return codelistCode;
	}

	public void setCodelistCode(String codelistCode) {
		this.codelistCode = codelistCode;
	}

	public String getCodelistExtensible() {
		return codelistExtensible;
	}

	public void setCodelistExtensible(String codelistExtensible) {
		this.codelistExtensible = codelistExtensible;
	}

	public String getCodelistName() {
		return codelistName;
	}

	public void setCodelistName(String codelistName) {
		this.codelistName = codelistName;
	}

	public String getcDISCSubmissionValue() {
		return cDISCSubmissionValue;
	}

	public void setcDISCSubmissionValue(String cDISCSubmissionValue) {
		this.cDISCSubmissionValue = cDISCSubmissionValue;
	}

	public String getcDISCSynonym() {
		return cDISCSynonym;
	}

	public void setcDISCSynonym(String cDISCSynonym) {
		this.cDISCSynonym = cDISCSynonym;
	}

	public String getcDISCDefinition() {
		return cDISCDefinition;
	}

	public void setcDISCDefinition(String cDISCDefinition) {
		this.cDISCDefinition = cDISCDefinition;
	}

	public String getnCIPreferredTerm() {
		return nCIPreferredTerm;
	}

	public void setnCIPreferredTerm(String nCIPreferredTerm) {
		this.nCIPreferredTerm = nCIPreferredTerm;
	}

	public String getDateVersion() {
		return dateVersion;
	}

	public void setDateVersion(String dateVersion) {
		this.dateVersion = dateVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cDISCDefinition == null) ? 0 : cDISCDefinition.hashCode());
		result = prime * result + ((cDISCSubmissionValue == null) ? 0 : cDISCSubmissionValue.hashCode());
		result = prime * result + ((cDISCSynonym == null) ? 0 : cDISCSynonym.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codelistCode == null) ? 0 : codelistCode.hashCode());
		result = prime * result + ((codelistExtensible == null) ? 0 : codelistExtensible.hashCode());
		result = prime * result + ((codelistName == null) ? 0 : codelistName.hashCode());
		result = prime * result + ((dateVersion == null) ? 0 : dateVersion.hashCode());
		result = prime * result + ((nCIPreferredTerm == null) ? 0 : nCIPreferredTerm.hashCode());
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
		SendTerminology other = (SendTerminology) obj;
		if (cDISCDefinition == null) {
			if (other.cDISCDefinition != null)
				return false;
		} else if (!cDISCDefinition.equals(other.cDISCDefinition))
			return false;
		if (cDISCSubmissionValue == null) {
			if (other.cDISCSubmissionValue != null)
				return false;
		} else if (!cDISCSubmissionValue.equals(other.cDISCSubmissionValue))
			return false;
		if (cDISCSynonym == null) {
			if (other.cDISCSynonym != null)
				return false;
		} else if (!cDISCSynonym.equals(other.cDISCSynonym))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codelistCode == null) {
			if (other.codelistCode != null)
				return false;
		} else if (!codelistCode.equals(other.codelistCode))
			return false;
		if (codelistExtensible == null) {
			if (other.codelistExtensible != null)
				return false;
		} else if (!codelistExtensible.equals(other.codelistExtensible))
			return false;
		if (codelistName == null) {
			if (other.codelistName != null)
				return false;
		} else if (!codelistName.equals(other.codelistName))
			return false;
		if (dateVersion == null) {
			if (other.dateVersion != null)
				return false;
		} else if (!dateVersion.equals(other.dateVersion))
			return false;
		if (nCIPreferredTerm == null) {
			if (other.nCIPreferredTerm != null)
				return false;
		} else if (!nCIPreferredTerm.equals(other.nCIPreferredTerm))
			return false;
		return true;
	}

}
