package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

@UseReporter(QuietReporter.class)
public class GameRunnerShould {

	private static final long FIXED_SEED = 1234;

	private Random random = new Random(FIXED_SEED);
	private Game game;
	private PrintStream previousOut;

	private String[] players = new String[]{"Chet", "Pat", "Sue"};

	@Test public void
	should_return_outcome_after_a_number_of_rolls_and_finishing_the_game() throws Exception {
		ByteArrayOutputStream byteStream = getCapturedOutputFromSystemOut();

		runGame();

		restoreSystemOut();
		Approvals.verify(byteStream.toString());
	}

	private void restoreSystemOut() {
		System.setOut(previousOut);
	}

	private ByteArrayOutputStream getCapturedOutputFromSystemOut() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		previousOut = System.out;
		System.setOut(printStream);
		return byteArrayOutputStream;
	}

	public void runGame() {
		game = new Game();
		for (String player : players) {
			game.add(player);
		}

		boolean notAWinner;
		do {
			game.roll(getDiceRoll());

			if (randomSizeCorrectAnswer()) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}

	private boolean randomSizeCorrectAnswer() {
		return random.nextInt(9) == 7;
	}

	public int getDiceRoll() {
		return random.nextInt(5) + 1;
	}
}
