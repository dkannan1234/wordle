package org.cis1200.wordle;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;


@SuppressWarnings("serial")
public class WordleBoard extends JPanel {

    private static Wordle w; // model for the game
    private static JLabel status; // current status text
    static int tries;
    static String correctWord;
    static List<List<List<String>>> allGuesses = new ArrayList<>();
    private static List<String> listOfWords = new ArrayList<>();
    static boolean done;
    // Game constants
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 350;

    /**
     * Initializes the game board.
     */
    public WordleBoard(JLabel statusInit) throws IOException {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        setW(new Wordle()); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        listOfWords = getW().createWordList();
    }

    public static Wordle getW() {
        return w;
    }

    public static void setW(Wordle w) {
        WordleBoard.w = w;
    }

    public void reset() {
        getW().reset();
        repaint();
        allGuesses = new ArrayList<>();
        done = false;
        tries = 0;
        correctWord = getW().getRandomWord(listOfWords);
        //System.out.println("Correct word: " + CORRECT_WORD);
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }


    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        status.setText("Type a 5 letter guess.");
    }


    public void displayAllWords(Graphics g, List<List<List<String>>> allWords) {
        int xOffset = 0;
        int yOffset = 0;
        for (List<List<String>> word: allWords) {
            xOffset = 0;
            for (List<String> letter: word) {
                if (letter.get(1).equals("g")) {
                    g.setColor(Color.GREEN);
                }
                if (letter.get(1).equals("y")) {
                    g.setColor(Color.ORANGE);
                }
                if (letter.get(1).equals("b")) {
                    g.setColor(Color.DARK_GRAY);
                }
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString(letter.get(0), 100 + (xOffset * 15), 50 + (yOffset * 25));
                xOffset++;
            }
            yOffset++;
        }
    }
    public void buttonPressed(String userInput) {
        status.setText("Type a 5 letter guess.");
        char[] inputArray = getW().makeArray(userInput);
        List<List<String>> letterAndColor = new ArrayList<>();
        if (getW().isValid(userInput, listOfWords) && tries <= 5) {
            List<Character> colorOfLetters = getW().checkGuess(inputArray, correctWord);
            tries++;
            done = true;
            for (char i : colorOfLetters) {
                if (i != 'g') {
                    done = false; }
            }
            for (int i = 0; i <= 4; i++) {
                List<String> letterColor = new ArrayList<>();
                letterColor.add(Character.toString(inputArray[i]));
                letterColor.add(Character.toString(colorOfLetters.get(i)));
                letterAndColor.add(letterColor);
            }
            allGuesses.add(letterAndColor);
            displayAllWords(getGraphics(), allGuesses);
            if (getW().isCorrect(colorOfLetters)) {
                getGraphics().drawString("YOU GUESSED THE WORD!", 130, 340);
            }

            if (tries == 5 && !getW().isCorrect(colorOfLetters)) {
                getGraphics().drawString("RAN OUT OF TRIES.", 130, 300);
                getGraphics().drawString("CORRECT WORD: " + correctWord, 130, 330);
            }
        } else {
            //NOT VALID
            status.setText("NOT A VALID WORD.");

        }
    }
    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}