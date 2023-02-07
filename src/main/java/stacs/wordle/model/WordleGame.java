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
    private ArrayList<Integer> greenLettersIndices;
    private ArrayList<Integer> redLettersIndices;
    private ArrayList<Integer> yellowLettersIndices;
    private ArrayList<Character> allRightlyGuessedLetters;
    private ArrayList<Character> allMisplacedLetters;
    private ArrayList<Character> allOptedLetters;


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

    public void computeLetterPositioningIndicesByRow(int rowNumber) {
        String originalWord = this.getChosenWord();
        this.resetLetterPositionDataSets();
        char currentLetterInBoard;
        char currentLetterInOriginalWord;
        for (int i = 0; i < this.gameBoard[rowNumber].length; i++) {
            currentLetterInBoard = this.gameBoard[rowNumber][i];
            currentLetterInOriginalWord = originalWord.charAt(i);
            if (String.valueOf(currentLetterInBoard) != null) {
                if (Character.compare(currentLetterInBoard, currentLetterInOriginalWord) == 0) {
                    greenLettersIndices.add(i);
                    allRightlyGuessedLetters.add(currentLetterInBoard);
                    originalWord = this.trimWord(originalWord, currentLetterInOriginalWord);
                } else if (originalWord.contains(String.valueOf(currentLetterInBoard))) {
                    yellowLettersIndices.add(i);
                    allMisplacedLetters.add(currentLetterInBoard);
                    originalWord = this.trimWord(originalWord, currentLetterInOriginalWord);
                } else {
                    redLettersIndices.add(i);
                }
            }
        }
    }
}
