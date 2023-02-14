package com.alessio.coc.models;

import java.util.ArrayList;

public class MemberListRow {

	private String name;
	private Integer th;
	private Integer exp;
	private Integer trophies;
	private Integer attacksWon;
	private Integer donated;
	private Integer received;

	private String warPreference;
	private Integer totalWarStars;
	private ArrayList<Attack> lastWarsPerformance;
	private String tag;

	public MemberListRow(String name, Integer th, Integer exp, Integer trophies, Integer attacksWon, Integer donated, Integer received, String warPreference, Integer totalWarStars, ArrayList<Attack> lastWarsPerformance, String tag) {
		this.name = name;
		this.th = th;
		this.exp = exp;
		this.trophies = trophies;
		this.attacksWon = attacksWon;
		this.donated = donated;
		this.received = received;
		this.warPreference = warPreference;
		this.totalWarStars = totalWarStars;
		this.lastWarsPerformance = lastWarsPerformance;
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public Integer getTh() {
		return th;
	}

	public Integer getExp() {
		return exp;
	}

	public Integer getTrophies() {
		return trophies;
	}

	public Integer getAttacksWon() {
		return attacksWon;
	}

	public Integer getDonated() {
		return donated;
	}

	public Integer getReceived() {
		return received;
	}

	public String getWarPreference() {
		return warPreference;
	}

	public Integer getTotalWarStars() {
		return totalWarStars;
	}

	public ArrayList<Attack> getAttacks() {
		return lastWarsPerformance;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-16s %2d %3d %4d🏆 %4d %5d %5d %-3s %4d ",
				name, th, exp, trophies, attacksWon, donated, received, warPreference, totalWarStars));

		if (lastWarsPerformance != null && lastWarsPerformance.size() > 0) {
			for (Attack attack: lastWarsPerformance) {
				sb.append(attack.toString2());
				sb.append(" ");
			}
		}
		sb.append(String.format("%s", tag));

		return sb.toString();
	}
}
