package stacs.wordle.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordleGame {
    private final int MAX_ROW = 6;
    private final int MAX_COL = 5;
    private final Statistics statistics;
    private Character[][] gameBoard;
    private final String wordListFilePath;
    private ArrayList<String> words;
    private String chosenWord;
    private final String DEFAULT_WORD = "debug";
    private int numberOfGuess;
    private boolean hasWon;
    private boolean hasLost;
    private ArrayList<Integer> greenLettersIndices;
    private ArrayList<Integer> redLettersIndices;
    private ArrayList<Integer> yellowLettersIndices;
    private ArrayList<Character> allRightlyGuessedLetters;
    private ArrayList<Character> allMisplacedLetters;
    private ArrayList<Character> allOptedLetters;


    public WordleGame(String wordListFilePath) {
        this.words = new ArrayList<>();
        this.wordListFilePath = wordListFilePath;
        this.statistics = new Statistics();
    }

    public void startGame() throws FileNotFoundException{
        this.statistics.incrementGameCount();
        this.gameBoard = new Character[MAX_ROW][MAX_COL];
        this.loadWordlist();
        this.chosenWord = this.chooseARandomWord();
        this.numberOfGuess = 0;
        this.hasWon = false;
        this.hasLost = false;
        this.allRightlyGuessedLetters = new ArrayList<>();
        this.allMisplacedLetters = new ArrayList<>();
        this.allOptedLetters = new ArrayList<>();
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

    public ArrayList<Integer> getGreenLettersIndices() {
        return greenLettersIndices;
    }

    public ArrayList<Integer> getRedLettersIndices() {
        return redLettersIndices;
    }

    public ArrayList<Integer> getYellowLettersIndices() {
        return yellowLettersIndices;
    }

    public ArrayList<Character> getAllRightlyGuessedLetters() {
        return allRightlyGuessedLetters;
    }

    public ArrayList<Character> getAllMisplacedLetters() {
        return allMisplacedLetters;
    }

    public ArrayList<Character> getAllOptedLetters() {
        return allOptedLetters;
    }

    public Statistics getStatistics() {
        return this.statistics;
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
            this.statistics.incrementNumberOfGamesWon();
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
            allOptedLetters.add(letter);
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

    private void resetLetterPositionDataSets() {
        this.greenLettersIndices = new ArrayList<>();
        this.redLettersIndices = new ArrayList<>();
        this.yellowLettersIndices = new ArrayList<>();
    }

    private String trimWord(String originalWord, char target) {
        String trimmedWord = originalWord.replaceFirst(String.valueOf(target) , " ");
        return trimmedWord;
    }

    private boolean isNotEmptySquare(int row, int col) {
        return this.gameBoard[row][col] != null;
    }

    public void computeLetterPositioningIndicesByRow(int rowNumber) {
        char letterInBoard, letterInOriginalWord;
        HashMap<Integer, Character> unmatched = new HashMap<>();
        String originalWord = this.getChosenWord();
        this.resetLetterPositionDataSets();
        for (int i = 0; i < this.gameBoard[rowNumber].length; i++) {
            if(this.isNotEmptySquare(rowNumber, i)) {
                letterInBoard = this.gameBoard[rowNumber][i];
                letterInOriginalWord = originalWord.charAt(i);
                if (Character.compare(letterInBoard, letterInOriginalWord) == 0) {
                    greenLettersIndices.add(i);
                    allRightlyGuessedLetters.add(letterInOriginalWord);
                    originalWord = this.trimWord(originalWord, letterInOriginalWord);
                } else {
                    unmatched.put(i, this.gameBoard[rowNumber][i]);
                }
            }
        }
        for (int i: unmatched.keySet()) {
            if(originalWord.contains(String.valueOf(unmatched.get(i)))) {
                yellowLettersIndices.add(i);
                allMisplacedLetters.add(unmatched.get(i));
                originalWord = this.trimWord(originalWord, unmatched.get(i));
            } else {
                redLettersIndices.add(i);
            }
        }
    }
}
