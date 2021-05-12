package by.anton.handlingtask.parser;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.SymbolLeaf;
import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.entity.TextComponentType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends AbstractTextParser {
    private static final String LEXEME_REGEX = "\\s+";
    private static final String WORD_REGEX = "[А-Яа-я\\w]{2,}";

    public LexemeParser() {
    }

    public LexemeParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public void parse(AbstractTextComponent abstractTextComponent, String string) {
        String[] lexemes = string.split(LEXEME_REGEX);
        Pattern wordPattern = Pattern.compile(WORD_REGEX);
        for (String lexeme : lexemes
                ) {
            AbstractTextComponent lexemeComponent;
            Matcher wordMatcher= wordPattern.matcher(lexeme);
            if (nextParser != null && wordMatcher.find()) {
                lexemeComponent = new TextComponent(TextComponentType.WORD);
                nextParser.parse(lexemeComponent, lexeme);
            } else {
                char symbol = lexeme.charAt(0);
                lexemeComponent = new SymbolLeaf(symbol);
            }
            abstractTextComponent.add(lexemeComponent);
        }
    }
}
