package stacs.wordle;


import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    @Test
    public void shouldLoadWordlist() throws FileNotFoundException
    {
        ArrayList<String> wordlist = WordleApp.loadWordlist("src/test/resources/wordlist-test.txt");

        // test wordlist only contains 3 words, so wordlist should have the size of 3
        assertEquals(3, wordlist.size());
    }

    @Test
    public void shouldLoadWordList_fileNameCannotBeInvalid() throws FileNotFoundException {
        assertThrows(FileNotFoundException.class, () -> WordleApp.loadWordlist("src/test/resources/wordlistinvalid-test.txt"));
    }
}
