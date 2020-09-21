package edu.unl.cse.soft160.a1.pediatric_sepsis;

import java.time.LocalDateTime;

import org.junit.Test;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Condition;
import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Determination;
import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Gender;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Observation;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Patient;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Vitals;
import edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm.PediatricSepsisAlgorithm;
import edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm.Sepsis_Determination;
import junit.framework.TestCase;

public class PediatricSepsisAlgorithmTest extends TestCase {

	@Test
	public void testNoMeasurements() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();

		patient.setVitals(vitals);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testDeceased() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);
		patient.setDeceased(true);

		Vitals vitals = new Vitals();

		patient.setVitals(vitals);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.NOT_APPLICABLE;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testNoBirthDate() {

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);

		Vitals vitals = new Vitals();

		patient.setVitals(vitals);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testNoVitals() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringNoAbnormals() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoHeartRate() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoRespirationRate() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoWhiteBloodCellCount() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoBodyTemperature() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertNoBodyTemperature() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringAbnormalTemperature() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringAbnormalWBC() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringAbnormalTemperatureTooLow() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 35.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringAbnormalWBCTooLow() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 4.0);
		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAbnormalWBCAndTemperature() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAbnormalWBCAndHeartRate() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testContinueMonitoringAbnormalRespiratoryRateAndHeartRate() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 51.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 37.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.CONTINUE_MONITORING;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAllAbnormal() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 51.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertCardioVascularDisease() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 64.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertMultipleOrganDysfunctions() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.PRESENT);
		vitals.setNeurologicDysfuntion(Condition.PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertOneOrganDysfunction() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testNotApplicableTooOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(18);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.NOT_APPLICABLE;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoBloodPressure() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoCreatinineLevel() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoBilirubinCount() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testMissingObservationNoAlanineTransaminase() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAlanineTransaminaseMale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 65.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAlanineTransaminaseMale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalBilirubinCount() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.1);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinineLevel() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.7);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertRespiratoryDysfunction() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertMissingHematologicDysfunctionObservation() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertMissingNeurologicDysfunctionObservation() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertMissingRespiratoryDysfunctionObservation() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 34.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 55.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.MISSING_OBSERVATION;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinineNewborn() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.1);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine2YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(2);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.8);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine4YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(4);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.9);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine8YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(8);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 105.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine11YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(11);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 105.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.1);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine13YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(13);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 117.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.3);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinineNewbornFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusDays(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 65.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.1);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine1YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine2YearOldFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(2);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.8);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine4YearOldFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(4);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.9);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine8YearOldFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(8);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 105.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.0);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine11YearOldFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(11);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 105.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 01.1);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinine13YearOldFemale() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(13);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 117.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.3);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalCreatinineDoublePrevious() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(13);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.FEMALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 117.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 1.2);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		Observation old_heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation old_respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation old_bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation old_whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation old_alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation old_bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation old_bloodPressure = new Observation(LocalDateTime.now(), 117.0);
		Observation old_creatinineLevel = new Observation(LocalDateTime.now(), 0.5);

		vitals.addHeartRateObservation(old_heartRate);
		vitals.addRespirationRateObservation(old_respirationRate);
		vitals.addBodyTemperatureObservation(old_bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(old_whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(old_alanineTransaminase);
		vitals.addBilirubinCountObservation(old_bilirubinCount);
		vitals.addBloodPressureObservation(old_bloodPressure);
		vitals.addCreatinineLevelObservation(old_creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalBilirubin1WeekOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusWeeks(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 15.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 75.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSepsisAlertAbnormalTransaminase1WeekOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusWeeks(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 46.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 14.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 75.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAbnormalRespirationRate6MonthOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusMonths(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 100.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertAbnormalHeartRate6MonthOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusMonths(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 181.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 100.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertLowWBC2WeekOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusWeeks(2);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 4.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 75.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertLowWBC6MonthOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusMonths(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 4.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 100.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertLowWBC1YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 5.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 94.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertLowWBC6YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 4.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 105.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSIRSAlertLowWBC13YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(13);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 4.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 117.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SIRS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertAbnormalBloodPressure2WeekOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusWeeks(2);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 74.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertAbnormalBloodPressure6MonthOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusMonths(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 99.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertAbnormalBloodPressure1YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(1);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 93.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertAbnormalBloodPressure6YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(6);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 104.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

	@Test
	public void testSevereSepsisAlertAbnormalBloodPressure13YearOld() {

		LocalDateTime birthDate = LocalDateTime.now().minusYears(13);

		Patient patient = new Patient("10000X");
		patient.setGender(Gender.MALE);
		patient.setBirthDate(birthDate);

		Vitals vitals = new Vitals();
		Observation heartRate = new Observation(LocalDateTime.now(), 180.0);
		Observation respirationRate = new Observation(LocalDateTime.now(), 50.0);
		Observation bodyTemperature = new Observation(LocalDateTime.now(), 39.0);
		Observation whiteBloodCellCount = new Observation(LocalDateTime.now(), 35.0);

		Observation alanineTransaminase = new Observation(LocalDateTime.now(), 45.0);
		Observation bilirubinCount = new Observation(LocalDateTime.now(), 1.0);
		Observation bloodPressure = new Observation(LocalDateTime.now(), 116.0);
		Observation creatinineLevel = new Observation(LocalDateTime.now(), 0.6);

		vitals.addHeartRateObservation(heartRate);
		vitals.addRespirationRateObservation(respirationRate);
		vitals.addBodyTemperatureObservation(bodyTemperature);
		vitals.addWhiteBloodCellCountObservation(whiteBloodCellCount);

		vitals.addAlanineTransaminaseObservation(alanineTransaminase);
		vitals.addBilirubinCountObservation(bilirubinCount);
		vitals.addBloodPressureObservation(bloodPressure);
		vitals.addCreatinineLevelObservation(creatinineLevel);

		vitals.setHematologicDysfunction(Condition.NOT_PRESENT);
		vitals.setNeurologicDysfuntion(Condition.NOT_PRESENT);
		vitals.setRespiratoryDysfunction(Condition.NOT_PRESENT);

		patient.setVitals(vitals);
		Determination expectedResult = Determination.SEVERE_SEPSIS_ALERT;
		Sepsis_Determination result = PediatricSepsisAlgorithm.analyze(patient);

		assertEquals(expectedResult, result.getDetermination());

	}

}
