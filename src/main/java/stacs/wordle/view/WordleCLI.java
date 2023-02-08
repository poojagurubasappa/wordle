package stacs.wordle.view;

import stacs.wordle.controller.WordleController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordleCLI {
    private WordleController wordleController;
    private final String White_Bold = "\033[1;97m";
    private final String Blue_Bold = "\033[1;34m";
    private final String Green_Bold = "\033[1;32m";
    private final String Yellow_Bold = "\033[1;33m";
    private final String Red_Bold = "\033[1;31m";
    private final String Green_Padding = "\033[42m";
    private final String Red_Font = "\033[0;31m";
    private final String Purple_Font = "\033[0;35m";
    private final String ColorNormalizer = "\033[0m";

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
        printCustomInformation(White_Bold, "WORDLE", true);
        printCustomInformation(Blue_Bold, "To Play The Game, Enter Five Lettered Dictionary Words Only", true);
    }

    private void computeBoardColors(char charToPrint, int index) {
        ArrayList<Integer> matchedLettersIndices = this.wordleController.getMatchedLettersIndices();
        ArrayList<Integer> misplacedLettersIndices = this.wordleController.getMisplacedLettersIndices();
        ArrayList<Integer> unfoundLettersIndices = this.wordleController.getUnfoundLettersIndices();

        String info = String.valueOf(charToPrint);
        if (matchedLettersIndices.contains(index)) {
            printCustomInformation(Green_Bold, info, false);
        } else if (misplacedLettersIndices.contains(index)) {
            printCustomInformation(Yellow_Bold, info, false);
        } else if (unfoundLettersIndices.contains(index)) {
            printCustomInformation(Red_Bold, info, false);
        } else {
            printCustomInformation(White_Bold, info, false);
        }
    }

    private void displayColoredFontsForBoard(Character[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            this.wordleController.computeLetterPositioningIndicesByRow(i);
            System.out.print("| ");
            for (int j = 0; j < gameBoard.length - 1; j++) {
                char charToPrint = gameBoard[i][j] == null ? '*' : gameBoard[i][j];
                this.computeBoardColors(charToPrint, j);
                System.out.print(" ");
            }
            System.out.print(" |" + ColorNormalizer + "\n");
        }
    }
    private void displayAlphabetsTemplate() {
        printCustomInformation(White_Bold, "-------------------------------------------------------------------------------------", true);
        ArrayList<Character> greenWords = this.wordleController.getAllMatchedLetters();
        ArrayList<Character> yellowWords = this.wordleController.getAllMisplacedLetters();
        ArrayList<Character> allWords = this.wordleController.getAllOptedLetters();
        for (char i = 'A'; i <= 'Z'; i++) {
            if (i == 'I' || i == 'R') {
                System.out.println();
            }

            if (greenWords.contains(String.valueOf(i))) {
                printCustomInformation(Green_Bold, " | " + i + " | ", false);
            } else if (yellowWords.contains(String.valueOf(i))) {
                printCustomInformation(Yellow_Bold, " | " + i + " | ", false);
            } else if (allWords.contains(i)) {
                printCustomInformation(Red_Bold, " | " + i + " | ", false);
            } else {
                printCustomInformation(White_Bold, " | " + i + " | ", false);
            }
        }
        printCustomInformation(White_Bold,"\n-------------------------------------------------------------------------------------", true);
    }

    private void displayGameBoard() {
        Character[][] gameBoard = this.wordleController.displayGameBoard();
        this.displayColoredFontsForBoard(gameBoard);
        this.displayAlphabetsTemplate();
    }

    private void printCustomInformation(String fontColor, String information, boolean shouldPrintInNewLine) {
        String content = fontColor + information + ColorNormalizer;
        if(shouldPrintInNewLine) {
            System.out.println(content);
        } else {
            System.out.print(content);
        }
    }

    private void restartGameInstructions(Scanner scanner) {
        this.displayStatistics();
        System.out.println("Enter 1 to restart a new game, 0 to abort game");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            try {
                this.wordleController.startNewGame();
            } catch (FileNotFoundException e) {
                printCustomInformation(Red_Font, "Sorry, something went wrong", true);
            }
            this.displayGameBoard();
        } else {
            System.exit(0);
        }
    }

    private void displayStatistics() {
        System.out.println("----------");
        System.out.println("STATISTICS");
        System.out.println("----------");
        System.out.println("Games Played: " + this.wordleController.getNumberOfGamesPlayed());
        System.out.println("Win %: " +  this.wordleController.getWinPercentage());
    }

    private void handleUserInput(String userInput, Scanner scanner) {
        try {
            this.wordleController.insertWordToGameBoard(userInput);
            if (this.wordleController.hasWonGame()) {
                printCustomInformation(Green_Padding, "Congratulations, you have won the game", true);
                this.restartGameInstructions(scanner);
            } else if (this.wordleController.hasLostGame()) {
                printCustomInformation(Red_Font, "Game Over", true);
                printCustomInformation(Blue_Bold, "The word is: " + this.wordleController.getChosenWord(), true);
                this.restartGameInstructions(scanner);
            } else {
                this.displayGameBoard();
            }
        } catch (IllegalArgumentException e) {
            printCustomInformation(Red_Font,e.getMessage() + " Please Retry.\n", true);
        }
    }
}
