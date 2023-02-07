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
    public WordleGame(String wordListFilePath) {
        this.words = new ArrayList<>();
        this.wordListFilePath = wordListFilePath;
    }

    public void startGame() throws FileNotFoundException{
        this.gameBoard = new Character[MAX_ROW][MAX_COL];
        this.loadWordlist();
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
}
