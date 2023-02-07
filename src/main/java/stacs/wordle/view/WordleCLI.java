package stacs.wordle.view;

import stacs.wordle.controller.WordleController;

import java.util.Scanner;

public class WordleCLI {
    private WordleController wordleController;

    public void setController(WordleController wordleController) {
        this.wordleController = wordleController;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        this.displayGameInstructions();
        this.displayGameBoard();
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            handleUserInput(userInput, scanner);
        }
        scanner.close();
    }

    private void displayGameInstructions() {

    }

    private void displayGameBoard() {

    }

    private void handleUserInput(String userInput, Scanner scanner) {

    }
}
