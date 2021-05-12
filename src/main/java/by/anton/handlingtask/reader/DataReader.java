package by.anton.handlingtask.reader;


import by.anton.handlingtask.exception.HandlingException;


public interface DataReader {
    public String readFile(String path) throws HandlingException;

}
