package by.anton.handlingtask.parser;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.entity.TextComponentType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractTextParser {
    private static final String SENTENCE_REGEX = "[A-ZА-Я]((!=|\\w+\\.\\w+)|[^?!.(]|\\([^)]*)*[.?!]{1,3}";

    public SentenceParser() {
    }

    public SentenceParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public void parse(AbstractTextComponent abstractTextComponent, String string) {
        Pattern pattern = Pattern.compile(SENTENCE_REGEX);
        Matcher matcher = pattern.matcher(string);
        ArrayList<String> sentencesList = new ArrayList<>();
        while (matcher.find()) {
            sentencesList.add(matcher.group());
        }
        for (String sentence : sentencesList
                ) {
            TextComponent sentenceComponent = new TextComponent(TextComponentType.SENTENCE);
            abstractTextComponent.add(sentenceComponent);
            if (nextParser != null) {
                nextParser.parse(sentenceComponent, sentence);
            }
        }
    }
}
