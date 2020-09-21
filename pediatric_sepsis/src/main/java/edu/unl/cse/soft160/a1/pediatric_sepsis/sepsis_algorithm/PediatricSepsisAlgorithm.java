package edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Condition;
import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Determination;
import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Gender;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Observation;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Patient;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Vitals;

public class PediatricSepsisAlgorithm {
	public static Sepsis_Determination sepsisDetermination = new Sepsis_Determination();

	
	

	public static Sepsis_Determination analyze(Patient patient) {
		
		sepsisDetermination.setDetermination(Determination.MISSING_OBSERVATION);
		sepsisDetermination.setLabJustifications(new ArrayList<String>());
		sepsisDetermination.setUnknownJustifications(new ArrayList<String>());
		sepsisDetermination.setVitalsJustifications(new ArrayList<String>());
		if (patient.getAge() == null) {
			sepsisDetermination.addUnknownJustification("The patient's age is unknown");
		} else if (patient.getAge() >= 18) {
			sepsisDetermination.setDetermination(Determination.NOT_APPLICABLE);
			sepsisDetermination.addLabJustification("The patient's age is: " + patient.getAge() + ", which is >= 18");
		} else if (patient.isDeceased()) {
			sepsisDetermination.setDetermination(Determination.NOT_APPLICABLE);
			sepsisDetermination.addLabJustification("The patient is deceased");
		}

		else if (patient.getMostRecentVitals() != null || patient.getVitals() != null) {
			Vitals vitals = patient.getVitals();
			MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
			// WBC and body temperature cannot both be unrecorded, and at least two
			// observations must be present
			int missingSIRSObservations = getNumberOfMissingSIRSObservations(mostRecentVitals);

			boolean missingWBCAndBodyTemperature = mostRecentVitals.getWhiteBloodCellCount() == null
					&& mostRecentVitals.getBodyTemperature() == null;

			if (!missingWBCAndBodyTemperature) {
				Condition abnormalTemperature = isAbnormalTemperature(patient);
				Condition abnormalWhiteBloodCellCount = isAbnormalWhiteBloodCellCount(patient);
				Condition abnormalHeartRate = isAbnormalHeartRate(patient);
				Condition abnormalRespirationRate = isAbnormalRespirationRate(patient);

				if (abnormalTemperature == Condition.PRESENT || abnormalWhiteBloodCellCount == Condition.PRESENT) {
					int numberOfSIRS = getNumberOfSIRS(abnormalTemperature, abnormalWhiteBloodCellCount,
							abnormalHeartRate, abnormalRespirationRate);

					if (numberOfSIRS >= 2) {
						Condition hematologicDysfunction = vitals.getHematologicDysfunction();
						Condition respiratoryDysfunction = vitals.getRespiratoryDysfunction();
						Condition neurologicDysfunction = vitals.getNeurologicDysfuntion();
						Condition cardiovascularDisease = isCardiovascularDisease(patient);
						Condition hepaticDysfunction = isHepaticDysfunction(patient);
						Condition renalDysfunction = isRenalDysfunction(patient);

						int missingDysfunctionObservations = getNumberOfMissingDysfunctionObservations(
								hematologicDysfunction, respiratoryDysfunction, neurologicDysfunction,
								hepaticDysfunction, renalDysfunction);

						int numberOfDysfunctions = getNumberOfDysfunctions(hematologicDysfunction,
								respiratoryDysfunction, neurologicDysfunction, hepaticDysfunction, renalDysfunction);

						if (cardiovascularDisease == Condition.PRESENT) {
							sepsisDetermination.setDetermination(Determination.SEVERE_SEPSIS_ALERT);
							sepsisDetermination
									.addLabJustification("The patient was diagnosed with cardiovascular disease");

						} else if (numberOfDysfunctions >= 2) {
							sepsisDetermination.setDetermination(Determination.SEVERE_SEPSIS_ALERT);

						} else if (cardiovascularDisease != Condition.UNKNOWN) {

							if (numberOfDysfunctions > 0) {
								if (missingDysfunctionObservations == 0) {
									sepsisDetermination.setDetermination(Determination.SEPSIS_ALERT);
								} // Default determination is MISSING_OBSERVATION, so no else is needed

							} else if (missingDysfunctionObservations == 0) {
								sepsisDetermination.setDetermination(Determination.SIRS_ALERT);

							}
						} else {
							sepsisDetermination.addUnknownJustification(
									"The patient is missing a diagnosis of cardiovascular disease");
						}

						// If all data is present, continue monitoring. Otherwise there is not enough
						// data.
					} else if (missingSIRSObservations == 0) {
						sepsisDetermination.setDetermination(Determination.CONTINUE_MONITORING);
						sepsisDetermination.addVitalsJustification("The patient's vitals did not raise any alarms");

					}
				} else {
					if (abnormalWhiteBloodCellCount == Condition.NOT_PRESENT
							&& abnormalTemperature == Condition.NOT_PRESENT) {
						sepsisDetermination.setDetermination(Determination.CONTINUE_MONITORING);
						sepsisDetermination.addVitalsJustification("The patient's vitals did not raise any alarms");
					} else {
						if (abnormalWhiteBloodCellCount == Condition.UNKNOWN) {
							sepsisDetermination.addUnknownJustification(
									"The patient is missing a white blood cell count observation");
						}
						if (abnormalTemperature == Condition.UNKNOWN) {
							sepsisDetermination
									.addUnknownJustification("The patient is missing a body temperature observation");
						}
					}
				}

			} else {
				if (mostRecentVitals.getBodyTemperature() == null) {
					sepsisDetermination
							.addUnknownJustification("The patient is missing a body temperature observation");
				}
				if (mostRecentVitals.getWhiteBloodCellCount() == null) {
					sepsisDetermination
							.addUnknownJustification("The patient is missing a white blood cell count observation");
				}

			}

		} else {
			sepsisDetermination.addVitalsJustification("The patient has no vitals");
		}

		return sepsisDetermination;

	}

	private static Condition isAbnormalTemperature(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double bodyTemperature = mostRecentVitals.getBodyTemperature();
		Condition abnormalTemperature = Condition.NOT_PRESENT;
		if (bodyTemperature == null) {
			abnormalTemperature = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's body temperature was: UNKNOWN");

		} else if (bodyTemperature > 38.5) {
			abnormalTemperature = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getBodyTemperatureObservation().getTimeOfObservation()
							+ " - Patient's body temperature was: " + bodyTemperature + ", abnormal threshold: > 38.5");

		} else if (bodyTemperature < 36.0) {
			abnormalTemperature = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getBodyTemperatureObservation().getTimeOfObservation()
							+ " - Patient's body temperature was: " + bodyTemperature + ", abnormal threshold: < 36.0");

		}

		return abnormalTemperature;
	}

	private static int getNumberOfDysfunctions(Condition hematologicDysfunction, Condition respiratoryDysfunction,
			Condition neurologicDysfunction, Condition hepaticDysfunction, Condition renalDysfunction) {
		int numberOfDysfunctions = 0;
		if (hematologicDysfunction == Condition.PRESENT) {
			sepsisDetermination.addLabJustification("The patient has been diagnosed with hematologic dysfunction");
			++numberOfDysfunctions;
		}
		if (respiratoryDysfunction == Condition.PRESENT) {
			sepsisDetermination.addLabJustification("The patient has been diagnosed with respiratory dysfunction");
			++numberOfDysfunctions;

		}
		if (neurologicDysfunction == Condition.PRESENT) {
			sepsisDetermination.addLabJustification("The patient has been diagnosed with neurologic dysfunction");
			++numberOfDysfunctions;

		}
		if (hepaticDysfunction == Condition.PRESENT) {
			sepsisDetermination.addLabJustification("The patient has been diagnosed with hepatic dysfunction");
			++numberOfDysfunctions;

		}
		if (renalDysfunction == Condition.PRESENT) {
			sepsisDetermination.addLabJustification("The patient has been diagnosed with renal dysfunction");
			++numberOfDysfunctions;

		}
		return numberOfDysfunctions;
	}

	private static int getNumberOfMissingDysfunctionObservations(Condition hematologicDysfunction,
			Condition respiratoryDysfunction, Condition neurologicDysfunction, Condition hepaticDysfunction,
			Condition renalDysfunction) {
		int missingObservations = 0;
		if (hematologicDysfunction == Condition.UNKNOWN) {
			sepsisDetermination
					.addUnknownJustification("The patient is missing a diagnosis of hematologic dysfunction");
			++missingObservations;
		}
		if (respiratoryDysfunction == Condition.UNKNOWN) {
			sepsisDetermination
					.addUnknownJustification("The patient is missing a diagnosis of respiratory dysfunction");
			++missingObservations;
		}
		if (neurologicDysfunction == Condition.UNKNOWN) {
			sepsisDetermination.addUnknownJustification("The patient is missing a diagnosis of neurologic dysfunction");
			++missingObservations;
		}
		if (hepaticDysfunction == Condition.UNKNOWN) {
			sepsisDetermination.addUnknownJustification("The patient is missing a diagnosis of hepatic dysfunction");
			++missingObservations;
		}
		if (renalDysfunction == Condition.UNKNOWN) {
			sepsisDetermination.addUnknownJustification("The patient is missing a diagnosis of renal dysfunction");
			++missingObservations;
		}
		return missingObservations;
	}

	private static Condition isRenalDysfunction(Patient patient) {
		Condition renalDysfunction = Condition.UNKNOWN;
		if (patient.getMostRecentVitals().getCreatinineLevel() != null) {
			Condition abnormalCreatinineByHistory = isAbnormalCreatinineByHistory(patient);
			Condition abnormalCreatinineByReference = isAbnormalCreatinineByReference(patient);
			if (abnormalCreatinineByHistory == Condition.PRESENT
					|| abnormalCreatinineByReference == Condition.PRESENT) {
				renalDysfunction = Condition.PRESENT;
			} else if (abnormalCreatinineByHistory == Condition.NOT_PRESENT
					&& abnormalCreatinineByReference == Condition.NOT_PRESENT) {
				renalDysfunction = Condition.NOT_PRESENT;
			}
		} else {
			sepsisDetermination.addUnknownJustification("Patient's creatinine level was: UNKNOWN");
		}
		return renalDysfunction;
	}

	private static Condition isAbnormalCreatinineByReference(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double creatinineLevel = mostRecentVitals.getCreatinineLevel();
		Condition abnormalCreatinineLevel = Condition.NOT_PRESENT;
		int age = patient.getAge();
		int ageInDays = (int) Math.floor(ChronoUnit.DAYS.between(patient.getBirthDate(), LocalDateTime.now()));
		Gender gender = patient.getGender();

		if (ageInDays >= 0 && ageInDays < 4) {
			if (creatinineLevel > 1.0) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 1.0");
			}
		} else if (ageInDays >= 4 && age < 2) {
			if (gender == Gender.MALE && creatinineLevel > 0.6) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.6");
			} else if (gender == Gender.FEMALE && creatinineLevel > 0.5) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.5");
			}
		} else if (age >= 2 && age < 4) {
			if (gender == Gender.MALE && creatinineLevel > 0.7) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.7");
			} else if (gender == Gender.FEMALE && creatinineLevel > 0.6) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.6");
			}
		} else if (age >= 4 && age < 8) {
			if (gender == Gender.MALE && creatinineLevel > 0.8) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.8");
			} else if (gender == Gender.FEMALE && creatinineLevel > 0.7) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.7");
			}
		} else if (age >= 8 && age < 11) {
			if (gender == Gender.MALE && creatinineLevel > 0.9) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.9");
			} else if (gender == Gender.FEMALE && creatinineLevel > 0.8) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.8");
			}
		} else if (age >= 11 && age < 13) {
			if (gender == Gender.MALE && creatinineLevel > 1.0) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 1.0");

			} else if (gender == Gender.FEMALE && creatinineLevel > 0.9) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 0.9");
			}
		} else if (age >= 13 && age < 18) {
			if (gender == Gender.MALE && creatinineLevel > 1.2) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 1.2");
			} else if (gender == Gender.FEMALE && creatinineLevel > 1.1) {
				abnormalCreatinineLevel = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's ceatinine level was: " + creatinineLevel
								+ ", abnormal threshold: > 1.1");

			}
		}

		return abnormalCreatinineLevel;
	}

	private static Condition isAbnormalCreatinineByHistory(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double creatinineLevel = mostRecentVitals.getCreatinineLevel();
		Condition abnormalCreatinineLevel = Condition.NOT_PRESENT;
		Vitals vitals = patient.getVitals();
		List<Observation> creatinineObservations = vitals.getCreatinineLevels();

		for (int i = 0; i < creatinineObservations.size(); ++i) {
			Observation pastVitals = creatinineObservations.get(i);
			// The observation was recorded in the past 72 hours.
			if (ChronoUnit.DAYS.between(pastVitals.getTimeOfObservation(),
					patient.getMostRecentVitals().getCreatinineLevelObservation().getTimeOfObservation()) <= 3) {
				Double pastCreatinineLevel = pastVitals.getRecord();
				if (pastCreatinineLevel * 2.0 <= creatinineLevel) {
					abnormalCreatinineLevel = Condition.PRESENT;
					sepsisDetermination.addVitalsJustification(
							mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
									+ " - Patient's creatinine level was: " + creatinineLevel
									+ ", double previous level: " + pastCreatinineLevel);
					break;
				}
			}

		}

		return abnormalCreatinineLevel;
	}

	private static Condition isHepaticDysfunction(Patient patient) {

		Condition abnormalBilirubinCount = isAbnormalBilirubinCount(patient);
		Condition abnormalTransaminase = isAbnormalTransaminase(patient);
		Condition hepaticDysfunction = Condition.UNKNOWN;
		if (abnormalBilirubinCount == Condition.PRESENT || abnormalTransaminase == Condition.PRESENT) {
			hepaticDysfunction = Condition.PRESENT;
		} else if (abnormalBilirubinCount == Condition.NOT_PRESENT && abnormalTransaminase == Condition.NOT_PRESENT) {
			hepaticDysfunction = Condition.NOT_PRESENT;
		}
		return hepaticDysfunction;
	}

	private static Condition isAbnormalBilirubinCount(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double bilirubinCount = mostRecentVitals.getBilirubinCount();
		Condition abnormalBilirubinCount = Condition.NOT_PRESENT;
		int age = patient.getAge();
		int ageInWeeks = (int) ChronoUnit.WEEKS.between(patient.getBirthDate(), LocalDateTime.now());

		if (bilirubinCount == null) {
			abnormalBilirubinCount = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's bilirubin count was: UNKNOWN");

		} else if (ageInWeeks >= 1 && ageInWeeks < 2 && bilirubinCount >= 15.0) {
			abnormalBilirubinCount = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's bilirubin count was: " + bilirubinCount + ", abnormal threshold: >= 15.0");
		} else if (ageInWeeks >= 2 && age < 18 && bilirubinCount > 1.0) {
			abnormalBilirubinCount = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's bilirubin count was: " + bilirubinCount + ", abnormal threshold: > 1.0");
		}
		return abnormalBilirubinCount;
	}

	private static Condition isAbnormalTransaminase(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double alanineTransaminase = mostRecentVitals.getAlanineTransaminase();
		Condition abnormalTransaminase = Condition.NOT_PRESENT;
		Integer age = patient.getAge();
		Gender gender = patient.getGender();

		if (alanineTransaminase == null) {
			abnormalTransaminase = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's alanine transaminase was: UNKNOWN");

		} else if (age >= 1 && gender == Gender.MALE) {
			if (alanineTransaminase > 55.0) {
				abnormalTransaminase = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's alanine transaminase was: " + alanineTransaminase
								+ ", abnormal threshold: > 55.0");
			}
		} else if (alanineTransaminase > 45.0) {
			abnormalTransaminase = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's alanine transaminase was: " + alanineTransaminase
							+ ", abnormal threshold: > 45.0");
		}
		return abnormalTransaminase;
	}

	private static Condition isCardiovascularDisease(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double bloodPressure = mostRecentVitals.getBloodPressure();
		int ageInWeeks = (int) ChronoUnit.WEEKS.between(patient.getBirthDate(), LocalDateTime.now());
		int age = patient.getAge();

		Condition cardiovascularDisease = Condition.NOT_PRESENT;
		if (bloodPressure == null) {
			cardiovascularDisease = Condition.UNKNOWN;
			sepsisDetermination.addVitalsJustification("Patient's blood pressure was: UNKNOWN");

		} else if (ageInWeeks <= 0 && bloodPressure < 65) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 65.0");
		} else if (ageInWeeks >= 1 && ageInWeeks < 4 && bloodPressure < 75) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 75.0");
		} else if (ageInWeeks >= 4 && age < 1 && bloodPressure < 100) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 100.0");
		} else if (age >= 1 && age < 6 && bloodPressure < 94) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 105.0");
		} else if (age >= 6 && age < 13 && bloodPressure < 105) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 105.0");
		} else if (age >= 13 && age < 18 && bloodPressure < 117) {
			cardiovascularDisease = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's systolic blood pressure was: " + bloodPressure
							+ ", abnormal threshold: < 117.0");
		}

		return cardiovascularDisease;
	}

	private static int getNumberOfMissingSIRSObservations(MostRecentVitals mostRecentVitals) {
		Double respirationRate = mostRecentVitals.getRespirationRate();
		Double heartRate = mostRecentVitals.getHeartRate();
		Double whiteBloodCellCount = mostRecentVitals.getWhiteBloodCellCount();
		Double bodyTemperature = mostRecentVitals.getBodyTemperature();
		int missingObservations = 0;
		if (bodyTemperature == null) {
			++missingObservations;
		}
		if (whiteBloodCellCount == null) {
			++missingObservations;
		}
		if (heartRate == null) {
			++missingObservations;
		}
		if (respirationRate == null) {
			++missingObservations;
		}

		return missingObservations;
	}

	private static Condition isAbnormalRespirationRate(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double respirationRate = mostRecentVitals.getRespirationRate();
		int ageInWeeks = (int) ChronoUnit.WEEKS.between(patient.getBirthDate(), LocalDateTime.now());
		int age = patient.getAge();
		Condition abnormalRespirationRate = Condition.NOT_PRESENT;
		if (respirationRate == null) {
			abnormalRespirationRate = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's respiration rate was: UNKNOWN");
		} else if (ageInWeeks <= 0 && respirationRate > 50.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 50.0");
		} else if (ageInWeeks >= 1 && ageInWeeks < 4 && respirationRate > 40.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 40.0");
		} else if (ageInWeeks >= 4 && age < 1 && respirationRate > 34.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 34.0");
		} else if (age >= 1 && age < 6 && respirationRate > 22.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 22.0");
		} else if (age >= 6 && age < 13 && respirationRate > 18.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 18.0");
		} else if (age >= 13 && age < 18 && respirationRate > 14.0) {
			abnormalRespirationRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's respiration rate was: " + respirationRate + ", abnormal threshold: > 14.0");
		}

		return abnormalRespirationRate;
	}

	private static Condition isAbnormalHeartRate(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double heartRate = mostRecentVitals.getHeartRate();
		int ageInWeeks = (int) ChronoUnit.WEEKS.between(patient.getBirthDate(), LocalDateTime.now());
		int age = patient.getAge();
		Condition abnormalHeartRate = Condition.NOT_PRESENT;
		if (heartRate == null) {
			abnormalHeartRate = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's heart rate was: UNKNOWN");

		} else if (ageInWeeks <= 0 && heartRate > 180.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 180.0");

		} else if (ageInWeeks >= 1 && ageInWeeks < 4 && heartRate > 180.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 180.0");
		} else if (ageInWeeks >= 4 && age < 1 && heartRate > 180.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 180.0");
		} else if (age >= 1 && age < 6 && heartRate > 140.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 140.0");
		} else if (age >= 6 && age < 13 && heartRate > 130.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 130.0");
		} else if (age >= 13 && age < 18 && heartRate > 110.0) {
			abnormalHeartRate = Condition.PRESENT;
			sepsisDetermination
					.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
							+ " - Patient's heart rate was: " + heartRate + ", abnormal threshold: > 110.0");
		}
		return abnormalHeartRate;
	}

	private static int getNumberOfSIRS(Condition abnormalTemperature, Condition abnormalWhiteBloodCellCount,
			Condition abnormalHeartRate, Condition abnormalRespirationRate) {

		int numberOfSIRS = 0;
		if (abnormalTemperature == Condition.PRESENT) {
			++numberOfSIRS;
		}
		if (abnormalWhiteBloodCellCount == Condition.PRESENT) {
			++numberOfSIRS;
		}
		if (abnormalHeartRate == Condition.PRESENT) {
			++numberOfSIRS;
		}
		if (abnormalRespirationRate == Condition.PRESENT) {
			++numberOfSIRS;
		}
		return numberOfSIRS;
	}

	private static Condition isAbnormalWhiteBloodCellCount(Patient patient) {
		MostRecentVitals mostRecentVitals = patient.getMostRecentVitals();
		Double whiteBloodCellCount = mostRecentVitals.getWhiteBloodCellCount();
		int age = patient.getAge();
		int ageInWeeks = (int) ChronoUnit.WEEKS.between(patient.getBirthDate(), LocalDateTime.now());

		Condition abnormalWhiteBloodCellCount = Condition.NOT_PRESENT;
		if (whiteBloodCellCount == null) {
			abnormalWhiteBloodCellCount = Condition.UNKNOWN;
			sepsisDetermination.addUnknownJustification("Patient's white blood cell count was: UNKNOWN");
		} else if (ageInWeeks < 1 && ageInWeeks >= 0) {
			if (whiteBloodCellCount > 34.0) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 34.0");
			} else if (whiteBloodCellCount < 5.0) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 5.0");
			}

		} else if (ageInWeeks < 4 && ageInWeeks >= 1) {
			if (whiteBloodCellCount > 19.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 19.5");
			} else if (whiteBloodCellCount < 5.0) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 5.0");
			}
		} else if (age < 1 && ageInWeeks >= 4) {
			if (whiteBloodCellCount > 17.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 17.5");
			} else if (whiteBloodCellCount < 5.0) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 5.0");
			}
		} else if (age < 6 && age >= 1) {
			if (whiteBloodCellCount > 15.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 15.5");
			} else if (whiteBloodCellCount < 6.0) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 6.0");
			}
		} else if (age < 13 && age >= 6) {
			if (whiteBloodCellCount > 13.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 13.5");
			} else if (whiteBloodCellCount < 4.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 4.5");
			}
		} else if (age < 18 && age >= 13) {
			if (whiteBloodCellCount > 11.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: > 11.5");
			} else if (whiteBloodCellCount < 4.5) {
				abnormalWhiteBloodCellCount = Condition.PRESENT;
				sepsisDetermination
						.addVitalsJustification(mostRecentVitals.getRespirationRateObservation().getTimeOfObservation()
								+ " - Patient's white blood cell count was: " + whiteBloodCellCount
								+ ", abnormal threshold: < 4.5");
			}
		}

		return abnormalWhiteBloodCellCount;
	}




}