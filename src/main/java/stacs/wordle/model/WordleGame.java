package stacs.wordle.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Wordle Game Class represents the model to encapsulate
 * game details.
 */
public class WordleGame {
    private final int MAX_ROW = 6;
    private final int MAX_COL = 5;
    private final Statistics statistics;
    private Character[][] gameBoard;
    private final String wordListFilePath;
    private final ArrayList<String> words;
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

    /**
     * This is the class constructor.
     * @param wordListFilePath is the path to dictionary file.
     */
    public WordleGame(String wordListFilePath) {
        this.words = new ArrayList<>();
        this.wordListFilePath = wordListFilePath;
        this.statistics = new Statistics();
    }

    /**
     * This method is to turn the game ON.
     * @throws FileNotFoundException when dictionary file path is invalid or empty.
     */
    public void startGame() throws IOException{
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

    /**
     * This method reads the dictionary file, changes its case, stores each word, in an ArrayList.
     * @throws FileNotFoundException when the dictionary file is not found in location specified.
     */
    public void loadWordlist() throws IOException
    {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(wordListFilePath);
        if(inputStream == null) {
            throw new IOException();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            this.words.add(line.toUpperCase());
        }
    }

    /**
     * This method exposes the number of rows of game board.
     * @return number of rows of game board.
     */
    public int getMAX_ROW() {
        return MAX_ROW;
    }

    /**
     * This method exposes the number of columns of gameboard.
     * @return number of columns of game board.
     */
    public int getMAX_COL() {
        return MAX_COL;
    }

    /**
     * This method is for fetching the game board.
     * @return game board.
     */
    public Character[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * This method is for exposing all the words read from a dictionary file.
     * @return a list of words, each which can be used for every game.
     */
    public ArrayList<String> getWords() {
        return words;
    }

    /**
     * This method is for fetching the number of guesses exhausted by the player.
     * @return number of guesses exhausted by player.
     */
    public int getNumberOfGuess() {
        return numberOfGuess;
    }

    /**
     * This method is for fetching the originally chosen word for each game selected at random from the word list.
     * @return a chosen word for each game.
     */
    public String getChosenWord() {
        return this.chosenWord;
    }

    /**
     * This method is for identifying if the player has won the game.
     * @return true or false.
     */
    public boolean getHasWon() {
        return hasWon;
    }
    /**
     * This method is for identifying if the player has lost the game.
     * @return true or false.
     */
    public boolean getHasLost() {
        return hasLost;
    }
    /**
     * This method is for returning the indices of all letters that are guessed correctly in a word input.
     * These are the letters that appear at right positions as in the original word.
     * @return a list comprising indices of rightly guessed letters in a word input.
     */
    public ArrayList<Integer> getGreenLettersIndices() {
        return greenLettersIndices;
    }
    /**
     * This method is for returning the indices of all letters in a word input that aren't found in the original word.
     * @return a list comprising indices of letters guessed in a word input, not found in the original word.
     */
    public ArrayList<Integer> getRedLettersIndices() {
        return redLettersIndices;
    }
    /**
     * This method is for returning the indices of all letters in a word input, that are guessed at misplaced positions.
     * @return a list comprising indices of letters in a word input, that appear in original word but at wrong place.
     */
    public ArrayList<Integer> getYellowLettersIndices() {
        return yellowLettersIndices;
    }
    /**
     * This method is for returning all the letters that are guessed correctly in a word input.
     * These are the letters that appear at right positions as in the original word.
     * @return a list comprising rightly guessed letters in a word input.
     */
    public ArrayList<Character> getAllRightlyGuessedLetters() {
        return allRightlyGuessedLetters;
    }
    /**
     * This method is for returning all the letters that appear in the original word, but in wrong positions.
     * These are the letters that appear at right positions as in the original word.
     * @return a list comprising rightly guessed letters in a word input with misplaced positions.
     */
    public ArrayList<Character> getAllMisplacedLetters() {
        return allMisplacedLetters;
    }
    /**
     * This method is for returning all the letters that were used across guesses in the game.
     * @return a list comprising all the letters used in the game by the player.
     */
    public ArrayList<Character> getAllOptedLetters() {
        return allOptedLetters;
    }

    /**
     * This method is for fetching the game statistics instance.
     * @return statistics instance.
     */
    public Statistics getStatistics() {
        return this.statistics;
    }

    /**
     * This method is for generating a random number.
     * @param range word list's size
     * @return a random number
     */
    private int generateRandomIndex(int range) {
        return (int) (Math.random() * range);
    }

    /**
     * This method chooses a word at a random index from the words list, a default word in case of exceptions.
     * @return a randomly chosen word for the game or a default word in case of exceptions.
     */
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
        return word != null && !word.isEmpty();
    }

    private void addLettersToGrid(String word) {
        char letter;
        for (int i = 0; i < this.gameBoard.length - 1; i++) {
            letter = word.charAt(i);
            this.gameBoard[this.numberOfGuess][i] = letter;
            allOptedLetters.add(letter);
        }
    }

    /**
     * This method is for inserting words to each row of the game board as game progresses.
     * @param word the input word.
     * @throws IllegalArgumentException when the word is not found in the dictonary.
     */
    public void insertWordToGameBoard(String word) throws IllegalArgumentException{
        String formattedWord = word.toUpperCase();
        if(this.isNotNullOrEmptyString(word) && this.words.contains(formattedWord)) {
            this.addLettersToGrid(formattedWord);
            this.performWordMatch(formattedWord);
            this.updateGameStatus();
        } else {
            throw new IllegalArgumentException("Word Is Either Empty Or Not Found In Our Dictionary");
        }
    }

    private void resetLetterPositionDataSets() {
        this.greenLettersIndices = new ArrayList<>();
        this.redLettersIndices = new ArrayList<>();
        this.yellowLettersIndices = new ArrayList<>();
    }

    private String trimWord(String originalWord, char target) {
        return originalWord.replaceFirst(String.valueOf(target) , " ");
    }

    private boolean isNotEmptySquare(int row, int col) {
        return this.gameBoard[row][col] != null;
    }

    /**
     * This method enables color coding for each word in the game grid by row number, for views' usage.
     * @param rowNumber row number from game board grid.
     */
    public void computeLetterPositioningIndicesByRow(int rowNumber) {
        char letterInBoard, letterInOriginalWord;
        HashMap<Integer, Character> unmatched = new HashMap<>();
        String originalWord = this.getChosenWord();
        this.resetLetterPositionDataSets();
        for (int i = 0; i < this.gameBoard[rowNumber].length; i++) {
            if(this.isNotEmptySquare(rowNumber, i)) {
                letterInBoard = this.gameBoard[rowNumber][i];
                letterInOriginalWord = originalWord.charAt(i);
                if (letterInBoard == letterInOriginalWord) {
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
