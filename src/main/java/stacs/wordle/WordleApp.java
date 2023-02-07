package stacs.wordle;

import stacs.wordle.model.WordleGame;
import stacs.wordle.model.controller.WordleController;
import stacs.wordle.model.view.WordleCLI;

import java.io.FileNotFoundException;

public class WordleApp
{
    public static void main( String[] args )
    {
        final String WORDS_LIST_PATH = "src/main/resources/wordlist.txt";
        WordleGame game = new WordleGame(WORDS_LIST_PATH);
        try {
            game.startGame();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        WordleCLI cli = new WordleCLI();
        new WordleController(game, cli);
        cli.start();
    }


}
