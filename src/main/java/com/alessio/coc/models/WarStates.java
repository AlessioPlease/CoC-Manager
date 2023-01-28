package com.alessio.coc.models;

import java.util.Arrays;
import java.util.HashSet;

public final class WarStates {

	public static final String preparation = "preparation";
	public static final String inWar = "inWar";
	public static final String warEnded = "warEnded";
	public static final HashSet<String> validWarStates = new HashSet<>(Arrays.asList(preparation, inWar, warEnded));

	public static final String notInWar = "notInWar";
	public static final String notFound = "notFound";
	public static final String accessDenied = "accessDenied";
	public static final HashSet<String> notValidWarStates = new HashSet<>(Arrays.asList(notInWar, notFound, accessDenied));


	private WarStates() {}
}