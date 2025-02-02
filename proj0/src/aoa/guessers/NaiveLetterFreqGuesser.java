package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.

        Map<Character, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (!freqMap.containsKey(c)) {
                    freqMap.put(c, 1);
                } else {
                    freqMap.put(c, freqMap.get(c) + 1);
                }
            }
        }
        return freqMap;
    }


    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.

        TreeMap<Character, Integer> sortedMap = new TreeMap<>(getFrequencyMap());

        for (Character guess : guesses) {
            sortedMap.remove(guess);
        }

        int maxFreq = 0;
        char maxChar = '?';

        for (Map.Entry<Character, Integer> entry : sortedMap.entrySet()) {
            if (!guesses.contains(entry.getKey())) {
                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                    maxChar = entry.getKey();
                }
            }
        }

        if (sortedMap.isEmpty()) {
            return '?';
        }

        return maxChar;

    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l','o');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
