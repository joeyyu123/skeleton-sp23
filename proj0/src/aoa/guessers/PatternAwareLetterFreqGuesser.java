package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(String pattern) {
        Map<Character, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            if (matchesPattern(word, pattern)) {
                for (char c : word.toCharArray()) {
                    freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
                }
            }
        }

        return freqMap;
    }

    private boolean matchesPattern(String word, String pattern) {
        if (word.length() != pattern.length()) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            char wordChar = word.charAt(i);
            char patternChar = pattern.charAt(i);

            if (patternChar != '-' && patternChar != wordChar) {
                return false;
            }
        }

        return true;
    }
    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        TreeMap<Character, Integer> sortedMap = new TreeMap<>(getFreqMapThatMatchesPattern(pattern));

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
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}