package me.daniel;

import java.util.ArrayList;
import java.util.Scanner;

public class HangManGame implements Game {
    private byte lives = 7;
    private String word;
    private String maskedWord;
    private final ArrayList<Character> guessedChars = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);
    public boolean done = false;

    public final String[][] hangingMan = {
            {
                    "=============",
                    "|/          \\|",
                    "|           O",
                    "|           ^",
                    "_\\____      ^"
            },
            {
                    "=============",
                    "|/         \\|",
                    "|           O",
                    "|           ^",
                    "_\\____       "
            },
            {
                    "=============",
                    "|/         \\|",
                    "|           O",
                    "|            ",
                    "_\\____       "
            },
            {
                    "=============",
                    "|/         \\|",
                    "|            ",
                    "|            ",
                    "_\\____       "
            },
            {
                    "=============",
                    "|/           ",
                    "|            ",
                    "|            ",
                    "_\\____       "
            },
            {
                    "             ",
                    "|/           ",
                    "|            ",
                    "|            ",
                    "_\\____       "
            },
            {
                    "             ",
                    "             ",
                    "             ",
                    "             ",
                    "______       "
            },
            {
                    "             ",
                    "             ",
                    "             ",
                    "             ",
                    "             "
            },
    };

    private void generateNewWord() {
        word = Words.english[(int)((Words.english.length - 1)*Math.random())]; // get the index of random num between 0 and 1 x length of words to int
        maskedWord = "_".repeat(word.length());
    }

    public void guessChar(char guess) {
        ArrayList<Byte> indexes = new ArrayList<>();
        for (byte i = 0; i < word.length(); i++) {
            if (word.toCharArray()[i] == guess) {
                indexes.add(i);
            }
        }
        guessedChars.add(guess);
        if (!indexes.isEmpty()) {
            reveal(indexes, guess);
            if (maskedWord.equals(word)) win();
            return;
        }
        lives--;
    }

    private void reveal(ArrayList<Byte> indexes, Character ch) {
        StringBuilder newMaskedWord = new StringBuilder(maskedWord);
        for (byte i: indexes) {
            newMaskedWord.replace(i, i+1, ch.toString());
        }
        maskedWord = newMaskedWord.toString();
    }

    public void reset() {
        generateNewWord();
        guessedChars.clear();
        guessedChars.add('>');
        lives = 7;
    }

    public void win() {
        System.out.println("Congratulations! You win!\n" +
                "Lives remaining: " + lives + "\tThe word was: " + word +
                "\n\t\t\t\t\tYou guessed: " + maskedWord);
        reset();
    }

    public void guessWord(String guess) {
        if (!guess.equals(word)) {
            lives--;
            return;
        }
        win();
    }

    public void guess() {
        System.out.print("Enter your guess: ");
        String guess = input.nextLine();
        if (guess.length() == 1)     guessChar(guess.charAt(0));
        else                         guessWord(guess);
    }

    public void start() {
        reset();
        while (!done) {
            printInfo();
            guess();
            if (lives==0) loose();
        }
    }

    public void loose() {
        printInfo();
        System.out.println("Unlucky! You lost :|\n" +
                "\t\t\t\t\tThe word was: " + word +
                "\n\t\t\t\t\tYou guessed: " + maskedWord);
        reset();
    }

    public void printInfo() {
        String toPrint = "============================================================\n" +
           String.format("|Lives: %-51d|\n", lives) +
           String.format("|Guessed Letters: %-26s               |\n", guessedChars.toString()
                                                                                 .substring(1, 3 * guessedChars.size() - 1)
                                                                                 .replaceAll(", ", "")) +
           String.format("|Masked Word: %-30s               |\n", maskedWord) +
           String.format("|                %-13s                             |\n", hangingMan[lives][0]) +
           String.format("|                %-13s                             |\n", hangingMan[lives][1]) +
           String.format("|                %-13s                             |\n", hangingMan[lives][2]) +
           String.format("|                %-13s                             |\n", hangingMan[lives][3]) +
           String.format("|                %-13s                             |\n", hangingMan[lives][4]) +
                         "============================================================";
        System.out.println(toPrint);
    }
}
