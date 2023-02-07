package stacs.wordle;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stacs.wordle.model.WordleGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    WordleGame game;
    @BeforeEach
    void setup() throws FileNotFoundException {
        game = new WordleGame("src/test/resources/wordlist-test.txt");
        game.startGame();
    }

    @Test
    public void shouldLoadWordlist() throws FileNotFoundException
    {
        assertEquals(3, game.getWords().size());
    }

    @Test
    public void shouldStartGame_wordFileNameCannotBeInvalid() throws FileNotFoundException {
        assertThrows(FileNotFoundException.class, () ->  { WordleGame game = new WordleGame("invalidFilePath.txt"); game.startGame(); });
    }

    @Test
    public void shouldStartTheGameWith5x6GameBoard() {
        assertEquals(game.getMAX_ROW(), 6);
        assertEquals(game.getMAX_COL(), 5);
    }

    @Test
    public void shouldChooseARandomWordFromWordList() {
        assertNotNull(game.getChosenWord());
        assertEquals(game.getChosenWord().length(), 5);
    }

    @Test
    public void shouldReturnTheCorrectNumberOfGuesses() {
        assertEquals(game.getNumberOfGuess(), 0);
        game.insertWordToGameBoard("debug");
        assertEquals(game.getNumberOfGuess(), 1);
        game.insertWordToGameBoard("debug");
        assertEquals(game.getNumberOfGuess(), 2);
    }

    @Test
    public void shouldInsertWordIntoGameBoard() {
        String word1 = "debug";
        game.insertWordToGameBoard(word1);
        Character[][] gameBoard = game.getGameBoard();
        assertEquals(gameBoard[0][0], 'd');
        assertEquals(gameBoard[0][1], 'e');
        assertEquals(gameBoard[0][2], 'b');
        assertEquals(gameBoard[0][3], 'u');
        assertEquals(gameBoard[0][4], 'g');
        assertEquals(gameBoard[1][0], null);

        String word2 = "cache";
        game.insertWordToGameBoard(word2);
        Character[][] updatedGameBoard = game.getGameBoard();
        assertEquals(updatedGameBoard[1][0], 'c');
        assertEquals(updatedGameBoard[1][1], 'a');
        assertEquals(updatedGameBoard[1][2], 'c');
        assertEquals(updatedGameBoard[1][3], 'h');
        assertEquals(updatedGameBoard[1][4], 'e');
        assertEquals(updatedGameBoard[2][0], null);
    }

    @Test
    public void shouldInsertWordsOnlyFoundInWordList_illegalArgumentException() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () ->  game.insertWordToGameBoard("12345"));
        assertThrows(IllegalArgumentException.class, () ->  game.insertWordToGameBoard("ross"));
        assertThrows(IllegalArgumentException.class, () ->  game.insertWordToGameBoard("@$$$$"));
    }

    @Test
    public void shouldTerminateGameWhenOutOfTries() {
        String chosenWord = game.getChosenWord();
        String randomWord = "";
        for(String s: this.game.getWords()) {
            if(!s.equals(chosenWord)) {
                randomWord = s;
                return;
            }
        }
        game.insertWordToGameBoard(randomWord);
        game.insertWordToGameBoard(randomWord);
        game.insertWordToGameBoard(randomWord);
        game.insertWordToGameBoard(randomWord);
        game.insertWordToGameBoard(randomWord);
        game.insertWordToGameBoard(randomWord);
        assertFalse(game.getGameWon());
    }

    public void shouldTerminateGameOnGuessingChosenWord() {
        String chosenWord = game.getChosenWord();
        game.insertWordToGameBoard(chosenWord);
        assertTrue(game.getGameWon());
    }
}
