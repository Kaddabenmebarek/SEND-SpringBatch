package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TrialElements extends Treatment implements Serializable {

	private static final long serialVersionUID = -5846026998844350500L;
	private String studyId;
	private String domain;
	private String eTcd;
	private String element;
	private String tesTrl;
	private String teeNrl;
	private String teDur;
	private Integer ppgInstanceId;
	private Integer spiInstanceId;
	private Long stratification;
	private Timestamp administrationDate;
	private Date startDate;

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

	public String geteTcd() {
		return eTcd;
	}

	public void seteTcd(String eTcd) {
		this.eTcd = eTcd;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getTesTrl() {
		return tesTrl;
	}

	public void setTesTrl(String tesTrl) {
		this.tesTrl = tesTrl;
	}

	public String getTeeNrl() {
		return teeNrl;
	}

	public void setTeeNrl(String teeNrl) {
		this.teeNrl = teeNrl;
	}

	public String getTeDur() {
		return teDur;
	}

	public void setTeDur(String teDur) {
		this.teDur = teDur;
	}

	public Integer getPpgInstanceId() {
		return ppgInstanceId;
	}

	public void setPpgInstanceId(Integer ppgInstanceId) {
		this.ppgInstanceId = ppgInstanceId;
	}

	public Integer getSpiInstanceId() {
		return spiInstanceId;
	}

	public void setSpiInstanceId(Integer spiInstanceId) {
		this.spiInstanceId = spiInstanceId;
	}

	public Long getStratification() {
		return stratification;
	}

	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}

	public Timestamp getAdministrationDate() {
		return administrationDate;
	}

	public void setAdministrationDate(Timestamp administrationDate) {
		this.administrationDate = administrationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((administrationDate == null) ? 0 : administrationDate.hashCode());
		result = prime * result + ((eTcd == null) ? 0 : eTcd.hashCode());
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
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
		TrialElements other = (TrialElements) obj;
		if (administrationDate == null) {
			if (other.administrationDate != null)
				return false;
		} else if (!administrationDate.equals(other.administrationDate))
			return false;
		if (eTcd == null) {
			if (other.eTcd != null)
				return false;
		} else if (!eTcd.equals(other.eTcd))
			return false;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		return true;
	}

}
