package stacs.wordle.controller;

import stacs.wordle.model.WordleGame;
import stacs.wordle.view.WordleCLI;

public class WordleController {
    private final WordleGame game;

    public WordleController(WordleGame game, WordleCLI cli) {
        this.game = game;
        cli.setController(this);
    }
}
