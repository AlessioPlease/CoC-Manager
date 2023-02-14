package com.alessio.coc;

import com.alessio.coc.models.*;

import java.util.ArrayList;

public class Controller {

	private Model model;
	private View view;

	/**
	 * Initializes the class' {@code Model} and {@code View} objects.
	 *
	 * @param api The {@code ClashOfClansAPI} object.
	 */
	public Controller(ClashOfClansAPI api) {
		this.model = new Model(api);
		this.view = new View(this);
	}

	/**
	 * Calls the {@code Model}'s function {@code searchMembersByName}.
	 * This function will return the list of members with all their information.
	 *
	 * @param substring The {@code String} object containing the text input
	 *                  from the user.
	 *
	 * @return A {@code String} object formatted to be easily readable in the
	 * {@code JLabel} it will be showing in.
	 */
	public String searchMembersByName(String substring) {
		ArrayList<Member> membersFound = model.searchMembersByName(substring);
		ArrayList<War> lastWars = model.getWars();
		ArrayList<MemberRow> membersToReturn = new ArrayList<>();

		for (int i = 0; i < membersFound.size(); i++) {
			membersToReturn.add(new MemberRow(membersFound.get(i)));
			for (War war: lastWars) {
				for (WarMember warMember: war.getMembers()) {
					if (membersFound.get(i).getTag().equals(warMember.getTag()) && warMember.getAttacks() != null) {
						membersToReturn.get(i).getLastWarsPerformance().addAll(warMember.getAttacks());
					}
				}
			}
		}
		return formatMembersListForLabel(membersToReturn);
	}


	/**
	 * Calls the {@code Model}'s function {@code searchNamesOnly}.
	 * This function returns a list of names only.
	 *
	 * @param substring The {@code String} object containing the text input
	 *                  from the user.
	 *
	 * @return A {@code String} object formatted to be easily readable in the
	 * {@code JLabel} it will be showing in.
	 */
	public String searchNamesOnly(String substring) {
		return formatStringListForLabel(model.searchNamesOnly(substring));
	}

	/**
	 * Puts the content of the {@code ArrayList<MemberRow>} parameter in a formatted {@code String}.
	 *
	 * @param members The {@code ArrayList<MemberRow>} object containing a list of clan members.
	 *
	 * @return A {@code String} object formatted to be easily readable in the
	 * {@code JLabel} it will be showing in.
	 */
	private String formatMembersListForLabel(ArrayList<MemberRow> members) {
		StringBuilder stringBuilder = new StringBuilder();


		stringBuilder.append("<html>");
		members.forEach(member -> stringBuilder.append(member.toString()).append("<br>"));
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}

	/**
	 * Puts the content of the {@code ArrayList<String>} parameter in a formatted {@code String}.
	 *
	 * @param list The {@code ArrayList<String>} object containing a list of names.
	 *
	 * @return A {@code String} object formatted to be easily readable in the
	 * {@code JLabel} it will be showing in.
	 */
	private String formatStringListForLabel(ArrayList<String> list) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("<html>");
		list.forEach(string -> stringBuilder.append(string).append("<br>"));
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}
}
