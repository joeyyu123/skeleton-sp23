package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character, Integer> map = this.getFreqMapThatMatchesPattern(pattern, guesses);
        if (map.isEmpty()){
            return '?';
        }
        int max = 0;
        char maxkey = 'a';
        for (Character letter : map.keySet()){
            if (guesses.contains(letter)){
                continue;
            }
            if (map.get(letter) > max){
                max = map.get(letter);
                maxkey = letter;
            }
        }return maxkey;
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(String pattern, List<Character> guesses){
        Map<Character, Integer> map = new TreeMap<>();
        List<String> matchedWords = new ArrayList<>();

        for (String word : words){
            if (word.length() != pattern.length()){
                continue;
            }else {
                boolean flag = true;
                for (int i=0; i<pattern.length(); i++){
                    if ((word.charAt(i) != pattern.charAt(i)) && (pattern.charAt(i) != '-')){
                        flag = false;
                        break;
                    }else if (guesses.contains(word.charAt(i)) && (pattern.charAt(i)) == '-'){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    matchedWords.add(word);
                }
            }
        }

        for (String word : matchedWords){
            for (char letter : word.toCharArray()){
                if (map.containsKey(letter)){
                    map.put(letter, map.get(letter) + 1);
                }else {
                    map.put(letter, 1);
                }
            }
        }return map;
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
