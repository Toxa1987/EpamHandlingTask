package by.anton.handlingtask.service;

import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.exception.HandlingException;

import java.util.List;
import java.util.Map;

public interface TextCompositeService {

    public void paragraphSort(TextComponent textComponent) throws HandlingException;

    public List<String> maxWordSentences(TextComponent textComponent) throws HandlingException;

    public void removeSentencesWithWordCountLessThan(TextComponent textComponent, int count) throws HandlingException;

    public Map<String, Integer> findIdenticalWords(TextComponent textComponent) throws HandlingException;

    public String numberOfLetters(TextComponent textComponent) throws HandlingException;
}
