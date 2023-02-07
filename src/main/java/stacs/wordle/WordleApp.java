package stacs.wordle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;

public class WordleApp
{
    public static void main( String[] args )
    {
        System.out.println("Welcome to CS5031 - Wordle");
    }

    // Unimplemented skeleton
    // You may refactor this method
    protected static ArrayList<String> loadWordlist(String wordlistPath) throws FileNotFoundException
    {
        ArrayList<String> words = new ArrayList<String>();
        Scanner scanner = new Scanner(new FileReader(wordlistPath));
        String line;
        while (scanner.hasNextLine()) {
            words.add(scanner.next());
        }
        scanner.close();
        return words;
    }
}
