package com.alessio.coc.models;

import java.io.Serializable;
import java.util.ArrayList;

public class War implements Serializable {

	private String warState;
	private String preparationStartTime;	// Used to tell wars apart
	private Integer numberOfParticipants;
	private Integer usedAttacks;
	private Integer ourStars;
	private Integer opponentStars;
	private Double destructionPercentage;
	private ArrayList<WarMember> members;

	public War(String warState, String preparationStartTime, Integer numberOfParticipants, Integer usedAttacks, Integer ourStars, Integer opponentStars, Double destructionPercentage, ArrayList<WarMember> members) {
		this.warState = warState;
		this.preparationStartTime = preparationStartTime;
		this.numberOfParticipants = numberOfParticipants;
		this.usedAttacks = usedAttacks;
		this.ourStars = ourStars;
		this.opponentStars = opponentStars;
		this.destructionPercentage = destructionPercentage;
		this.members = members;
	}

	public War (String warState) {
		this(
				warState,
				"",
				0,
				0,
				0,
				0,
				0d,
				new ArrayList<>());
	}

	public War (String preparationStartTime, ArrayList<WarMember> members) {
		this(
				"",
				preparationStartTime,
				0,
				0,
				0,
				0,
				0d,
				members);
	}

	public String getWarState() {
		return warState;
	}

	public void setWarState(String warState) {
		this.warState = warState;
	}

	public String getPreparationStartTime() {
		return preparationStartTime;
	}

	public void setPreparationStartTime(String preparationStartTime) {
		this.preparationStartTime = preparationStartTime;
	}

	public Integer getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Integer numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public Integer getUsedAttacks() {
		return usedAttacks;
	}

	public void setUsedAttacks(Integer usedAttacks) {
		this.usedAttacks = usedAttacks;
	}

	public Integer getOurStars() {
		return ourStars;
	}

	public void setOurStars(Integer ourStars) {
		this.ourStars = ourStars;
	}

	public Integer getOpponentStars() {
		return opponentStars;
	}

	public void setOpponentStars(Integer opponentStars) {
		this.opponentStars = opponentStars;
	}

	public Double getDestructionPercentage() {
		return destructionPercentage;
	}

	public void setDestructionPercentage(Double destructionPercentage) {
		this.destructionPercentage = destructionPercentage;
	}

	public ArrayList<WarMember> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<WarMember> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Start date: " + preparationStartTime + "\n" +
				"Number of participants: " + numberOfParticipants + "\n" +
				"Used attacks: " + usedAttacks + "\n" +
				"Our stars: " + ourStars + "\n" +
				"Opponent stars: " + opponentStars + "\n" +
				"Destruction percentage: " + destructionPercentage;
	}
}
