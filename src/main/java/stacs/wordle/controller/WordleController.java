package stacs.wordle.controller;

import stacs.wordle.model.WordleGame;
import stacs.wordle.view.WordleCLI;

import java.util.ArrayList;

public class WordleController {
    private final WordleGame game;

    public WordleController(WordleGame game, WordleCLI cli) {
        this.game = game;
        cli.setController(this);
    }

    public Character[][] displayGameBoard() {
        return game.getGameBoard();
    }
    public void insertWordToGameBoard(String word) throws IllegalArgumentException {
        game.insertWordToGameBoard(word);
    }
    public boolean hasWonGame() {
        return game.getHasWon();
    }
    public boolean hasLostGame() {
        return game.getHasLost();
    }
    public String getChosenWord() {
        return game.getChosenWord();
    }

    public ArrayList<Integer> getMatchedLettersIndices() {
        return game.getGreenLettersIndices();
    }

    public ArrayList<Integer> getMisplacedLettersIndices() {
        return game.getYellowLettersIndices();
    }

    public ArrayList<Integer> getUnfoundLettersIndices() {
        return game.getRedLettersIndices();
    }

    public void computeLetterPositioningIndicesByRow(int rowNumber) {
        game.computeLetterPositioningIndicesByRow(rowNumber);
    }
}
