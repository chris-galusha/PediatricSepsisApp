package edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend;

import java.time.LocalDateTime;

public class Observation {
	private LocalDateTime timeOfObservation = null;
	private Double record = null;

	public Observation(LocalDateTime timeOfObservation, Double record) {
		this.timeOfObservation = timeOfObservation;
		this.record = record;
	}

	public LocalDateTime getTimeOfObservation() {
		return timeOfObservation;
	}

	public Double getRecord() {
		return record;
	}
}