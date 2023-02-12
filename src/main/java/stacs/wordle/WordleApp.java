package stacs.wordle;

import stacs.wordle.model.WordleGame;
import stacs.wordle.controller.WordleController;
import stacs.wordle.view.WordleCLI;

import java.io.IOException;

public class WordleApp
{
    public static void main( String[] args )
    {
        final String WORDS_LIST_PATH = "wordlist.txt";
        WordleGame game = new WordleGame(WORDS_LIST_PATH);
        try {
            game.startGame();
        } catch (IOException e) {
            System.out.println("Sorry something went wrong");
        }
        WordleCLI cli = new WordleCLI();
        new WordleController(game, cli);
        cli.start();
    }
}
