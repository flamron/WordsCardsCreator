package by.renat;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class WordsCardsCreator {

    static final int CARD_WIDTH = 40; // in symbols
    static final int CARD_HEIGHT = 10; // in symbols
    static final char VERTICAL_BORDER = '|';
    static final char HORIZONTAL_BORDER = '-';
    static final int CARDS_IN_ROW = 3;

    public static void parse(String inputFileName, String outFaceFileName, String outBackFileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter bwFace = new BufferedWriter(new FileWriter(outFaceFileName));
             BufferedWriter bwBack = new BufferedWriter(new FileWriter(outBackFileName))) {

            Pattern pattern = Pattern.compile("([a-zA-Z -]+) ([^а-яА-ЯёЁ]+|=) ([а-яА-ЯёЁ,;().+ ]+)([a-zA-Z,.;:\\[\\]’ ()-]*)");
            String line = br.readLine();
            int counter = 1;


            while (line != null) {
                if (line.length() == 0) {
                    System.out.println(counter + ": Пустая строка.");
                } else {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String all = matcher.group(0);
                        String engWord = matcher.group(1);
                        String transcription = matcher.group(2);
                        String translate = matcher.group(3);
                        String example = matcher.group(4);
                        if (all.length() != line.length()) {
                            System.out.println("ВНИМАНИЕ! Не вся строка спарсилась !");
                            System.out.println(counter + ": " + engWord);
                            System.out.println(transcription);
                            System.out.println(translate);
                            System.out.println(example);
                        } else {
                            
                        }
                    } else {
                        System.out.println(counter + ": Строка не парсится.");
                    }
                }
                counter++;
                line = br.readLine();
            }
        }
    }

}
