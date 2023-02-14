package com.alessio.coc.models;

import java.util.ArrayList;

public class MemberListRow extends Member {

	/*
	name
	th
	exp
	trophies
	attacksWon
	donated
	received

	warPreference
	totalWarStars
	lastWarsPerformance
	tag
	 */
	private ArrayList<Attack> lastWarsPerformance;

	public MemberListRow(String name, Integer th, Integer exp, Integer trophies, Integer attacksWon, Integer donated, Integer received, String warPreference, Integer totalWarStars, Integer clanRank, String role, String tag, ArrayList<Attack> lastWarsPerformance) {
		super(name, th, exp, trophies, attacksWon, donated, received, warPreference, totalWarStars, clanRank, role, tag);
		this.lastWarsPerformance = lastWarsPerformance;
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
