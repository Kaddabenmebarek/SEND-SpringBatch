package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

public class DateDuration implements Serializable {

	private static final long serialVersionUID = 3215537994087079030L;
	private Duration duration;
	private Duration offset;
	private Long phaseDuration;
	private Date firstAssignmentDate;
	private Date animalDeathDate;
	private Date asignmentStageDate;

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Duration getOffset() {
		return offset;
	}

	public void setOffset(Duration offset) {
		this.offset = offset;
	}

	public Long getPhaseDuration() {
		return phaseDuration;
	}

	public void setPhaseDuration(Long phaseDuration) {
		this.phaseDuration = phaseDuration;
	}

	public Date getFirstAssignmentDate() {
		return firstAssignmentDate;
	}

	public void setFirstAssignmentDate(Date firstAssignmentDate) {
		this.firstAssignmentDate = firstAssignmentDate;
	}

	public Date getAnimalDeathDate() {
		return animalDeathDate;
	}

	public void setAnimalDeathDate(Date animalDeathDate) {
		this.animalDeathDate = animalDeathDate;
	}

	public Date getAsignmentStageDate() {
		return asignmentStageDate;
	}

	public void setAsignmentStageDate(Date asignmentStageDate) {
		this.asignmentStageDate = asignmentStageDate;
	}

}
