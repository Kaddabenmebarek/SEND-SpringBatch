package com.idorsia.research.send.domain;

import java.util.List;

public class GenericDomain<T> {
	private List<T> list;

	public GenericDomain(List<T> list) {
		super();
		this.list = list;
	}
	
	public List<T> getList() {
		return list;
	}
}
