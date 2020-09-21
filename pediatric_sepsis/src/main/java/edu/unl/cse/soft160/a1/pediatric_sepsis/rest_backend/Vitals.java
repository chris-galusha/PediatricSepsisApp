package edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend;

import java.util.ArrayList;
import java.util.List;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Condition;

public class Vitals {
	private List<Observation> heartRates = new ArrayList<Observation>();
	private List<Observation> respirationRates = new ArrayList<Observation>();
	private List<Observation> bodyTemperatures = new ArrayList<Observation>();
	private List<Observation> creatinineLevels = new ArrayList<Observation>();
	private List<Observation> bilirubinCounts = new ArrayList<Observation>();
	private List<Observation> bloodPressures = new ArrayList<Observation>();
	private List<Observation> whiteBloodCellCounts = new ArrayList<Observation>();
	private List<Observation> alanineTransaminases = new ArrayList<Observation>();
	private Condition hematologicDysfunction = Condition.UNKNOWN;
	private Condition neurologicDysfuntion = Condition.UNKNOWN;
	private Condition respiratoryDysfunction = Condition.UNKNOWN;

	public List<Observation> getHeartRates() {
		return heartRates;
	}

	public void setHeartRates(List<Observation> heartRates) {
		this.heartRates = heartRates;
	}

	public List<Observation> getRespirationRates() {
		return respirationRates;
	}

	public void setRespirationRates(List<Observation> respirationRates) {
		this.respirationRates = respirationRates;
	}

	public List<Observation> getBodyTemperatures() {
		return bodyTemperatures;
	}

	public void setBodyTemperatures(List<Observation> bodyTemperatures) {
		this.bodyTemperatures = bodyTemperatures;
	}

	public List<Observation> getCreatinineLevels() {
		return creatinineLevels;
	}

	public void setCreatinineLevels(List<Observation> creatinineLevels) {
		this.creatinineLevels = creatinineLevels;
	}

	public List<Observation> getBilirubinCounts() {
		return bilirubinCounts;
	}

	public void setBilirubinCounts(List<Observation> bilirubinCounts) {
		this.bilirubinCounts = bilirubinCounts;
	}

	public List<Observation> getBloodPressures() {
		return bloodPressures;
	}

	public void setBloodPressures(List<Observation> bloodPressures) {
		this.bloodPressures = bloodPressures;
	}

	public List<Observation> getWhiteBloodCellCounts() {
		return whiteBloodCellCounts;
	}

	public void setWhiteBloodCellCounts(List<Observation> whiteBloodCellCounts) {
		this.whiteBloodCellCounts = whiteBloodCellCounts;
	}

	public Condition getHematologicDysfunction() {
		return hematologicDysfunction;
	}

	public void setHematologicDysfunction(Condition hematologicDysfunction) {
		this.hematologicDysfunction = hematologicDysfunction;
	}

	public Condition getNeurologicDysfuntion() {
		return neurologicDysfuntion;
	}

	public void setNeurologicDysfuntion(Condition neurologicDysfuntion) {
		this.neurologicDysfuntion = neurologicDysfuntion;
	}

	public Condition getRespiratoryDysfunction() {
		return respiratoryDysfunction;
	}

	public void setRespiratoryDysfunction(Condition respiratoryDysfunction) {
		this.respiratoryDysfunction = respiratoryDysfunction;
	}

	public List<Observation> getAlanineTransaminases() {
		return alanineTransaminases;
	}

	public void setAlanineTransaminases(List<Observation> alanineTransaminases) {
		this.alanineTransaminases = alanineTransaminases;
	}

	public Observation getMostRecentObservation(List<Observation> observations) {
		Observation mostRecentObservation = null;
		if (observations.size() != 0) {
			mostRecentObservation = observations.get(0);
		}
		for (int i = 0; i < observations.size(); i++) {
			if (observations.get(i).getTimeOfObservation().isAfter(mostRecentObservation.getTimeOfObservation())) {
				mostRecentObservation = observations.get(0);
			}
		}
		return mostRecentObservation;
	}

	public void addHeartRateObservation(Observation observation) {
		heartRates.add(observation);
	}

	public void addRespirationRateObservation(Observation observation) {
		respirationRates.add(observation);
	}

	public void addBodyTemperatureObservation(Observation observation) {
		bodyTemperatures.add(observation);
	}

	public void addCreatinineLevelObservation(Observation observation) {
		creatinineLevels.add(observation);
	}

	public void addBilirubinCountObservation(Observation observation) {
		bilirubinCounts.add(observation);
	}

	public void addBloodPressureObservation(Observation observation) {
		bloodPressures.add(observation);
	}

	public void addWhiteBloodCellCountObservation(Observation observation) {
		whiteBloodCellCounts.add(observation);
	}

	public void addAlanineTransaminaseObservation(Observation observation) {
		alanineTransaminases.add(observation);
	}

}
