package by.renat;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) {
        WordsCardsCreator wcc = null;
        try {
            wcc = new WordsCardsCreator(13, 27, 2, true, true, true, false);
        } catch (Exception e) {
            System.out.println("Error while created WordCardsCreator, message: " + e.getMessage());
            return;
        }
        String input, output;
        if (args.length > 0)
            input = args[0];
        else
            input = "D:/Dropbox/Java/words.txt";
        if (args.length > 1)
            output = args[1];
        else
            output = "D:/Dropbox/Java/face.txt";
        try {
            wcc.parse(input, output);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка создания карточки: " + e.getMessage());
        }
    }
}
