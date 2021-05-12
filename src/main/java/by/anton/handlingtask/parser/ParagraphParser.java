package by.anton.handlingtask.parser;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.entity.TextComponentType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends AbstractTextParser {
    private static final String PARAGRAPH_REGEX = "[^\\n\\t]+";

    public ParagraphParser(){}
    public ParagraphParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public void parse(AbstractTextComponent abstractTextComponent, String string) {
        Pattern pattern = Pattern.compile(PARAGRAPH_REGEX);
        Matcher matcher = pattern.matcher(string);
        ArrayList<String> listParagraphs = new ArrayList<>();
        while (matcher.find()) {
            listParagraphs.add(matcher.group());
        }
        for (String paragraph : listParagraphs
                ) {
            TextComponent paragraphComponent = new TextComponent(TextComponentType.PARAGRAPH);
            abstractTextComponent.add(paragraphComponent);
            if (nextParser != null) {
                nextParser.parse(paragraphComponent, paragraph);
            }
        }
    }
}
