package by.anton.handlingtask.reader.impl;


import by.anton.handlingtask.exception.HandlingException;
import by.anton.handlingtask.reader.DataReader;
import by.anton.handlingtask.validator.impl.FileValidatorImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReaderImpl implements DataReader {

    @Override
    public String readFile(String path) throws HandlingException {
        FileValidatorImpl fileValidate = new FileValidatorImpl();
        if (!fileValidate.validateFilePath(path)) {
            throw new HandlingException("File is not exist or is empty or incorrect path");
        }
        ArrayList<String> lines;
        Path pathFile = Paths.get(path);
        try (Stream<String> lineStream = Files.lines(pathFile)) {
            lines = lineStream.collect(Collectors.toCollection(ArrayList::new));

        } catch (IOException e) {
            throw new HandlingException("Reading file is fail ", e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : lines
                ) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }
}
