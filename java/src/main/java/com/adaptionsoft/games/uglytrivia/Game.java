package com.adaptionsoft.games.uglytrivia;

public class Game {
    private static final int TOTAL_QUESTIONS_COUNT = 50;

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

    Players players = new Players();
    Places places = new Places();
    Purses purses = new Purses();
    InPenaltyBox inPenaltyBox = new InPenaltyBox();

    PopQuestions popQuestions = new PopQuestions();
    ScienceQuestions scienceQuestions = new ScienceQuestions();
    SportsQuestion sportsQuestions = new SportsQuestion();
    RockQuestions rockQuestions = new RockQuestions();

    int currentPlayer = 0;

    boolean isGettingOutOfPenaltyBox;

    public Game() {
        popQuestions.add(POP_QUESTION, TOTAL_QUESTIONS_COUNT);
        scienceQuestions.add(SCIENCE_QUESTION, TOTAL_QUESTIONS_COUNT);
        sportsQuestions.add(SPORTS_QUESTION, TOTAL_QUESTIONS_COUNT);
        rockQuestions.add(ROCK_QUESTION, TOTAL_QUESTIONS_COUNT);
    }

    public boolean add(Player player) {
        players.add(player);
        places.setPlayersPlaceToZero(players.count());
        purses.setPlayersPlaceToZero(players.count());
        inPenaltyBox.setPlayersPlaceTo(players.count(), false);

        System.out.printf(PLAYER_WAS_ADDED, player);
        System.out.printf(THEY_ARE_PLAYER_NUMBER, players.count());
        return true;
    }

    public void roll(int roll) {
        System.out.printf(PLAYER_IS_THE_CURRENT_PLAYER, getCurrentPlayer());
        System.out.printf(THEY_HAVE_ROLLED_A, roll);

        if (inPenaltyBox.get(currentPlayer)) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.printf(PLAYER_IS_GETTING_OUT_OF_THE_PENALTY_BOX, getCurrentPlayer());
                places.add(currentPlayer, roll);
                if (places.get(currentPlayer) > 11) places.subtract(currentPlayer, 12);

                System.out.printf(PLAYERS_NEW_LOCATION_IS, getCurrentPlayer(), places.get(currentPlayer));
                System.out.printf(THE_CATEGORY_IS, currentCategory());
                askQuestion();
            } else {
                System.out.printf(PLAYER_IS_NOT_GETTING_OUT_OF_THE_PENALTY_BOX, getCurrentPlayer());
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            places.add(currentPlayer, roll);
            if (places.get(currentPlayer) > 11) places.subtract(currentPlayer, 12);

            System.out.printf(PLAYERS_NEW_LOCATION_IS, getCurrentPlayer(), places.get(currentPlayer));
            System.out.printf(THE_CATEGORY_IS, currentCategory());
            askQuestion();
        }

    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayer);
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
        if (places.get(currentPlayer) == 0) return POP;
        if (places.get(currentPlayer) == 4) return POP;
        if (places.get(currentPlayer) == 8) return POP;
        if (places.get(currentPlayer) == 1) return SCIENCE;
        if (places.get(currentPlayer) == 5) return SCIENCE;
        if (places.get(currentPlayer) == 9) return SCIENCE;
        if (places.get(currentPlayer) == 2) return SPORTS;
        if (places.get(currentPlayer) == 6) return SPORTS;
        if (places.get(currentPlayer) == 10) return SPORTS;
        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox.get(currentPlayer)) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println(ANSWER_WAS_CORRECT);
                purses.increment(currentPlayer);
                System.out.printf(PLAYER_NOW_HAS_N_GOLD_COINS, getCurrentPlayer(), purses.get(currentPlayer));

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.count()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.count()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println(ANSWER_WAS_CORRENT);
            purses.increment(currentPlayer);
            System.out.printf(PLAYER_NOW_HAS_N_GOLD_COINS, getCurrentPlayer(), purses.get(currentPlayer));

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.count()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println(QUESTION_WAS_INCORRECTLY_ANSWERED);
        System.out.printf(PLAYER_WAS_SENT_TO_THE_PENALTY_BOX, getCurrentPlayer());
        inPenaltyBox.setPlayersPlaceTo(currentPlayer, true);

        currentPlayer++;
        if (currentPlayer == players.count()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return !(purses.get(currentPlayer) == 6);
    }
}