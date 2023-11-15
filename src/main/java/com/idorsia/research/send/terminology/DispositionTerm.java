package com.idorsia.research.send.terminology;

public enum DispositionTerm {

	ACCIDENTAL_DEATH("ACCIDENTAL DEATH"), 
	FOUND_DEAD("FOUND DEAD"), 
	INTERIM_SACRIFICE("INTERIM SACRIFICE"),
	MISSING("MISSING"), 
	MORIBUND_SACRIFICE("MORIBUND SACRIFICE"), 
	NON_MORIBUND_SACRIFICE("NON-MORIBUND SACRIFICE"),
	RECOVERY_SACRIFICE("RECOVERY SACRIFICE"), 
	REMOVED_FROM_STUDY_ALIVE("REMOVED FROM STUDY ALIVE"),
	TERMINAL_SACRIFICE("TERMINAL SACRIFICE");

	private String value;

	DispositionTerm(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
