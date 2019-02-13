package by.renat;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class WordsCardsCreator {
    private class TwoSideCard {
        Card face;
        Card back;

        TwoSideCard(Card face, Card back) {
            this.face = face;
            this.back = back;
        }
    }

    private int height;
    private int width;
    private int cardsInLine;
    private char linesFace[][];
    private char linesBack[][];

    private static final char HORIZ = '-';
    private static final char VERTIC = '|';
    private char[] horizLine;

    public WordsCardsCreator(int height, int width, int cardsInLine) throws Exception {
        this.height = height;
        this.width = width;
        this.cardsInLine = cardsInLine;
        if (cardsInLine > 20)
            throw new Exception("Too much cards in line. May be error. Must be less or equal then 20");
        // +2 - this is for two borders; (cardsInLine - 1) - this is for borders between cards in horizontal orientation
        int widthWithBorders = width*cardsInLine + 2 + (cardsInLine - 1);
        linesFace = new char[height + 1][widthWithBorders];
        linesBack = new char[height + 1][widthWithBorders];
        horizLine = new char[widthWithBorders];
        Arrays.fill(horizLine, HORIZ);
    }

    public void parse(String inputFileName, String outFaceFileName, String outBackFileName) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter bwFace = new BufferedWriter(new FileWriter(outFaceFileName));
             BufferedWriter bwBack = new BufferedWriter(new FileWriter(outBackFileName))) {

            Pattern pattern = Pattern.compile("([a-zA-Z -]+) ([^а-яА-ЯёЁ]+|=) ([а-яА-ЯёЁ,;().+ ]+)([a-zA-Z,.;:\\[\\]’ ()-]*)");
            String line = br.readLine();
            int counter = 1;
            int cardsLineCounter = 0;

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
                            TwoSideCard tcard = null;
                            try {
                                tcard = buildCard(engWord, transcription, translate, example);
                            } catch (Exception e) {
                                throw new Exception("Error while create card with line " + counter + ": " + e.getMessage());
                            }
                            if (cardsLineCounter < cardsInLine) {
                                addCardToLines(tcard, cardsLineCounter);
                                cardsLineCounter++;
                            } else {
                                writeLinesToFiles(bwFace, bwBack);
                                cardsLineCounter = 0;
                            }
                            /*System.out.println(tcard.face);
                            System.out.println(tcard.back);*/
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

    private TwoSideCard buildCard(String engWord, String transcription, String translete, String example) throws Exception {
        Card face = new MyCard(height, width);
        face.writeToCard(engWord, Card.ALIGN.UP);
        face.writeToCard(example, Card.ALIGN.DOWN);
        face.writeToCard(transcription, Card.ALIGN.CENTER);
        Card back = new MyCard(height, width);
        back.writeToCard(" ", Card.ALIGN.DOWN);
        back.writeToCard(translete, Card.ALIGN.DOWN);
        return new TwoSideCard(face, back);
    }

    private void addCardToLines(TwoSideCard tcard, int cardPosition) {
        /*int jPos = cardPosition*width;
        int jNextPos = (cardPosition + 1)*width;*/
        fillLinesFromCard(linesFace, tcard.face, cardPosition);
        fillLinesFromCard(linesBack, tcard.back, cardPosition);
    }

    private void fillLinesFromCard(char[][] lines, Card card, int cardPos) {
        int startColumn = cardPos * width + cardPos;
        int endColumn = (cardPos + 1) * width + cardPos + 1;
        char[][] clines = card.getLines();
        Arrays.fill(lines[0], startColumn, endColumn + 1, HORIZ);
        for (int i = 0; i < clines.length; i++) {
            lines[i + 1][startColumn] = VERTIC;
            System.arraycopy(clines[i], 0, lines[i + 1], startColumn + 1, clines[i].length);
            lines[i + 1][endColumn] = VERTIC;
        }
    }

    private void writeLinesToFiles(BufferedWriter bwFace, BufferedWriter bwBack) throws IOException {
        for (char[] chars : linesFace) {
            bwFace.write(chars);
            bwFace.write(System.lineSeparator());
        }
        for (char[] chars : linesBack) {
            bwBack.write(chars);
            bwBack.write(System.lineSeparator());
        }

    }


}























