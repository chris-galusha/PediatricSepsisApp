package edu.unl.cse.soft160.a1.pediatric_sepsis.sepsis_algorithm;

import java.util.ArrayList;
import java.util.List;

import edu.unl.cse.soft160.a1.pediatric_sepsis.enumerated_types.Determination;

public class Sepsis_Determination {
	private Determination determination;
	private List<String> labJustifications;
	private List<String> unknownJustifications;
	private List<String> vitalsJustifications;

	public Sepsis_Determination() {
		this.labJustifications = new ArrayList<String>();
		this.unknownJustifications = new ArrayList<String>();
		this.vitalsJustifications = new ArrayList<String>();
	}

	public Determination getDetermination() {
		return determination;
	}

	public void setDetermination(Determination determination) {
		this.determination = determination;
	}

	public List<String> getLabJustifications() {
		return labJustifications;
	}

	public List<String> getUnknownJustifications() {
		return unknownJustifications;
	}

	public List<String> getVitalsJustifications() {
		return vitalsJustifications;
	}

	public void addUnknownJustification(String justification) {
		this.unknownJustifications.add(justification);
	}

	public void addLabJustification(String justification) {
		this.labJustifications.add(justification);
	}

	public void addVitalsJustification(String justification) {
		this.vitalsJustifications.add(justification);
	}

	public void setLabJustifications(List<String> labJustifications) {
		this.labJustifications = labJustifications;
	}

	public void setUnknownJustifications(List<String> unknownJustifications) {
		this.unknownJustifications = unknownJustifications;
	}

	public void setVitalsJustifications(List<String> vitalsJustifications) {
		this.vitalsJustifications = vitalsJustifications;
	}
	
	

}
