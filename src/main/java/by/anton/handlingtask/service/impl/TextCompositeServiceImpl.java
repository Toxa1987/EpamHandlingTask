package by.anton.handlingtask.service.impl;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.entity.TextComponentType;
import by.anton.handlingtask.exception.HandlingException;
import by.anton.handlingtask.service.TextCompositeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextCompositeServiceImpl implements TextCompositeService {
    private static final Logger logger = LogManager.getLogger();
    private static final String WORD_REGEX = "[\\wА-яа-я]([\\wА-яа-я]|[^\\)]|!=)*[^.?,!\\)]";
    private static final String VOWEL_REGEX = "[aoiuyeоаиеуёэюяы]";
    private static final String LETTERS_REGEX = "\\w|[А-Яа-я]";

    @Override
    public void paragraphSort(TextComponent textComponent) throws HandlingException {
        logger.info("Method to sort paragraph called");
        if (textComponent == null || textComponent.getComponentType() != TextComponentType.TEXT) {
            throw new HandlingException("Introduced text component null or has incorrect type. " +
                    "Must be Text find: " + textComponent.getComponentType());
        }
        textComponent.getComponentList().sort((o1, o2) -> Integer.compare(o1.getSize(), o2.getSize()));
    }

    @Override
    public List<String> maxWordSentences(TextComponent textComponent) throws HandlingException {
        logger.info("Method to find sentences with word which has max length called");
        if (textComponent == null || textComponent.getComponentType() != TextComponentType.TEXT) {
            throw new HandlingException("Introduced text component null or has incorrect type. " +
                    "Must be Text find: " + textComponent.getComponentType());
        }
        List<String> tempList = new ArrayList<>();
        List<AbstractTextComponent> componentList = textComponent.getComponentList();
        int maxsize = 0;
        for (AbstractTextComponent component : componentList
                ) {
            TextComponent tempParagraphComponent = (TextComponent) component;
            List<AbstractTextComponent> sentencesList = tempParagraphComponent.getComponentList();
            for (AbstractTextComponent sentence : sentencesList
                    ) {
                TextComponent tempSentenceComponent = (TextComponent) sentence;
                List<AbstractTextComponent> lexemeList = tempSentenceComponent.getComponentList();
                for (AbstractTextComponent lexeme : lexemeList
                        ) {
                    if (lexeme.getComponentType() == TextComponentType.WORD) {
                        if (lexeme.getSize() == maxsize) {
                            tempList.add(sentence.operation());
                        }
                        if (lexeme.getSize() > maxsize) {
                            maxsize = lexeme.getSize();
                            tempList.removeAll(tempList);
                            tempList.add(sentence.operation());
                        }
                    }
                }
            }
        }
        return tempList;
    }

    @Override
    public void removeSentencesWithWordCountLessThan(TextComponent textComponent, int count) throws HandlingException {
        logger.info("Method to remove sentences with word count less than " + count);
        if (textComponent == null || textComponent.getComponentType() != TextComponentType.TEXT) {
            throw new HandlingException("Introduced text component null or has incorrect type. " +
                    "Must be Text find: " + textComponent.getComponentType());
        }
        List<String> tempList = new ArrayList<>();
        List<AbstractTextComponent> componentList = textComponent.getComponentList();
        for (AbstractTextComponent component : componentList
                ) {
            TextComponent tempParagraphComponent = (TextComponent) component;
            List<AbstractTextComponent> sentencesList = tempParagraphComponent.getComponentList();
            List<AbstractTextComponent> listForDeleteComponent = new ArrayList<>();
            for (AbstractTextComponent sentence : sentencesList
                    ) {
                if (sentence.getSize() < count) {
                    listForDeleteComponent.add(sentence);
                }
            }
            for (AbstractTextComponent deleteComponent : listForDeleteComponent
                    ) {
                tempParagraphComponent.remove(deleteComponent);
            }
        }
    }

    @Override
    public Map<String, Integer> findIdenticalWords(TextComponent textComponent) throws HandlingException {
        logger.info("Method to find identical words called");
        if (textComponent == null || textComponent.getComponentType() != TextComponentType.TEXT) {
            throw new HandlingException("Introduced text component null or has incorrect type. " +
                    "Must be Text find: " + textComponent.getComponentType());
        }
        List<AbstractTextComponent> componentList = textComponent.getComponentList();
        List<String> lexemesList = new ArrayList<>();
        Pattern pattern = Pattern.compile(WORD_REGEX);
        Matcher matcher;
        for (AbstractTextComponent component : componentList
                ) {
            TextComponent tempParagraphComponent = (TextComponent) component;
            List<AbstractTextComponent> tempSentencesList = tempParagraphComponent.getComponentList();
            for (AbstractTextComponent sentence : tempSentencesList
                    ) {
                TextComponent tempSentenceComponent = (TextComponent) sentence;
                List<AbstractTextComponent> tempLexemeList = tempSentenceComponent.getComponentList();
                for (AbstractTextComponent lexeme : tempLexemeList
                        ) {
                    String tempStringLexeme = lexeme.operation();
                    matcher = pattern.matcher(tempStringLexeme);
                    if (matcher.find()) {
                        lexemesList.add(matcher.group());
                    }
                }
            }
        }
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < lexemesList.size(); i++) {
            String word = lexemesList.get(i);
            if (!wordMap.containsKey(word)) {
                int count = 1;
                for (int j = i + 1; j < lexemesList.size(); j++) {
                    if (word.equalsIgnoreCase(lexemesList.get(j))) {
                        count++;
                    }
                }
                if (count > 1) {
                    wordMap.put(word, count);
                }
            }
        }
        return wordMap;
    }

    @Override
    public String numberOfLetters(TextComponent textComponent) throws HandlingException {
        logger.info("Method to find number of vowels and consonants letters called.");
        if (textComponent == null || textComponent.getComponentType() != TextComponentType.SENTENCE) {
            throw new HandlingException("Incorrect type. Must be: Sentence find: " + textComponent.getComponentType());
        }
        String sentence = textComponent.operation();
        int vowelsCount = findCountWithRegex(sentence, VOWEL_REGEX);
        int consonantsCount = findCountWithRegex(sentence, LETTERS_REGEX) - vowelsCount;
        String result = "number of vowels letters in sentence: " + vowelsCount +
                "\nnumber of consonants letters in sentence: " + consonantsCount;
        return result;
    }

    private int findCountWithRegex(String string, String regex) {
        int count = 0;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
