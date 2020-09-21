package edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend;

import java.io.IOException;

import edu.unl.cse.soft160.rest_connector.connector.OpenMRSConnection;
import edu.unl.cse.soft160.rest_connector.connector.PatientRecord;
import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Gender;
import edu.unl.cse.soft160.rest_connector.connector.ObservationRecord;

public class PatientRecords {
	private String username;
	private String password;
	private String serverLocation;
	private String patientID;
	private OpenMRSConnection connection;
	private PatientRecord patientRecord;

	public PatientRecords(String username, String password, String serverLocation) throws IOException {
		this.username = username;
		this.password = password;
		this.serverLocation = serverLocation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerLocation() {
		return serverLocation;
	}

	public void setServerLocation(String serverLocation) {
		this.serverLocation = serverLocation;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public void setConnection() throws IOException {
		this.connection = new OpenMRSConnection(this.serverLocation, this.username, this.password);
	}

	public OpenMRSConnection getConnection() {
		return connection;
	}

	public void setPatientRecord() throws IOException {
		this.patientRecord = connection.getPatientRecord(this.patientID);
	}

	public PatientRecord getPatientRecord() {
		return patientRecord;
	}

	public Patient createPatient(String patientID) throws IOException {
		Patient patient = new Patient(patientID);
		this.patientRecord = connection.getPatientRecord(patientID);
		patient.setFamilyName(patientRecord.getFamilyName());
		patient.setFirstName(patientRecord.getGivenName());
		patient.setBirthDate(patientRecord.getBirthDate().atStartOfDay());
		patient.setDeceased(patientRecord.getDeceased());
		if (Character.toUpperCase(patientRecord.getGenderCode()) == 'M') {
			patient.setGender(Gender.MALE);
		} else {
			patient.setGender(Gender.FEMALE);
		}
		Vitals vitals = new Vitals();
		patient.setVitals(vitals);
		for (ObservationRecord observationRecord : connection.getObservationRecords(patientRecord.getUUID())) {
			if (observationRecord.getConcept().equals("Systolic blood pressure")) {
				Observation systolicBloodPressure = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addBloodPressureObservation(systolicBloodPressure);
			} else if (observationRecord.getConcept().equals("Respiratory rate")) {
				Observation respiratoryRate = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addRespirationRateObservation(respiratoryRate);
			} else if (observationRecord.getConcept().equals("Bilirubin Total (mg/dL)")) {
				Observation bilirubinCount = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addBilirubinCountObservation(bilirubinCount);
			} else if (observationRecord.getConcept().equals("Temperature (C)")) {
				Observation temperature = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addBodyTemperatureObservation(temperature);
			} else if (observationRecord.getConcept().equals("Pulse")) {
				Observation heartRate = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addHeartRateObservation(heartRate);
			} else if (observationRecord.getConcept().equals("Leukocytes (#/µL)")) {
				Observation whiteBloodCellCount = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement() / 1000);
				vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);
			} else if (observationRecord.getConcept().equals("Creatinine in Blood (mg/dL)")) {
				Observation creatinineCount = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addCreatinineLevelObservation(creatinineCount);
			} else if (observationRecord.getConcept().equals("Alanine transaminase (U/L)")) {
				Observation alanineTransaminaseCount = new Observation(observationRecord.getTimestamp(),
						observationRecord.getMeasurement());
				vitals.addAlanineTransaminaseObservation(alanineTransaminaseCount);
			}
		}

		return patient;
	}
}
