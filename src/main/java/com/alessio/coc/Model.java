package com.alessio.coc;

import com.alessio.coc.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

	private final ClashOfClansAPI api;
	private Clan clanInfo = null;
	private ArrayList<War> wars = null;

	/**
	 * Initializes the class' {@code ClashOfClansAPI} object
	 * with the {@code api} parameter passed as argument.
	 *
	 * @param api The {@code ClashOfClansAPI} object.
	 */
	public Model(ClashOfClansAPI api) {
		this.api = api;

		this.clanInfo = File.readClanInfoFromFile();
		this.wars = File.readWarsInfoFromFile();
		System.out.println("File data extraction succeeded!");
		/*
		System.out.println("Extracting clan data from server...");
		this.clanInfo = fetchAndSaveClanMembersInfo();
		System.out.println("Extracting war data from server...");
		this.wars = fetchAndSaveWarInfo();
		System.out.println("Fetch completed!");
		 */
	}

	/**
	 * Updates information about clan members, checks if there are members
	 * who are not in the clan anymore from the last update
	 * and saves the past wars' performance of these members in a file.
	 * It then saves the information about clan members in another file.
	 *
	 * @return A {@code Clan} object containing information about the clan members.
	 */
	private Clan fetchAndSaveClanMembersInfo() {
		ClashOfClansAPI coc = this.api;
		coc.updateClanInfo();
		ArrayList<Member> membersWhoLeft = membersWhoLeft(coc.getClanInfo().getMembers());
		if (membersWhoLeft != null) {
			ArrayList<War> performanceOfMembersWhoLeft = getWarPerformanceOfTheseMembers(membersWhoLeft);
			File.addMembersWhoLeftToFile(performanceOfMembersWhoLeft);
		}
		File.saveClanInfoToFile(coc.getClanInfo());
		return coc.getClanInfo();
	}

	/**
	 * Updates information about the ongoing war.
	 * Makes sure that the war object received contains a valid war (that is if the clan is
	 * either in preparation, in war, or the war has just ended).
	 * Checks if there are new members in the current war that are not present in che clan
	 * members information. If so it proceeds to {@code fetchAndSaveClanMembersInfo()}.
	 * It finally adds the current war to the wars already saved in the file.
	 *
	 * @return An {@code ArrayList<War>} object containing information about
	 *         the ongoing war.
	 */
	private ArrayList<War> fetchAndSaveWarInfo() {
		ClashOfClansAPI coc = this.api;
		ArrayList<War> wars = File.readWarsInfoFromFile();
		coc.updateWarInfo();

		String warState = coc.getWarInfo().getWarState();
		if (!WarStates.validWarStates.contains(warState)) {
			// Skips all the rest since the war received is not valid.
			System.out.println("Clan is not currently in war and didn't do any war recently.");
			return wars;
		}

		if (areThereNewMembers(coc.getWarInfo().getMembers())) {
			fetchAndSaveClanMembersInfo();
		}
		incorporateNewWar(wars, coc.getWarInfo());
		File.saveWarsInfoToFile(wars);
		return wars;
	}

	/**
	 * Checks whether the single war is already present in the list of wars.
	 * If it is present, the single war data is overwritten, otherwise it is added
	 * at the end of the {@code savedWars} list.
	 *
	 * @param savedWars The {@code ArrayList<War>} object containing a list of war.
	 * @param war The single {@code War} object to search for in the {@code ArrayList<War>}
	 */
	private void incorporateNewWar(ArrayList<War> savedWars, War war) {
		boolean noWarMatched = true;

		for (int i = 0; i < savedWars.size(); i++) {
			if (savedWars.get(i).getPreparationStartTime().equals(war.getPreparationStartTime())) {
				savedWars.set(i, war);
				noWarMatched = false;
			}
		}
		if (noWarMatched) {
			savedWars.add(war);
		}
	}

	/**
	 * Looks for the war performances, in all wars contained in the file,
	 * for each member passed as argument.
	 *
	 * @param membersWhoLeft The {@code ArrayList<Member>} object containing
	 *                       the list of members to return information of.
	 *
	 * @return An {@code ArrayList<War>} object containing war performance
	 *         information about all the members contained in the parameter.
	 */
	private ArrayList<War> getWarPerformanceOfTheseMembers(ArrayList<Member> membersWhoLeft) {
		ArrayList<War> performanceInfo = new ArrayList<>();
		ArrayList<War> wars = File.readWarsInfoFromFile();

		for (Member member: membersWhoLeft) {
			for (War war: wars) {
				for (WarMember warMember: war.getMembers()) {
					if (member.getTag().equals(warMember.getTag())) {
						performanceInfo.add(new War(
								war.getPreparationStartTime(),
								new ArrayList<>(List.of(new WarMember(warMember.getName(), warMember.getTag(), warMember.getWarPosition(), warMember.getAttacks())))));
					}
				}
			}
		}
		return performanceInfo;
	}

	/**
	 * Compares the clan members information saved in the file with the list
	 * of members passed as argument to check if there are members that
	 * are not in the clan anymore.
	 *
	 * @param newMembersInfo The {@code ArrayList<Member>} object containing
	 *                       the list of members to compare with the file.
	 *
	 * @return An {@code ArrayList<Member>} object containing the list of members
	 *         that are not in the clan anymore.
	 */
	private ArrayList<Member> membersWhoLeft(ArrayList<Member> newMembersInfo) {
		ArrayList<Member> membersWhoLeft = new ArrayList<>();
		HashSet<String> newMembersTags = new HashSet<>();
		HashSet<String> oldMembersTags = new HashSet<>();
		ArrayList<Member> oldMembersInfo = File.readClanInfoFromFile().getMembers();

		// Fill HashSets
		for (Member newMember: newMembersInfo) {
			newMembersTags.add(newMember.getTag());
		}
		for (Member oldMember: oldMembersInfo) {
			oldMembersTags.add(oldMember.getTag());
		}

		if (oldMembersTags.equals(newMembersTags)) {
			return null;
		} else {
			oldMembersTags.removeAll(newMembersTags);		// oldMembersTags now only contains members who are not in the clan anymore
			System.out.println("Members who left have been detected and will be saved in a file");
			for (Member oldMember: oldMembersInfo) {
				if (oldMembersTags.contains(oldMember.getTag())) {
					membersWhoLeft.add(oldMember);
				}
			}
			return membersWhoLeft;
		}
	}

	/**
	 * Compares the clan members information saved in the file with the list
	 * of members passed as argument to check if there are new members in the clan.
	 *
	 * @param newMembersInfo The {@code ArrayList<Member>} object containing
	 *                       the list of members to compare with the file.
	 *
	 * @return A {@code boolean} variable returning {@code true} if the
	 *         {@code ArrayList<WarMember>} object passed as argument
	 *         contains new members compared to the list of members in
	 *         the clan members information file.
	 */
	private boolean areThereNewMembers(ArrayList<WarMember> newMembersInfo) {
		ArrayList<Member> oldMembersInfo = File.readClanInfoFromFile().getMembers();
		HashSet<String> newMembersTags = new HashSet<>();
		HashSet<String> oldMembersTags = new HashSet<>();

		// Fill HashSets
		for (WarMember newMember: newMembersInfo) {
			newMembersTags.add(newMember.getTag());
		}
		for (Member oldMember: oldMembersInfo) {
			oldMembersTags.add(oldMember.getTag());
		}

		return !newMembersTags.equals(oldMembersTags);
	}

	/**
	 * Searches for clan members using their names.
	 *
	 * @param substring The {@code String} object containing the text input
	 *                  from the user.
	 *
	 * @return An {@code ArrayList<Member>} object containing
	 * the list of the members whose name matches the {@code String} received as parameter.
	 */
	public ArrayList<Member> searchMembersByName(String substring) {
		if (clanInfo == null) {
			return null;
		}
		ArrayList<Member> matchingMembers = clanInfo.getMembers().stream()
				.filter(member -> member.getName().contains(substring))	// Filters based on the condition
				.collect(Collectors.toCollection(ArrayList::new));		// Makes a list of the members

		return matchingMembers;
	}

	/**
	 * Searches for clan members using their names.
	 *
	 * @param substring The {@code String} object containing the text input
	 *                  from the user.
	 *
	 * @return An {@code ArrayList<String>} object containing only names of members
	 * that match the {@code String} received as parameter.
	 */
	public ArrayList<String> searchNamesOnly(String substring) {
		if (clanInfo == null) {
			return null;
		}
		return clanInfo.getMembers().stream()
				.map(Member::getName)									// Gets the name for each member
				.filter(memberName -> memberName.contains(substring))	// Filters based on the condition
				.collect(Collectors.toCollection(ArrayList::new));		// Makes a list for the names
	}

	/**
	 * Prints the {@code Clan} object passed as argument
	 * in an easily readable format.
	 *
	 * @param clanInfo The {@code Clan} object containing
	 *                 information about the clan members.
	 */
	public void printClanInfo(Clan clanInfo) {
		for (Member row: clanInfo.getMembers()) {
			System.out.printf(row.toString() + "\n");
		}
	}

	/**
	 * Prints the {@code ArrayList<War>} object passed as argument
	 * in an easily readable format.
	 *
	 * @param warsInfo The {@code ArrayList<Member>} object containing
	 *                 information about all saved wars.
	 */
	public void printWarsInfo(ArrayList<War> warsInfo) {
		for (War warInfo: warsInfo) {
			System.out.println(warInfo.toString());
			System.out.println("##### ATTACCHI #####");
			for (WarMember warMember: warInfo.getMembers()) {
				System.out.printf(warMember.toString() + " ");
				if (warMember.getAttacks() != null) {
					for (Attack attack: warMember.getAttacks()) {
						System.out.printf(attack.toString());
					}
				}
				System.out.println();
			}
		}
	}

	public Clan getClanInfo() {
		return clanInfo;
	}

	public ArrayList<War> getWars() {
		return wars;
	}
}
