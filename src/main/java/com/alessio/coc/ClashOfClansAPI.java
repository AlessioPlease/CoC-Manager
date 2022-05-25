package com.alessio.coc;

import com.alessio.coc.models.Member;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

//		###################################	REMEMBER TO UPDATE THE TOKEN	###################################
//		https://developer.clashofclans.com/#/account

public class ClashOfClansAPI {
	private ArrayList<Member> clanMembersInfo;
	private Instant lastUpdate = Instant.parse("2000-01-01T10:15:30.00Z");


	public ClashOfClansAPI() {
		clanMembersInfo = getClanMembersInfo();
	}

	private String requestClanMembersInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + Constants.getClanTag() + "/members";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	public ArrayList<Member> extractClanMembersInfo(String response) {
		ArrayList<Member> membersInfo = new ArrayList<>();
		JSONObject json = new JSONObject(response);
		JSONArray items = json.getJSONArray("items");

		for (int i = 0; i < items.length(); i++) {
			HashMap<String, String> otherInfo = extractOtherInfo(requestOtherInfo(items.getJSONObject(i).getString("tag")));

			Member member = new Member(
					items.getJSONObject(i).getString("name"),
					items.getJSONObject(i).getInt("donations"),
					items.getJSONObject(i).getInt("donationsReceived"),
					Integer.valueOf(otherInfo.get("attackWins")),
					Integer.valueOf(otherInfo.get("townHallLevel")),
					otherInfo.get("warPreference"),
					items.getJSONObject(i).getString("role"),
					items.getJSONObject(i).getInt("expLevel"),
					items.getJSONObject(i).getInt("trophies"),
					Integer.valueOf(otherInfo.get("warStars")),
					items.getJSONObject(i).getInt("clanRank"),
					items.getJSONObject(i).getString("tag")
			);
			membersInfo.add(member);
		}
		return membersInfo;
	}

	private String requestOtherInfo(String dirtyTag) {
		String tag = dirtyTag.replace("#", "");
		String url = "https://api.clashofclans.com/v1/players/%23" + tag;
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	private HashMap<String, String> extractOtherInfo(String response) {
		HashMap<String, String> memberInfo = new HashMap<>();
		JSONObject json = new JSONObject(response);

		memberInfo.put("townHallLevel", Integer.toString(json.getInt("townHallLevel")));
		memberInfo.put("attackWins", Integer.toString(json.getInt("attackWins")));
		memberInfo.put("warPreference", json.getString("warPreference"));
		memberInfo.put("warStars", Integer.toString(json.getInt("warStars")));

		return memberInfo;
	}


	private String requestWarInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + Constants.getClanTag() + "/currentwar";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	private void extractWarInfo(String response) {

	}
	private HttpURLConnection buildRequest(String urlString, String method) {

		try {
			URL url = new URL(urlString);
			HttpURLConnection req = (HttpURLConnection) url.openConnection();
			req.setRequestMethod(method);
			req.setRequestProperty("Authorization", Constants.getAuthToken());
			return req;
		} catch (IOException e) {
			System.out.println("Something went wrong while building the request");
			e.printStackTrace();
			return null;
		}
	}

	private String getResponse(HttpURLConnection request) {

		StringBuilder content = new StringBuilder();

		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String inputLine;

			while ((inputLine = buffer.readLine()) != null) {
				content.append(inputLine);
			}
			buffer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong while reading the response");
			e.printStackTrace();
			return "";
		}
		return content.toString();
	}

	private boolean updateIsRecent() {
		Duration timeElapsed = Duration.between(this.lastUpdate, Instant.now());

		return timeElapsed.toMinutes() <= Constants.getUpdateInterval();
	}

	public ArrayList<Member> getClanMembersInfo() {

		if (! updateIsRecent()) {
			this.clanMembersInfo = extractClanMembersInfo(requestClanMembersInfo());
			lastUpdate = Instant.now();
		}
		return this.clanMembersInfo;
	}
}

