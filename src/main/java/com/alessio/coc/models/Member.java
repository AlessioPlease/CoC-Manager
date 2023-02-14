package com.alessio.coc.models;

import java.io.Serializable;

public class Member implements Serializable {

	protected String name;
	protected Integer th;
	protected Integer exp;
	protected Integer trophies;
	protected Integer attacksWon;
	protected Integer donated;
	protected Integer received;
	protected String warPreference;
	protected Integer totalWarStars;
	protected Integer clanRank;
	protected String role;
	protected String tag;

	public Member(String name, Integer th, Integer exp, Integer trophies, Integer attacksWon, Integer donated, Integer received, String warPreference, Integer totalWarStars, Integer clanRank, String role, String tag) {
		this.name = name;
		this.th = th;
		this.exp = exp;
		this.trophies = trophies;
		this.attacksWon = attacksWon;
		this.donated = donated;
		this.received = received;
		this.warPreference = warPreference;
		this.totalWarStars = totalWarStars;
		this.clanRank = clanRank;
		this.role = role;
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getTrophies() {
		return trophies;
	}

	public void setTrophies(Integer trophies) {
		this.trophies = trophies;
	}

	public Integer getAttacksWon() {
		return attacksWon;
	}

	public void setAttacksWon(Integer attacksWon) {
		this.attacksWon = attacksWon;
	}

	public Integer getDonated() {
		return donated;
	}

	public void setDonated(Integer donated) {
		this.donated = donated;
	}

	public Integer getReceived() {
		return received;
	}

	public void setReceived(Integer received) {
		this.received = received;
	}

	public String getWarPreference() {
		return warPreference;
	}

	public void setWarPreference(String warPreference) {
		this.warPreference = warPreference;
	}

	public Integer getTotalWarStars() {
		return totalWarStars;
	}

	public void setTotalWarStars(Integer totalWarStars) {
		this.totalWarStars = totalWarStars;
	}

	public Integer getClanRank() {
		return clanRank;
	}

	public void setClanRank(Integer clanRank) {
		this.clanRank = clanRank;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return String.format("%-16s %2d %3d %4d🏆 %4d⚔️ %5d↑ %5d↓ %-3s %4d☆ #%2d %-8s %s",
				name, th, exp, trophies, attacksWon, donated, received, warPreference, totalWarStars, clanRank, role, tag);
	}
}
