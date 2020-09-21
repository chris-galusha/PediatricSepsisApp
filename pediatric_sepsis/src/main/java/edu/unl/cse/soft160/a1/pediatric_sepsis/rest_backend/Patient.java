package edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Gender;
import edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm.MostRecentVitals;

public class Patient {
	private String UUID = null;
	private String firstName = null;
	private String familyName = null;
	private LocalDateTime birthDate = null;
	private Gender gender = null;
	private Vitals vitals = null;
	private String location = null;
	private boolean deceased = false;

	public Patient(String UUID) {
		this.UUID = UUID;
	}

	public String getUUID() {
		return UUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Vitals getVitals() {
		return vitals;
	}

	public void setVitals(Vitals vitals) {
		this.vitals = vitals;
	}

	public Integer getAge() {
		Integer ageInYears = null;
		if (birthDate != null) {
			ageInYears = (int) ChronoUnit.YEARS.between(birthDate, LocalDateTime.now());
		}
		return ageInYears;
	}

	public MostRecentVitals getMostRecentVitals() {
		MostRecentVitals mostRecentVitals = null;
		if (this.getVitals() != null) {
			vitals = this.getVitals();
			mostRecentVitals = new MostRecentVitals();

			if (vitals.getMostRecentObservation(vitals.getAlanineTransaminases()) != null) {
				mostRecentVitals.setAlanineTransaminaseObservation(
						vitals.getMostRecentObservation(vitals.getAlanineTransaminases()));
			}
			if (vitals.getMostRecentObservation(vitals.getBilirubinCounts()) != null) {

				mostRecentVitals
						.setBilirubinCountObservation(vitals.getMostRecentObservation(vitals.getBilirubinCounts()));
			}
			if (vitals.getMostRecentObservation(vitals.getBloodPressures()) != null) {
				mostRecentVitals
						.setBloodPressureObservation(vitals.getMostRecentObservation(vitals.getBloodPressures()));
			}
			if (vitals.getMostRecentObservation(vitals.getBodyTemperatures()) != null) {

				mostRecentVitals
						.setBodyTemperatureObservation(vitals.getMostRecentObservation(vitals.getBodyTemperatures()));
			}

			if (vitals.getMostRecentObservation(vitals.getCreatinineLevels()) != null) {

				mostRecentVitals
						.setCreatinineLevelObservation(vitals.getMostRecentObservation(vitals.getCreatinineLevels()));
			}
			if (vitals.getMostRecentObservation(vitals.getHeartRates()) != null) {

				mostRecentVitals.setHeartRateObservation(vitals.getMostRecentObservation(vitals.getHeartRates()));
			}
			if (vitals.getMostRecentObservation(vitals.getRespirationRates()) != null) {

				mostRecentVitals
						.setRespirationRateObservation(vitals.getMostRecentObservation(vitals.getRespirationRates()));
			}
			if (vitals.getMostRecentObservation(vitals.getWhiteBloodCellCounts()) != null) {
				mostRecentVitals.setWhiteBloodCellCountObservation(
						vitals.getMostRecentObservation(vitals.getWhiteBloodCellCounts()));
			}

		}
		return mostRecentVitals;

	}

	public boolean isDeceased() {
		return deceased;
	}

	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
