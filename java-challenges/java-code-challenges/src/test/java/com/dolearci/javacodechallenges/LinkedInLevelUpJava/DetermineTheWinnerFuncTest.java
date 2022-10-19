package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.*;

import com.dolearci.javacodechallenges.levelupjava.DetermineTheWinner;
import org.junit.jupiter.api.Test;

import java.util.List;

class DetermineTheWinnerFuncTest {

	private static final String PLAYER_ONE = "Sally";
	private static final String PLAYER_TWO = "Roger";

	@Test
	public void teamAttributes() {

		DetermineTheWinner.Team team = new DetermineTheWinner.Team(PLAYER_ONE, PLAYER_TWO);

		assertEquals(PLAYER_ONE, team.getPlayer1());
		assertEquals(PLAYER_TWO, team.getPlayer2());
		assertEquals(List.of(), team.getScores());
	}

	@Test
	public void sumTotalScore() {

		DetermineTheWinner.Team team = new DetermineTheWinner.Team(PLAYER_ONE, PLAYER_TWO);
		team.getScores().addAll(List.of(2, 9, 9, 3, 11, 2));

		assertEquals(36, team.sumTotalScore());
	}

	@Test
	public void sumTotalScore_negativeInputs() {

		DetermineTheWinner.Team team = new DetermineTheWinner.Team(PLAYER_ONE, PLAYER_TWO);
		team.getScores().addAll(List.of(-2, -3, 5));

		assertEquals(5, team.sumTotalScore());
	}

	@Test
	public void sumTotalScore_emptyList() {

		DetermineTheWinner.Team team = new DetermineTheWinner.Team(PLAYER_ONE, PLAYER_TWO);
		assertEquals(0, team.sumTotalScore());
	}

}