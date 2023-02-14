package com.alessio.coc.models;

import java.util.ArrayList;

public class MemberRow extends Member {

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

	public MemberRow(Member member) {
		super(member.name,
				member.th,
				member.exp,
				member.trophies,
				member.attacksWon,
				member.donated,
				member.received,
				member.warPreference,
				member.totalWarStars,
				member.clanRank,
				member.role,
				member.tag);
		this.lastWarsPerformance = new ArrayList<>();
	}

	public ArrayList<Attack> getLastWarsPerformance() {
		return lastWarsPerformance;
	}

	public void setLastWarsPerformance(ArrayList<Attack> lastWarsPerformance) {
		this.lastWarsPerformance = lastWarsPerformance;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-16s %2d %3d %4d🏆 %4d⚔️ %5d↑ %5d↓ %-3s %4d ",
				name, th, exp, trophies, attacksWon, donated, received, warPreference, totalWarStars));

		if (lastWarsPerformance != null && lastWarsPerformance.size() > 0) {
			for (Attack attack: lastWarsPerformance) {
				sb.append(attack.toString2());
				sb.append(" ");
			}
		}
		sb.append(String.format("%s", tag));

		System.out.println(sb);
		return sb.toString();
	}
}
