package stacs.wordle.controller;

import stacs.wordle.model.WordleGame;
import stacs.wordle.view.WordleCLI;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class is the Controller for Wordle Game bridging across model and views.
 * Any number of views can access controller to access the game model.
 */
public class WordleController {
    private final WordleGame game;

    /**
     * This is the class constructor.
     * @param game is the game model, instance of WordleGame.
     * @param cli is the view instance.
     */
    public WordleController(WordleGame game, WordleCLI cli) {
        this.game = game;
        cli.setController(this);
    }

    /**
     * This method is for fetching the wordle game's board.
     * @return game board
     */
    public Character[][] displayGameBoard() {
        return game.getGameBoard();
    }

    /**
     * This method is for inserting player input to wordle game board grid.
     * @param word is the input provided by the player.
     * @throws IllegalArgumentException when the word is not found in the dictionary or is invalid.
     */
    public void insertWordToGameBoard(String word) throws IllegalArgumentException {
        game.insertWordToGameBoard(word);
    }

    /**
     * This method is for identifying if the player has won the game.
     * @return true or false.
     */
    public boolean hasWonGame() {
        return game.getHasWon();
    }
    /**
     * This method is for identifying if the player has lost the game.
     * @return true or false.
     */
    public boolean hasLostGame() {
        return game.getHasLost();
    }
    /**
     * This method is for fetching the chosen word from the game.
     * @return chosen original word.
     */
    public String getChosenWord() {
        return game.getChosenWord();
    }
    /**
     * This method is for fetching rightly guessed letters input in each turn of player's attempts.
     * @return a list of indices of rightly guessed letters.
     */
    public ArrayList<Integer> getMatchedLettersIndices() {
        return game.getGreenLettersIndices();
    }
    /**
     * This method is for fetching letters that are found in wrong positions of original word in each turn of player's attempt.
     * @return a list of indices of letters at wrong position in each attempt.
     */
    public ArrayList<Integer> getMisplacedLettersIndices() {
        return game.getYellowLettersIndices();
    }
    /**
     * This method is for fetching letters that are not found in original word for each turn of player's attempt.
     * @return a list of indices of letters not found in each attempt.
     */
    public ArrayList<Integer> getUnfoundLettersIndices() {
        return game.getRedLettersIndices();
    }
    /**
     * This method is for computing the potential color coding for all guessed words in the grid by row number.
     */
    public void computeLetterPositioningIndicesByRow(int rowNumber) {
        game.computeLetterPositioningIndicesByRow(rowNumber);
    }
    /**
     * This method is for fetching letters that are guessed correctly in the game.
     * @return a list of letters guessed correctly across all attempts.
     */
    public ArrayList<Character> getAllMatchedLetters() {
        return game.getAllRightlyGuessedLetters();
    }
    /**
     * This method is for fetching letters that are found in wrong positions across all of player's attempts.
     * @return a list of letters at wrong positions guessed across all attempts.
     */
    public ArrayList<Character> getAllMisplacedLetters() {
        return game.getAllMisplacedLetters();
    }
    /**
     * This method is for fetching all the letters used during the game.
     * @return a list of all letters used across all attempts of the game.
     */
    public ArrayList<Character> getAllOptedLetters() {
        return game.getAllOptedLetters();
    }

    /**
     * This method is for starting a new game.
     * @throws FileNotFoundException when the dictionary file is unable to be loaded correctly.
     */
    public void startNewGame() throws FileNotFoundException {
        game.startGame();
    }

    /**
     * This method is for fetching the number of games played stats.
     * @return the number of games played.
     */
    public int getNumberOfGamesPlayed() {
        return game.getStatistics().getNumberOfGamesPlayed();
    }

    /**
     * This method is for fetching the win percentage stats.
     * @return win percentage.
     */
    public double getWinPercentage() {
        return game.getStatistics().getWinPercentage();
    }
}
