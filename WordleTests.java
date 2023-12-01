package org.cis1200.wordle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordleTests {

    @Test
    public void testIsValid() {
        Wordle w = new Wordle();
        List<String> listofWords = w.createWordList();
        assertEquals(w.isValid("shell", listofWords), true);
    }

    @Test
    public void testIsValidFalse() {
        Wordle w = new Wordle();
        List<String> listofWords = w.createWordList();
        assertEquals(w.isValid("shel", listofWords), false);
    }

    @Test
    public void testIsValidNotRealWord() {
        Wordle w = new Wordle();
        List<String> listofWords = w.createWordList();
        assertEquals(w.isValid("asdfg", listofWords), false);
    }

    @Test
    public void testCheckGuessCorrect() {
        Wordle w = new Wordle();
        char [] c = w.makeArray("house");
        List <Character> correctList = new ArrayList<>();
        correctList.add('g');
        correctList.add('g');
        correctList.add('g');
        correctList.add('g');
        correctList.add('g');
        assertEquals(w.checkGuess(c, "house"), correctList);
    }

    @Test
    public void testCheckGuessWrongSpot() {
        Wordle w = new Wordle();
        char [] c = w.makeArray("shell");
        List <Character> correctList = new ArrayList<>();
        correctList.add('y');
        correctList.add('y');
        correctList.add('y');
        correctList.add('b');
        correctList.add('b');
        assertEquals(w.checkGuess(c, "house"), correctList);
    }

}
