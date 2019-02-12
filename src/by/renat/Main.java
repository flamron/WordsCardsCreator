package by.renat;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*try {
            WordsCardsCreator.parse("D:/Dropbox/Java/words.txt", "D:/Dropbox/Java/face.txt", "D:/Dropbox/Java/back.txt");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }*/
        //Card card = new MyCard();
        //card.writeToCard("test", Card.ALIGN.UP);

        Card card = new MyCard(10, 26);
        try {
            card.writeToCard("draft", Card.ALIGN.UP);
            card.writeToCard("Joe O’Neil provided initial drafts for the material", Card.ALIGN.DOWN);
            card.writeToCard("dræft", Card.ALIGN.CENTER);
        } catch (Exception e) {
            System.out.println("Error while adding text to card: " + e.getMessage());
        }
        System.out.println(card);
    }
}
