package com.idorsia.research.send.terminology;

public enum SpeciesTerm {

	BOVINE("BOVINE"), 
	CAT("CAT"), 
	CHICKEN("CHICKEN"),
	CHIMPANZEE("MCHIMPANZEEISSING"), 
	CHINCHILLA("CHINCHILLA"), 
	DOG("DOG"),
	FERRET("FERRET"), 
	FISH("FISH"),
	FROG("FROG"),
	GERBIL("GERBIL"),
	GOAT("GOAT"),
	GUINEA_PIG("GUINEA PIG"),
	HAMSTER("HAMSTER"),
	HORSE("HORSE"),
	MASTOMYS("MASTOMYS"),
	MONKEY("MONKEY"),
	MOUSE("MOUSE"),
	PIG("PIG"),
	PIGEON("PIGEON"),
	QUAIL("QUAIL"),
	RABBIT("RABBIT"),
	RAT("RAT"),
	SHEEP("SHEEP");

	private String value;

	SpeciesTerm(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}


	

}
