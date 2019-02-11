package by.renat;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            WordsCardsCreator.parse("D:\\Dropbox\\Java\\words.txt");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
}
