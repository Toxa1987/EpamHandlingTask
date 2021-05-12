package by.anton.handlingtask;

import by.anton.handlingtask.entity.AbstractTextComponent;
import by.anton.handlingtask.entity.TextComponent;
import by.anton.handlingtask.entity.TextComponentType;
import by.anton.handlingtask.exception.HandlingException;
import by.anton.handlingtask.parser.*;
import by.anton.handlingtask.reader.DataReader;
import by.anton.handlingtask.reader.impl.DataReaderImpl;
import by.anton.handlingtask.service.impl.TextCompositeServiceImpl;

import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DataReader dataReader = new DataReaderImpl();
        try {
            String s = dataReader.readFile("./src/main/resources/data/textdata.txt");
            System.out.println(s);
            System.out.println("====================================================================================");

            TextComponent textComponent = new TextComponent(TextComponentType.TEXT);
            AbstractTextParser Parser = new ChainParserBuilder()
                    .setParser(new ParagraphParser())
                    .setParser(new SentenceParser())
                    .setParser(new LexemeParser())
                    .setParser(new WordParser())
                    .build();
            Parser.parse(textComponent, s);
            TextCompositeServiceImpl service = new TextCompositeServiceImpl();
            List<AbstractTextComponent> list = textComponent.getComponentList();
            TextComponent  textComponent1 = (TextComponent) list.get(0);
            String ss = service.numberOfLetters((TextComponent)textComponent1.getComponentList().get(0));
            System.out.println(ss);

        } catch (HandlingException e) {
            e.printStackTrace();
        }

    }
}
