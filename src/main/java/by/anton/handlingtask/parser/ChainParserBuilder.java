package by.anton.handlingtask.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ChainParserBuilder {
    private static final Logger logger = LogManager.getLogger();
    private List<AbstractTextParser> parsers = new ArrayList<>();

    public ChainParserBuilder() {
    }

    public ChainParserBuilder setParser(AbstractTextParser abstractTextParser) {
        parsers.add(abstractTextParser);
        return this;
    }

    public AbstractTextParser build() {
        logger.info("building parser dependency.");
        for (int i = 1; i < parsers.size(); i++) {
            AbstractTextParser parser = parsers.get(i - 1);
            parser.setNextParser(parsers.get(i));
        }
        return parsers.get(0);
    }
}
