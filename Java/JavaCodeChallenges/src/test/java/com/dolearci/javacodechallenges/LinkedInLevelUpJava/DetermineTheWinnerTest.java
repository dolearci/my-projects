package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class DetermineTheWinnerTest {

	private static final DetermineTheWinner.Team team1 = new DetermineTheWinner.Team("Sally", "Roger");
	private static final DetermineTheWinner.Team team2 = new DetermineTheWinner.Team("Eric", "Rebecca");
	private static final DetermineTheWinner.Team team3 = new DetermineTheWinner.Team("Tony", "Shannon");

	private final ByteArrayOutputStream printOut = new ByteArrayOutputStream();

	@BeforeEach
	public void setUpEach() {
		System.setOut(new PrintStream(printOut));
	}

	@AfterEach
	public void cleanUpEach() {
		System.setOut(System.out);
		team1.getScores().clear();
		team2.getScores().clear();
		team3.getScores().clear();
	}

	@Test
	public void generateTeamsScores() {
		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		int numberOfRounds = 4;

		DetermineTheWinner.TeamUtils.generateTeamsScores(teams, numberOfRounds);

		teams.forEach(team -> {
			List<Integer> scores = team.getScores();
			assertEquals(numberOfRounds, scores.size());
			scores.forEach(score -> assertTrue(score >= 0));
		});
	}

	@Test
	public void generateTeamsScores_emptyTeamsInput() {

		List<DetermineTheWinner.Team> teams = List.of();
		DetermineTheWinner.TeamUtils.generateTeamsScores(teams, 3);

		assertEquals(0, teams.size());
	}

	@Test
	public void generateTeamsScores_noRounds() {

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		int numberOfRounds = 0;

		DetermineTheWinner.TeamUtils.generateTeamsScores(teams, numberOfRounds);

		teams.forEach(team -> assertEquals(numberOfRounds, team.getScores().size()));
	}

	@Test
	public void revealResults() {
		team1.getScores().addAll(List.of(2, 4, 5));
		team2.getScores().addAll(List.of(8, 3, 4));
		team3.getScores().addAll(List.of(9, 2, 1));

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("""
				Now for the results, the WINNER is...
				With 15 points, it's team Eric and Rebecca!

				Then we have...\s
				With 12 points, it's team Tony and Shannon!

				Then we have...\s
				With 11 points, it's team Sally and Roger!

				""", printOut.toString());
	}

	@Test
	public void revealResults_unevenRounds() {
		team1.getScores().addAll(List.of(2, 5));
		team2.getScores().addAll(List.of(8, 3, 4));
		team3.getScores().add(9);

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("""
				Now for the results, the WINNER is...
				With 15 points, it's team Eric and Rebecca!

				Then we have...\s
				With 9 points, it's team Tony and Shannon!

				Then we have...\s
				With 7 points, it's team Sally and Roger!

				""", printOut.toString());
	}

	@Test
	public void revealResults_noScores() {

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("The game hasn't started yet.\n", printOut.toString());
	}

	@Test
	public void revealResults_noTeams() {

		List<DetermineTheWinner.Team> teams = List.of();
		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("The game hasn't started yet.\n", printOut.toString());
	}

	@Test
	public void revealResults_tiedWinner() {

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		team1.getScores().addAll(List.of(3, 3, 3, 3));
		team2.getScores().addAll(List.of(4, 2, 2, 4));
		team3.getScores().addAll(List.of(5, 0, 3, 2));

		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("""
				Now for the results, the WINNER is...
				It's a TIE!
				With 12 points, it's team Sally and Roger!
				With 12 points, it's team Eric and Rebecca!

				Then we have...\s
				With 10 points, it's team Tony and Shannon!

				""", printOut.toString());
	}

	@Test
	public void revealResults_tiedNonWinner() {

		List<DetermineTheWinner.Team> teams = List.of(team1, team2, team3);
		team1.getScores().addAll(List.of(3, 4, 2, 5));
		team2.getScores().addAll(List.of(1, 4, 2, 3));
		team3.getScores().addAll(List.of(5, 0, 3, 2));

		DetermineTheWinner.TeamUtils.revealResults(teams);

		assertEquals("""
				Now for the results, the WINNER is...
				With 14 points, it's team Sally and Roger!

				Then we have...\s
				It's a TIE!
				With 10 points, it's team Eric and Rebecca!
				With 10 points, it's team Tony and Shannon!

				""", printOut.toString());
	}
}

