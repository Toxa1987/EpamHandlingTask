package by.anton.handlingtask.entity;

public enum TextComponentType {
    TEXT("\n"),
    PARAGRAPH(" "),
    SENTENCE(" "),
    WORD(""),
    SYMBOL("");

    private final String delimiter;

    TextComponentType(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
