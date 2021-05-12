package by.anton.handlingtask.parser;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.SymbolLeaf;

public class WordParser extends AbstractTextParser {
    public WordParser() {
    }

    public WordParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public void parse(AbstractTextComponent abstractTextComponent, String string) {
        char[] symbols = string.toCharArray();
        for (char s : symbols
                ) {
            SymbolLeaf symbol = new SymbolLeaf(s);
            abstractTextComponent.add(symbol);
        }
    }
}
