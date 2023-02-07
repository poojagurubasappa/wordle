package stacs.wordle.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class WordleGame {
    private final int MAX_ROW = 6;
    private final int MAX_COL = 5;
    private Character[][] gameBoard;
    private final String wordListFilePath;
    private ArrayList<String> words;
    private String chosenWord;
    private final String DEFAULT_WORD = "debug";
    private int numberOfGuess;
    private boolean hasWon;
    private boolean hasLost;
    public WordleGame(String wordListFilePath) {
        this.words = new ArrayList<>();
        this.wordListFilePath = wordListFilePath;
    }

    public void startGame() throws FileNotFoundException{
        this.gameBoard = new Character[MAX_ROW][MAX_COL];
        this.loadWordlist();
        this.chosenWord = this.chooseARandomWord();
        this.numberOfGuess = 0;
        this.hasWon = false;
        this.hasLost = false;
    }

    public void loadWordlist() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new FileReader(wordListFilePath));
        while (scanner.hasNextLine()) {
            this.words.add(scanner.nextLine().toUpperCase());
        }
        scanner.close();
    }

    public int getMAX_ROW() {
        return MAX_ROW;
    }

    public int getMAX_COL() {
        return MAX_COL;
    }

    public Character[][] getGameBoard() {
        return gameBoard;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public int getNumberOfGuess() {
        return numberOfGuess;
    }

    public String getChosenWord() {
        return this.chosenWord;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasLost() {
        return hasLost;
    }

    private int generateRandomIndex(int range) {
        return (int) (Math.random() * range);
    }

    private String chooseARandomWord() {
        try {
            int randomIndex = generateRandomIndex(words.size());
            return this.words.get(randomIndex);
        } catch (Exception e) {
            return DEFAULT_WORD;
        }
    }

    private void updateGameStatus() {
        this.numberOfGuess++;
        int max_tries = getMAX_ROW();
        if (this.getNumberOfGuess() >= max_tries) {
            this.hasLost = true;
        }
    }

    private void performWordMatch(String word) {
        if (word.equals(this.chosenWord)) {
            this.hasWon = true;
        }
    }

    private boolean isNotNullOrEmptyString(String word) {
        if(word == null || word.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void addLettersToGrid(String word) {
        char letter;
        for (int i = 0; i < this.gameBoard.length - 1; i++) {
            letter = word.charAt(i);
            this.gameBoard[this.numberOfGuess][i] = letter;
        }
    }

    public void insertWordToGameBoard(String word) throws IllegalArgumentException{
        String formattedWord = word.toUpperCase();
        if(this.isNotNullOrEmptyString(word) && this.words.contains(formattedWord)) {
            this.addLettersToGrid(formattedWord);
            this.performWordMatch(formattedWord);
            this.updateGameStatus();
        } else {
            throw new IllegalArgumentException("Word Is Either Empty Or Not Found In Acceptable List");
        }
    }
}
