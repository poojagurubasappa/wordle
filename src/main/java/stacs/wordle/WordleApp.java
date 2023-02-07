package stacs.wordle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class WordleApp
{
    public static void main( String[] args )
    {
        System.out.println("Welcome to CS5031 - Wordle");
    }

    protected static ArrayList<String> loadWordlist(String wordlistPath) throws FileNotFoundException
    {
        ArrayList<String> words = new ArrayList<String>();
        Scanner scanner = new Scanner(new FileReader(wordlistPath));
        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine());
        }
        scanner.close();
        return words;
    }
}
