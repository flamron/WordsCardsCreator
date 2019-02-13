package by.renat;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) {
        WordsCardsCreator wcc = null;
        try {
            wcc = new WordsCardsCreator(15, 30, 4);
        } catch (Exception e) {
            System.out.println("Error while created WordCardsCreator, message: " + e.getMessage());
            return;
        }
        try {
            wcc.parse("D:/Dropbox/Java/words.txt", "D:/Dropbox/Java/face.txt", "D:/Dropbox/Java/back.txt");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка создания карточки: " + e.getMessage());
        }
    }
}
