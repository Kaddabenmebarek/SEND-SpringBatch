package com.idorsia.research.send.domain;

import java.io.Serializable;

public class EtcdElement implements Serializable {

	private static final long serialVersionUID = -7221809517953264508L;

	private Integer namedTreatmentId;
	private String namedTreatmentName;
	private String etcd;
	private String element;
	private Integer seSeq;

	public EtcdElement() {
		super();
	}

	public EtcdElement(Integer namedTreatmentId, String namedTreatmentName, String etcd, String element) {
		super();
		this.namedTreatmentId = namedTreatmentId;
		this.namedTreatmentName = namedTreatmentName;
		this.etcd = etcd;
		this.element = element;
	}

	public Integer getNamedTreatmentId() {
		return namedTreatmentId;
	}

	public void setNamedTreatmentId(Integer namedTreatmentId) {
		this.namedTreatmentId = namedTreatmentId;
	}

	public String getNamedTreatmentName() {
		return namedTreatmentName;
	}

	public void setNamedTreatmentName(String namedTreatmentName) {
		this.namedTreatmentName = namedTreatmentName;
	}

	public String getEtcd() {
		return etcd;
	}

	public void setEtcd(String etcd) {
		this.etcd = etcd;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public Integer getSeSeq() {
		return seSeq;
	}

	public void setSeSeq(Integer seSeq) {
		this.seSeq = seSeq;
	}

}
