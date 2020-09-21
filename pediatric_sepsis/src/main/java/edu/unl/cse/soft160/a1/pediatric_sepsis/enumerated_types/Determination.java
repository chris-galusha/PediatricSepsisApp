package edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types;

public enum Determination {
	CONTINUE_MONITORING, // found no evidence of SIRS or sepsis
	SIRS_ALERT, // found evidence of SIRS but not sepsis
	SEPSIS_ALERT, // found evidence of sepsis
	SEVERE_SEPSIS_ALERT, // found evidence of sepsis and multiple or high-risk organ dysfunction
	NOT_APPLICABLE, // found that the patient is an adult, or the patient is deceased
	MISSING_OBSERVATION, // found too few data to make a determination
}
