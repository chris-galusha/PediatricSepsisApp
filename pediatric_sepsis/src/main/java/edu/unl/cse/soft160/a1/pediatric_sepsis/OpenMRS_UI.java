package edu.unl.cse.soft160.a1.pediatric_sepsis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Condition;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Observation;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.Patient;
import edu.unl.cse.soft160.a1.pediatric_sepsis.rest_backend.PatientRecords;
import edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm.PediatricSepsisAlgorithm;
import edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm.Sepsis_Determination;

public class OpenMRS_UI {
	private static String server;
	private static String username;
	private static String password;
	private static ArrayList<String> patientIDs = new ArrayList<String>();
	static Scanner scanner = new Scanner(System.in);
	static PatientRecords patientRecords;
	static boolean noErrors = true;

	private static void getPatientReports() throws IOException {
		Patient patient = null;
		for (int i = 0; i < patientIDs.size(); ++i) {
			try {
				patient = patientRecords.createPatient(patientIDs.get(i));
			} catch (Exception e) {
				System.out.println("Patient ID " + patientIDs.get(i) + " is malformed");
				System.out.println();
			}
			if (patient == null) {
				System.out.println("No match.");
				System.out.println("Patient " + patientIDs.get(i) + " does not exist");
				System.out.println();
			} else {
				Sepsis_Determination determination = getPatientAnalysis(patient, i);
				if (!determination.getUnknownJustifications().isEmpty()) {
					System.out.println(
							"Would you like to add the missing observations? \nEnter Y for yes or enter any other character for no.");
					if (scanner.next().equals("Y")) {
						for (String justification : determination.getUnknownJustifications()) {
							if (justification.contains("age")) {
								System.out.println("Enter year of birth");
								int yearOfBirth = scanner.nextInt();
								System.out.println("Enter month of birth(04 instead of April)");
								int monthOfBirth = scanner.nextInt();
								System.out.println("Enter day of birth");
								int dayOfBirth = scanner.nextInt();
								LocalDate birthday = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
								patient.setBirthDate(LocalDateTime.of(birthday, LocalTime.of(0, 0)));
							}
							if (justification.contains("white blood cell")) {
								System.out.println("Enter white blood cell count");
								patient.getVitals().addWhiteBloodCellCountObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
								System.out.println(patient.getVitals().getWhiteBloodCellCounts().get(0).getRecord());
							}
							if (justification.contains("body temperature")) {
								System.out.println("Enter patients body temperature");
								patient.getVitals().addBodyTemperatureObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
							}
							if (justification.contains("hematologic")) {
								System.out.println("Hematologic dysfunction? (type UNKNOWN, PRESENT, or NOT_PRESENT)");
								String temp = scanner.next();
								Condition condition;
								if (temp.toUpperCase().contains("UNKNOWN")) {
									condition = Condition.UNKNOWN;
									patient.getVitals().setHematologicDysfunction(condition);
								} else if (temp.toUpperCase().contains("PRESENT")) {
									condition = Condition.PRESENT;
									patient.getVitals().setHematologicDysfunction(condition);
								} else if (temp.toUpperCase().contains("NOT_PRESENT")) {
									condition = Condition.NOT_PRESENT;
									patient.getVitals().setHematologicDysfunction(condition);
								} else {
									System.out.println("Not a valid option, setting to UNKOWN");
								}
							}
							if (justification.contains("respiratory")) {
								System.out.println("Respiratory dysfunction? (type UNKNOWN, PRESENT, or NOT_PRESENT)");
								String temp = scanner.next();
								Condition condition;
								if (temp.toUpperCase().contains("UNKNOWN")) {
									condition = Condition.UNKNOWN;
									patient.getVitals().setRespiratoryDysfunction(condition);
								} else if (temp.toUpperCase().contains("PRESENT")) {
									condition = Condition.PRESENT;
									patient.getVitals().setRespiratoryDysfunction(condition);
								} else if (temp.toUpperCase().contains("NOT_PRESENT")) {
									condition = Condition.NOT_PRESENT;
									patient.getVitals().setRespiratoryDysfunction(condition);
								} else {
									System.out.println("Not a valid option, setting to UNKOWN");
								}
							}
							if (justification.contains("neurologic")) {
								System.out.println("Neurologic dysfunction? (type UNKNOWN, PRESENT, or NOT_PRESENT)");
								String temp = scanner.next();
								Condition condition;
								if (temp.toUpperCase().contains("UNKNOWN")) {
									condition = Condition.UNKNOWN;
									patient.getVitals().setNeurologicDysfuntion(condition);
								} else if (temp.toUpperCase().contains("PRESENT")) {
									condition = Condition.PRESENT;
									patient.getVitals().setNeurologicDysfuntion(condition);
								} else if (temp.toUpperCase().contains(("NOT_PRESENT"))) {
									condition = Condition.NOT_PRESENT;
									patient.getVitals().setNeurologicDysfuntion(condition);
								} else {
									System.out.println("Not a valid option, setting to UNKOWN");
								}
							}
							if (justification.contains("hepatic")) {
								System.out.println("Need bilirubin and transaminase values");
								System.out.println("Enter bilirubin count");
								patient.getVitals().addBilirubinCountObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
								System.out.println();
								System.out.println("Enter transaminase value");
								patient.getVitals().addAlanineTransaminaseObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
							}
							if (justification.contains("renal")) {
								System.out.println("Need creatinine level to check for renal dysfunciton");
								patient.getVitals().addCreatinineLevelObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
							}
							if (justification.contains("respiration rate")) {
								System.out.println("Enter respiration rate");
								patient.getVitals().addRespirationRateObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
							}
							if (justification.contains("heart rate")) {
								System.out.println("Enter heart rate");
								patient.getVitals().addHeartRateObservation(
										new Observation(LocalDateTime.now(), scanner.nextDouble()));
							}
						}
					} else {
						System.out.println("Exiting Application");
					}
					getPatientAnalysis(patient, i);
				}
			}
		}
	}

	private static Sepsis_Determination getPatientAnalysis(Patient patient, int i) {
		Sepsis_Determination determination = PediatricSepsisAlgorithm.analyze(patient);
		System.out.println("  Pediatric SEPSIS determination: " + determination.getDetermination());
		System.out.println("  Patient ID " + patientIDs.get(i));
		System.out.println("  Name: " + patient.getFamilyName().substring(0, 1).toUpperCase()
				+ patient.getFamilyName().substring(1) + ", "
				+ patient.getFirstName().substring(0, 1).toUpperCase() + patient.getFirstName().substring(1));
		System.out.println("  Report Generated on " + LocalDate.now() + " at " + LocalTime.now());
		System.out.println("  Birth Date: " + patient.getBirthDate());
		System.out.println("  Location: " + patient.getLocation());
		System.out.println("  Pediatric SEPSIS determination: " + determination.getDetermination());
		System.out.println("***************************************** \nCritical Lab Observations");
		if (determination.getLabJustifications().isEmpty()) {
			System.out.println("NONE");
		}
		for (String justification : determination.getLabJustifications()) {
			System.out.println(justification);
		}
		System.out.println();
		System.out.println("***************************************** \nCritical Vital Observations");
		if (determination.getVitalsJustifications().isEmpty()) {
			System.out.println("NONE");
		}
		for (String justification : determination.getVitalsJustifications()) {
			System.out.println(justification);
		}
		System.out.println();
		System.out.println("***************************************** \nMissing Observations");
		if (determination.getUnknownJustifications().isEmpty()) {
			System.out.println("NONE");
		}
		for (String justification : determination.getUnknownJustifications()) {
			System.out.println(justification);
		}
		System.out.println();
		System.out.println();
		return determination;
	}

	public static void checkInputs(String server, String username, String password) {
		if (server == null || username == null || password == null) {
			System.out.println("No Input");
			requestLogin(scanner);
		}
	}

	public static void requestLogin(Scanner scanner) {
		System.out.println("Enter Server ID");
		server = scanner.next();
		System.out.println("Enter User ID");
		username = scanner.next();
		System.out.println("Enter Password");
		password = scanner.next();
	}

	public static PatientRecords makeConnection(String server, String username, String password) throws IOException {
		try {
			patientRecords = new PatientRecords(username, password, server);
			patientRecords.setConnection();
		} catch (ProtocolException e) {
			System.out.println("Invalid Username or Password");
			noErrors = false;
			return null;
		} catch (UnknownHostException e) {
			System.out.println("Invalid server location");
			noErrors = false;
			return null;
		} catch (ConnectException e) {
			System.out.println("Could not connect with server");
			noErrors = false;
			return null;
		} catch (FileNotFoundException e) {
			System.out.println("Program could not contact server");
			noErrors = false;
			return null;
		}
		return patientRecords;
	}

	public static void addPatientIDs(Scanner scanner) throws IOException {
		boolean exit = false;
		while (!exit) {
			patientIDs.clear();
			System.out.print("Enter Patient ID or Type E to Exit: ");
			String nextLine = scanner.next();
			if (nextLine.equals("E")) {
				System.out.print("Exiting application...");
				exit = true;

			} else {
				patientIDs.add(nextLine);
				if (patientIDs.isEmpty()) {
					System.out.println("No patients entered");
				} else {
					
					System.out.println("Pulling Reports");
					System.out.println("*************************************************");
					getPatientReports();
					
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		requestLogin(scanner);
		checkInputs(server, username, password);
		patientRecords = makeConnection(server, username, password);
		if (noErrors == true) {
			addPatientIDs(scanner);
			scanner.close();
		}
	}
}
