package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String POP_QUESTION = "Pop Question ";
    private static final String SCIENCE_QUESTION = "Science Question ";
    private static final String SPORTS_QUESTION = "Sports Question ";
    private static final String ROCK_QUESTION = "Rock Question ";
    private static final String POP = "Pop";

    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    private static final String ANSWER_WAS_CORRECT = "Answer was correct!!!!";
    private static final String ANSWER_WAS_CORRENT = "Answer was corrent!!!!";
    private static final String PLAYERS_NEW_LOCATION_IS = "%s's new location is %d%n";
    private static final String PLAYER_IS_GETTING_OUT_OF_THE_PENALTY_BOX = "%s is getting out of the penalty box%n";
    private static final String PLAYER_IS_NOT_GETTING_OUT_OF_THE_PENALTY_BOX = "%s is not getting out of the penalty box%n";
    private static final String PLAYER_IS_THE_CURRENT_PLAYER = "%s is the current player%n";
    private static final String PLAYER_NOW_HAS_N_GOLD_COINS = "%s now has %d Gold Coins.%n";
    private static final String PLAYER_WAS_ADDED = "%s was added%n";
    private static final String PLAYER_WAS_SENT_TO_THE_PENALTY_BOX = "%s was sent to the penalty box%n";
    private static final String QUESTION_WAS_INCORRECTLY_ANSWERED = "Question was incorrectly answered";
    private static final String THEY_ARE_PLAYER_NUMBER = "They are player number %d%n";
    private static final String THEY_HAVE_ROLLED_A = "They have rolled a %d%n";
    private static final String THE_CATEGORY_IS = "The category is %s%n";

    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(POP_QUESTION + i);
            scienceQuestions.addLast((SCIENCE_QUESTION + i));
            sportsQuestions.addLast((SPORTS_QUESTION + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return ROCK_QUESTION + index;
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.printf(PLAYER_WAS_ADDED, playerName);
        System.out.printf(THEY_ARE_PLAYER_NUMBER, players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.printf(PLAYER_IS_THE_CURRENT_PLAYER, players.get(currentPlayer));
        System.out.printf(THEY_HAVE_ROLLED_A, roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.printf(PLAYER_IS_GETTING_OUT_OF_THE_PENALTY_BOX, players.get(currentPlayer));
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.printf(PLAYERS_NEW_LOCATION_IS, players.get(currentPlayer), places[currentPlayer]);
                System.out.printf(THE_CATEGORY_IS, currentCategory());
                askQuestion();
            } else {
                System.out.printf(PLAYER_IS_NOT_GETTING_OUT_OF_THE_PENALTY_BOX, players.get(currentPlayer));
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.printf(PLAYERS_NEW_LOCATION_IS, players.get(currentPlayer), places[currentPlayer]);
            System.out.printf(THE_CATEGORY_IS, currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == POP)
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return POP;
        if (places[currentPlayer] == 4) return POP;
        if (places[currentPlayer] == 8) return POP;
        if (places[currentPlayer] == 1) return SCIENCE;
        if (places[currentPlayer] == 5) return SCIENCE;
        if (places[currentPlayer] == 9) return SCIENCE;
        if (places[currentPlayer] == 2) return SPORTS;
        if (places[currentPlayer] == 6) return SPORTS;
        if (places[currentPlayer] == 10) return SPORTS;
        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println(ANSWER_WAS_CORRECT);
                purses[currentPlayer]++;
                System.out.printf(PLAYER_NOW_HAS_N_GOLD_COINS, players.get(currentPlayer), purses[currentPlayer]);

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println(ANSWER_WAS_CORRENT);
            purses[currentPlayer]++;
            System.out.printf(PLAYER_NOW_HAS_N_GOLD_COINS, players.get(currentPlayer), purses[currentPlayer]);

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println(QUESTION_WAS_INCORRECTLY_ANSWERED);
        System.out.printf(PLAYER_WAS_SENT_TO_THE_PENALTY_BOX, players.get(currentPlayer));
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}