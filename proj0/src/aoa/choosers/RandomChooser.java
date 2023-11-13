package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;
    private List<Character> guesses = new ArrayList<>();

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        List<String> words = FileUtils.readWords(dictionaryFile);
        List<String> matchedWords = new ArrayList<>();

        if (wordLength<1){
            throw new IllegalArgumentException("wordLength must greater than 0");
        }

        for (String word : words){
            if (word.length() == wordLength){
                matchedWords.add(word);
            }
        }

        if (matchedWords.isEmpty()){
            throw new IllegalStateException("there are no words found of wordLength");
        }

        chosenWord = matchedWords.get(StdRandom.uniform(0,matchedWords.size()));
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        if (chosenWord.contains(Character.toString(letter))){
            guesses.add(letter);
            getPattern();
        }
        int count = 0;
        for (int i=0; i < chosenWord.length(); i++){
            if (chosenWord.charAt(i) == letter){
                count += 1;
            }
        }return count;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        pattern = chosenWord;
        for (int i=0; i<chosenWord.length(); i++){
            if (guesses.contains(chosenWord.charAt(i))){
                char c = chosenWord.charAt(i);
                pattern = pattern.substring(0,i) + c + pattern.substring(i+1);
            }else {
                pattern = pattern.substring(0,i) + '-' + pattern.substring(i+1);
            }
        }return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
