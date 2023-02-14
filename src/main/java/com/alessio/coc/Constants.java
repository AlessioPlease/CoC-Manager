package com.alessio.coc;

public final class Constants {

	private static final String AUTHTOKEN = "Bearer " + File.readDataFromFile("Token.txt");
	private static final String CLANTAG = File.readDataFromFile("ClanTag.txt");
	private static final int autoUpdateInterval = 10;	// MINUTES
	private static final int manualUpdateInterval = 1;	// MINUTES

	private Constants() {
	}

	public static String getAuthToken() {
		return AUTHTOKEN;
	}

	public static String getClanTag() {
		return CLANTAG;
	}

	public static int getAutoUpdateInterval() {
		return autoUpdateInterval;
	}

	public static int getManualUpdateInterval() {
		return manualUpdateInterval;
	}
}