package edu.unl.cse.soft160.a1.pediatric_sepsis;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Gender;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Observation;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Patient;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.PatientRecords;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Vitals;

public class createPatientTest {

	@Test
	public void maleCreatePatientTest() throws IOException {
		boolean correct = true;
		PatientRecords connection = new PatientRecords("admin", "Admin123", "localhost:8080");
		connection.setPatientID("10004M");
		connection.setConnection();
		Patient results = connection.createPatient("10004M");

		Patient expected = new Patient("10004M");
		expected.setFamilyName("Biden");
		expected.setFirstName("Joe");
		expected.setGender(Gender.MALE);
		expected.setBirthDate(LocalDateTime.of(2008, 04, 05, 00, 00));
		Vitals vitals = new Vitals();

		Observation whiteBloodCellCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 10.0);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);
		Observation creatinineCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addCreatinineLevelObservation(creatinineCount);
		Observation bilirubinCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addBilirubinCountObservation(bilirubinCount);
		Observation alanineTransaminaseCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addAlanineTransaminaseObservation(alanineTransaminaseCount);

		Observation systolicBloodPressure = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 120.0);
		vitals.addBloodPressureObservation(systolicBloodPressure);
		Observation heartRate = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 100.0);
		vitals.addHeartRateObservation(heartRate);
		Observation temperature = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 37.0);
		vitals.addBodyTemperatureObservation(temperature);
		Observation respiratoryRate = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 18.0);
		vitals.addRespirationRateObservation(respiratoryRate);
		expected.setVitals(vitals);
		if (expected.getGender() != results.getGender()) {
			correct = false;
		}
		if (!expected.getFamilyName().equals(results.getFamilyName())) {
			correct = false;
		}
		if (!expected.getFirstName().equals(results.getFirstName())) {
			correct = false;
		}
		if (!expected.getBirthDate().equals(results.getBirthDate())) {
			correct = false;
		}
		if (!expected.getVitals().getBilirubinCounts().get(0).getRecord()
				.equals(results.getVitals().getBilirubinCounts().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getAlanineTransaminases().get(0).getRecord()
				.equals(results.getVitals().getAlanineTransaminases().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getBloodPressures().get(0).getRecord()
				.equals(results.getVitals().getBloodPressures().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getBodyTemperatures().get(0).getRecord()
				.equals(results.getVitals().getBodyTemperatures().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getCreatinineLevels().get(0).getRecord()
				.equals(results.getVitals().getCreatinineLevels().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getHeartRates().get(0).getRecord()
				.equals(results.getVitals().getHeartRates().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getRespirationRates().get(0).getRecord()
				.equals(results.getVitals().getRespirationRates().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getWhiteBloodCellCounts().get(0).getRecord()
				.equals(results.getVitals().getWhiteBloodCellCounts().get(0).getRecord())) {
			correct = false;
		}
		assertEquals(true, correct);
	}

	@Test
	public void femaleCreatePatientTest() throws IOException {
		boolean correct = true;
		PatientRecords connection = new PatientRecords("admin", "Admin123", "localhost:8080");
		connection.setPatientID("10005K");
		connection.setConnection();
		Patient results = connection.createPatient("10005K");

		Patient expected = new Patient("10005K");
		expected.setFamilyName("Biden");
		expected.setFirstName("Joe");
		expected.setGender(Gender.FEMALE);
		expected.setBirthDate(LocalDateTime.of(2008, 04, 05, 00, 00));
		Vitals vitals = new Vitals();

		Observation whiteBloodCellCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 10.0);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);
		Observation creatinineCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addCreatinineLevelObservation(creatinineCount);
		Observation bilirubinCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addBilirubinCountObservation(bilirubinCount);
		Observation alanineTransaminaseCount = new Observation(LocalDateTime.of(2018, 12, 05, 11, 42, 43), 2.0);
		vitals.addAlanineTransaminaseObservation(alanineTransaminaseCount);

		Observation systolicBloodPressure = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 120.0);
		vitals.addBloodPressureObservation(systolicBloodPressure);
		Observation heartRate = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 100.0);
		vitals.addHeartRateObservation(heartRate);
		Observation temperature = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 37.0);
		vitals.addBodyTemperatureObservation(temperature);
		Observation respiratoryRate = new Observation(LocalDateTime.of(2018, 12, 05, 11, 40, 03), 18.0);
		vitals.addRespirationRateObservation(respiratoryRate);
		expected.setVitals(vitals);
		if (expected.getGender() != results.getGender()) {
			correct = false;
		}
		if (!expected.getFamilyName().equals(results.getFamilyName())) {
			correct = false;
		}
		if (!expected.getFirstName().equals(results.getFirstName())) {
			correct = false;
		}
		if (!expected.getBirthDate().equals(results.getBirthDate())) {
			correct = false;
		}
		if (!expected.getVitals().getBilirubinCounts().get(0).getRecord()
				.equals(results.getVitals().getBilirubinCounts().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getAlanineTransaminases().get(0).getRecord()
				.equals(results.getVitals().getAlanineTransaminases().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getBloodPressures().get(0).getRecord()
				.equals(results.getVitals().getBloodPressures().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getBodyTemperatures().get(0).getRecord()
				.equals(results.getVitals().getBodyTemperatures().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getCreatinineLevels().get(0).getRecord()
				.equals(results.getVitals().getCreatinineLevels().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getHeartRates().get(0).getRecord()
				.equals(results.getVitals().getHeartRates().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getRespirationRates().get(0).getRecord()
				.equals(results.getVitals().getRespirationRates().get(0).getRecord())) {
			correct = false;
		}
		if (!expected.getVitals().getWhiteBloodCellCounts().get(0).getRecord()
				.equals(results.getVitals().getWhiteBloodCellCounts().get(0).getRecord())) {
			correct = false;
		}
		assertEquals(true, correct);
	}
}
