package stacs.wordle.model;

/**
 * This class encapsulates the scoring statistics of the games played in memory.
 */
public class Statistics {
    private int numberOfGamesWon;
    private int numberOfGamesPlayed;

    /**
     * This is the class constructor.
     */
    Statistics() {
        this.numberOfGamesPlayed = 0;
        this.numberOfGamesWon = 0;
    }

    /**
     * This method is for incrementing the game count each time a new game is started.
     */
    public void incrementGameCount() {
        this.numberOfGamesPlayed++;
    }

    /**
     * This method is for fetching the number of games played.
     * @return the number of games played.
     */
    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    /**
     * This method is for fetching the number of games won by a player.
     * @return the number of games won by a player.
     */
    public int getNumberOfGamesWon() {
        return numberOfGamesWon;
    }

    /**
     * This method is for incrementing the win count each time a game is won by the player.
     */
    public void incrementNumberOfGamesWon() {
        numberOfGamesWon++;
    }

    /**
     * This method is for computing and returning the win percentage of the games played.
     * @return win percentage of the games played.
     */
    public double getWinPercentage() {
        return ((double) getNumberOfGamesWon()/getNumberOfGamesPlayed())*100;
    }
}
