package by.renat;

public interface Card {
    enum ALIGN {
        UP, DOWN, CENTER
    }
    void writeToCard(String text, Card.ALIGN align) throws Exception;
    public String toStringWithoutBorders();
}
