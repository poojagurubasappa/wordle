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
    public WordleGame(String wordListFilePath) {
        this.words = new ArrayList<>();
        this.wordListFilePath = wordListFilePath;
    }

    public void startGame() throws FileNotFoundException{
        this.gameBoard = new Character[MAX_ROW][MAX_COL];
        this.loadWordlist();
        this.chosenWord = this.chooseARandomWord();
        this.numberOfGuess = 0;
    }

    public void loadWordlist() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new FileReader(wordListFilePath));
        while (scanner.hasNextLine()) {
            this.words.add(scanner.nextLine());
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

    public void insertWordToGameBoard(String word) {
        char letter;
        for (int i = 0; i < this.gameBoard.length - 1; i++) {
            letter = word.charAt(i);
            this.gameBoard[this.numberOfGuess][i] = letter;
        }
        this.numberOfGuess++;
    }
}
