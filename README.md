# Sepsis Determination App

The pediatric sepsis detection app connects to OpenMRS and, given a patient ID,
automatically determines whether or not that patient has symptoms indicative of
pediatric sepsis using the HIMSS early-detection pediatric sepsis determination algorithm. Its reports
include evidence for the app's findings and the key information needed for
immediate treatment: the location of the patient and the next steps to take. In
the case of a sepsis determination that cannot be differentiated from
other conditions, these steps also include the labs that should be run to make
that distinction as soon as possible.

Project status: **feature-complete, fully tested, no test failures, and no known
bugs**

Authors:
*   Christopher Galusha <christgx5@gmail.com>
*   James Ogden <togden444@gmail.com>
*   Zachary Statema <zstate15@gmail.com>
*   A.J. Lindquist <anlindq@gmail.com>

## Dependencies

The sepsis detection app depends on
[the OpenMRS REST connector](https://git.unl.edu/bgarvin/openmrs-rest-connector).
The REST connector must be in the project's build path during development and
the project's classpath when the app is run.

To run, the REST connector in turn requires a running instance of OpenMRS either
on the same computer or accessible over a network.

For development, JUnit 4 is required to run the project's unit tests.

## Building

The app can be built and packaged as a JAR by running `mvn package` from the
`pediatric_sepsis` folder. The project can also be imported into Eclipse, in which
case Eclipse will build the `.class` files, but not a JAR.

## Running

For development, `PediatricSepsisDeterminationApp.java` can be run in Eclipse by
right-clicking on it in the "Package Explorer" and selecting "Run As" → "Java
Application".

Once packaged, the app can be run with the command

````
java -jar pediatri_sepsis.jar
````

where `pediatric_sepsis.jar` is the name of the JAR that was built (which may vary
depending on your Maven configuration.)

The pediatric sepsis determination app will prompt for the URI, port number, and
login credentials to the OpenMRS server. Enter this information as requested.

The app will then prompt the user for a patient ID. Once the patient ID has been
entered, the app will print a report for that patient. If the patient ID is left
blank, or the user types "E" when prompted for a patient ID, the app will exit. If the algoritm cannot reach a determination, additional information will be prompted for to reach a determination.

## Software Architecture

The pediatric sepsis detection app is organized into four tiers: a command line interface for user interaction&nbsp;(`OpenMRS_UI`), a connector to the OpenMRS backend&nbsp;(`rest_backend`), an implementation of the sepsis determination algorithm&nbsp;(`sepsis_algorithm`), and a collection of enumerated types used by the project&nbsp;(`enumerated_types`):

1.  The main class:

    *   `OpenMRS_UI.java`

	Handles major responsibilities, such as asking the user for patient and connection data, and formatting the final report.


2.  The `rest_backend` package mostly contains classes for representing OpenMRS types locally:

	*   `Patient.java`
	*   `Vitals.java`
	*   `Observation.java`

	An additional class handles getting data from the OpenMRS server connection and converting it to a local patient:

	*   `PatientRecords.java`

3.  The `sepsis_algorithm` package contains a single entry point:

    *   `PediatricSepsisAlgorithm.java`

	The pediatric sepsis determination algorithm itself is comletely implemented by this class. The helper class:

	*   `MostRecentVitals.java`

	Stores the most recent vitals measurements for convenient access by the algorithm, as past vitals are typically not of concern.

    The pediatric sepsis algorithm returns an object of type `Sepsis_Determination` defined in `Sepsis_Determination.java` which represents a determination (see `Determination.java`) and justifications for that determination. The justifications are divided into:

	*   Unknown justifications which indicate missing data
	
	*   Lab justifications indicating diagnoses for various dyfunctions

	*   Vitals justifications indicating abnormal individual vitals measurements.




4.  The `enumerated_types` package provides the enumerated types used throughout the project:

    *   `Condition.java`
    *   `Determination.java`
    *   `Gender.java`
	
	Condition is primarily used by the algorithm to represent information about various dysfunctions. 
	
	Determination is a field of the Sepsis_Determination class, and represents the determination of the sepsis algorithm.
	
	Gender simply represents the gender of a patient.

## Testing Strategy

The pediatric sepsis determination app has been verified with automated unit tests
of the HIMSS pediatric sepsis determination algorithm and manual system tests of the
app as a whole. Both types of tests were designed using the specifications provided to derive categories and partitions to support white box category-partition testing. The test suites were then augmented to achieve 100% code coverage. The
categories and partitions chosen are documented in a separate file called `test_design.md`, which is in the `documentation` folder.

Unit tests are available in the `test` folder:

*   `PediatricSepsisAlgorithmTest.java`
*   `createPatientTest.java`

	createPatientTest contains a few tests specifically for the `PatientRecords.java` file.

The unit tests require JUnit 4.

System tests are documented as Markdown in the `system_tests` folder. These tests include:

*   The steps to import an OpenMRS sql database
*   The steps to run the OpenMRS server
*   The steps to run the pediatric sepsis determination app
*   Basic usage of the determination app

The system tests do not require any special tools or software.

No UI testing, security testing, or load/stress testing was performed for this
milestone.

