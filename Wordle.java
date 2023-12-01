package org.cis1200.wordle;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Wordle {
    /**
     * Constructor sets up game state.
     */
    public Wordle() {
        reset();
    }

    public List<String> createWordList() {

        List<String> wordlist = new ArrayList<>();
        BufferedReader br = FileLineIterator.fileToReader("./files/usa.txt");
        FileLineIterator f = new FileLineIterator(br);
        while (f.hasNext()) {
            String word = f.next();
            if (word != null && word.length() == 5) {
                wordlist.add(word);
            }
        }

        return wordlist;
    }

    public String getRandomWord(List<String> listOfWords) {
        Random r = new Random();
        String randomWord = listOfWords.get(r.nextInt(listOfWords.size()));
        return randomWord;
    }

    /**
     *  isValid checks if the guess is a real word
     */
    public static boolean isValid(String guess, List<String> listOfWords) {
        return guess.length() == 5 && listOfWords.contains(guess);
    }

    /**
     * makeArray takes the user's guess as a string and returns it as an array of chars,
     * so it is easier to check whether they are correct.
     */
    public char[] makeArray(String guess) {
        return guess.toCharArray();
    }


    /**
     *
     * @param currentWord
     * @param guess
     * @param column
     * @return whether that letter is somewhere in the word.
     * HELPER function for checkGuess
     */
    private boolean contains(char[] currentWord, char[] guess, int column) {
        for (int index = 0; index < currentWord.length; index++) {
            if (index != column && guess[column] == currentWord[index]) {
                return true;
            }
        }
        return false ;
    }

    /**
     *
     * @param guess
     * @return character list
     * G = correct letter, correct place (GREEN)
     * Y = correct letter, wrong place (YELLOW)
     * B = letter not in word (BLACK)
     */
    public List<Character> checkGuess(char[] guess, String wordString) {
        List<Character> result = new LinkedList<>();
        char[] correctWord = makeArray(wordString);
        for (int i = 0; i < 5; i++) {
            if (guess[i] == correctWord[i]) {
                result.add('g');
            } else if (contains(correctWord, guess, i)) {
                result.add('y');
            } else {
                result.add('b');
            }
        }
        return result;
    }

    public boolean isCorrect(List<Character> c) {
        for (char x: c) {
            if (x != 'g') {
                return false;
            }
        }
        return true;
    }


    //FILE I/O Concept
    public void writeGuessesToFile(
            List<List<Character>> guessesToWrite, String filePath
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (List<Character> characters : guessesToWrite) {
                bw.write(characters.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {

    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Wordle w = new Wordle();
        String guess = "guess";
        int numGuesses = 0;
        boolean guessedWord = false;

    }
}
