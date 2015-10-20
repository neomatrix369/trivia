package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    protected static final String POP_QUESTION = "Pop Question ";
    protected static final String SCIENCE_QUESTION = "Science Question ";
    protected static final String SPORTS_QUESTION = "Sports Question ";
    protected static final String ROCK_QUESTION = "Rock Question ";
    protected static final String POP = "Pop";
    protected static final String SCIENCE = "Science";
    protected static final String SPORTS = "Sports";
    protected static final String ROCK = "Rock";
    protected static final String ANSWER_WAS_CORRECT = "Answer was correct!!!!";
    protected static final String ANSWER_WAS_CORRENT = "Answer was corrent!!!!";
    protected static final String QUESTION_WAS_INCORRECTLY_ANSWERED = "Question was incorrectly answered";
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

        System.out.printf("%s was added%n", playerName);
        System.out.printf("They are player number %d%n", players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.printf("%s is the current player%n", players.get(currentPlayer));
        System.out.printf("They have rolled a %d%n", roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.printf("%s is getting out of the penalty box%n", players.get(currentPlayer));
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.printf("%s's new location is %d%n", players.get(currentPlayer), places[currentPlayer]);
                System.out.printf("The category is %s%n", currentCategory());
                askQuestion();
            } else {
                System.out.printf("%s is not getting out of the penalty box%n", players.get(currentPlayer));
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.printf("%s's new location is %d%n", players.get(currentPlayer), places[currentPlayer]);
            System.out.printf("The category is %s%n", currentCategory());
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
                System.out.printf("%s now has %d Gold Coins.%n", players.get(currentPlayer), purses[currentPlayer]);

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
            System.out.printf("%s now has %d Gold Coins.%n", players.get(currentPlayer), purses[currentPlayer]);

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println(QUESTION_WAS_INCORRECTLY_ANSWERED);
        System.out.printf("%s was sent to the penalty box%n", players.get(currentPlayer));
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}