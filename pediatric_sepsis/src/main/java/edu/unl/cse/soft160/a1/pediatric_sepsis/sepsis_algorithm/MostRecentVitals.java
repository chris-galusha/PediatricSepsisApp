package edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm;

import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Observation;

public class MostRecentVitals {
	private Observation heartRateObservation = null;
	private Observation respirationRateObservation = null;
	private Observation bodyTemperatureObservation = null;
	private Observation creatinineLevelObservation = null;
	private Observation bilirubinCountObservation = null;
	private Observation bloodPressureObservation = null;
	private Observation whiteBloodCellCountObservation = null;
	private Observation alanineTransaminaseObservation = null;

	public Observation getHeartRateObservation() {
		return heartRateObservation;
	}

	public void setHeartRateObservation(Observation heartRateObservation) {
		this.heartRateObservation = heartRateObservation;
	}

	public Observation getRespirationRateObservation() {
		return respirationRateObservation;
	}

	public void setRespirationRateObservation(Observation respirationRateObservation) {
		this.respirationRateObservation = respirationRateObservation;
	}

	public Observation getBodyTemperatureObservation() {
		return bodyTemperatureObservation;
	}

	public void setBodyTemperatureObservation(Observation bodyTemperatureObservation) {
		this.bodyTemperatureObservation = bodyTemperatureObservation;
	}

	public Observation getCreatinineLevelObservation() {
		return creatinineLevelObservation;
	}

	public void setCreatinineLevelObservation(Observation creatinineLevelObservation) {
		this.creatinineLevelObservation = creatinineLevelObservation;
	}

	public Observation getBilirubinCountObservation() {
		return bilirubinCountObservation;
	}

	public void setBilirubinCountObservation(Observation bilirubinCountObservation) {
		this.bilirubinCountObservation = bilirubinCountObservation;
	}

	public Observation getBloodPressureObservation() {
		return bloodPressureObservation;
	}

	public void setBloodPressureObservation(Observation systolicBloodPressureObservation) {
		this.bloodPressureObservation = systolicBloodPressureObservation;
	}

	public Observation getWhiteBloodCellCountObservation() {
		return whiteBloodCellCountObservation;
	}

	public void setWhiteBloodCellCountObservation(Observation whiteBloodCellCountObservation) {
		this.whiteBloodCellCountObservation = whiteBloodCellCountObservation;
	}

	public Observation getAlanineTransaminaseObservation() {
		return alanineTransaminaseObservation;
	}

	public void setAlanineTransaminaseObservation(Observation alanineTransaminaseObservation) {
		this.alanineTransaminaseObservation = alanineTransaminaseObservation;
	}

	public Double getHeartRate() {
		Double heartRate = null;
		if (this.getHeartRateObservation() != null) {
			heartRate = this.getHeartRateObservation().getRecord();
		}
		return heartRate;
	}

	public Double getRespirationRate() {

		Double respirationRate = null;
		if (this.getRespirationRateObservation() != null) {
			respirationRate = this.getRespirationRateObservation().getRecord();
		}
		return respirationRate;
	}

	public Double getBodyTemperature() {
		Double bodyTemperature = null;
		if (this.getBodyTemperatureObservation() != null) {
			bodyTemperature = this.getBodyTemperatureObservation().getRecord();
		}
		return bodyTemperature;
	}

	public Double getCreatinineLevel() {

		Double creatinineLevel = null;
		if (this.getCreatinineLevelObservation() != null) {
			creatinineLevel = this.getCreatinineLevelObservation().getRecord();
		}
		return creatinineLevel;

	}

	public Double getBilirubinCount() {
		Double bilirubinCount = null;
		if (this.getBilirubinCountObservation() != null) {
			bilirubinCount = this.getBilirubinCountObservation().getRecord();
		}
		return bilirubinCount;
	}

	public Double getBloodPressure() {
		Double bloodPressure = null;
		if (this.getBloodPressureObservation() != null) {
			bloodPressure = this.getBloodPressureObservation().getRecord();
		}
		return bloodPressure;
	}

	public Double getWhiteBloodCellCount() {
		Double whiteBloodCellCount = null;
		if (this.getWhiteBloodCellCountObservation() != null) {
			whiteBloodCellCount = this.getWhiteBloodCellCountObservation().getRecord();
		}
		return whiteBloodCellCount;
	}

	public Double getAlanineTransaminase() {
		Double alanineTransaminase = null;
		if (this.getAlanineTransaminaseObservation() != null) {
			alanineTransaminase = this.getAlanineTransaminaseObservation().getRecord();
		}
		return alanineTransaminase;
	}

}
