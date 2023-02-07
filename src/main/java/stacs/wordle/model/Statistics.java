package stacs.wordle.model;

public class Statistics {
    private int numberOfGamesWon;
    private int numberOfGamesPlayed;

    Statistics() {
        this.numberOfGamesPlayed = 0;
        this.numberOfGamesWon = 0;
    }
    public void incrementGameCount() {
        this.numberOfGamesPlayed++;
    }
    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public int getNumberOfGamesWon() {
        return numberOfGamesWon;
    }

    public int incrementNumberOfGamesWon() {
        return numberOfGamesWon++;
    }

    public double getWinPercentage() {
        return ((double) getNumberOfGamesWon()/getNumberOfGamesPlayed())*100;
    }
}
