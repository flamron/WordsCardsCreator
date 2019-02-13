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
    private int cardsInRow;
    private char linesFace[][];
    private char linesBack[][];

    private static final char FACE_HORIZ_BORDER = '-';
    private static final char FACE_VERTIC_BORDER = '|';
    private static final char BACK_HORIZ_BORDER = '.';
    private static final char BACK_VERTIC_BORDER = '.';
    private boolean leftBorderSpace = true;
    private boolean rightBorderSpace = true;
    private boolean topBorderSpace = true;
    private boolean bottomBorderSpace = true;
    private char[] endLineFace;
    private char[] endLineBack;
    private int cardsInColumn = 3;

    public WordsCardsCreator(int height, int width, int cardsInRow, boolean leftBorderSpace, boolean rightBorderSpace, boolean topBorderSpace, boolean bottomBorderSpace) throws Exception {
        this.height = height;
        this.width = width;
        this.cardsInRow = cardsInRow;
        if (cardsInRow > 20)
            throw new Exception("Too much cards in line. May be error. Must be less or equal then 20");

        this.leftBorderSpace = leftBorderSpace;
        this.rightBorderSpace = rightBorderSpace;
        this.topBorderSpace = topBorderSpace;
        this.bottomBorderSpace = bottomBorderSpace;

        int borderAddedSpaceHeight = 0;
        if (topBorderSpace) borderAddedSpaceHeight++;
        if (bottomBorderSpace) borderAddedSpaceHeight++;
        int heightWithBorders = height + 1 + borderAddedSpaceHeight;

        int borderAddedSpaceWidth = 0;
        if (leftBorderSpace) borderAddedSpaceWidth++;
        if (rightBorderSpace) borderAddedSpaceWidth++;
        int widthWithBorders = width* cardsInRow + 2 + (cardsInRow - 1) + borderAddedSpaceWidth* cardsInRow;

        linesFace = new char[heightWithBorders][widthWithBorders];
        linesBack = new char[heightWithBorders][widthWithBorders];

        endLineFace = new char[widthWithBorders];
        endLineBack = new char[widthWithBorders];
        Arrays.fill(endLineFace, FACE_HORIZ_BORDER);
        Arrays.fill(endLineBack, BACK_HORIZ_BORDER);
    }

    public void parse(String inputFileName, String outFaceFileName, String outBackFileName) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outFaceFileName));
             /*BufferedWriter bwBack = new BufferedWriter(new FileWriter(outBackFileName))*/) {

            Pattern pattern = Pattern.compile("([a-zA-Z -]+) ([^а-яА-ЯёЁ]+|=) ([а-яА-ЯёЁ,;().+ ]+)([a-zA-Z,.;:\\[\\]’ ()-]*)");
            String line = br.readLine();
            int counter = 1;
            int cardsLineCounter = 0;

            TwoSideCard cards[][] = new TwoSideCard[cardsInColumn][cardsInRow];
            int posI = 0;
            int posJ = 0;
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
                                tcard = buildCard(engWord.toLowerCase(), transcription, translate, example);
                            } catch (Exception e) {
                                throw new Exception("Error while create card with line " + counter + ": " + e.getMessage());
                            }
                            if (posJ == cardsInRow) {
                                posJ = 0;
                                posI++;
                            }
                            if (posI == cardsInColumn) {
                                writeCardsToFile(bw, cards);
                                posI = 0;
                                posJ = 0;
                            }
                            cards[posI][posJ++] = tcard;


                            /*if (cardsLineCounter < cardsInRow) {
                                addCardToLines(tcard, cardsLineCounter);
                            } else {
                                writeLinesToFiles(bw, bwBack);
                                cardsLineCounter = 0;
                                addCardToLines(tcard, cardsLineCounter);
                            }
                            cardsLineCounter++;*/
                        }
                    } else {
                        System.out.println(counter + ": Строка не парсится.");
                    }
                }
                counter++;
                line = br.readLine();
            }
            /*bw.write(endLineFace);
            bw.write(System.lineSeparator());*/
        }
    }

    private void writeCardsToFile(BufferedWriter bw, TwoSideCard cards[][]) throws IOException {
        for (int i = 0; i < cardsInColumn; i++) {
            for (int j = 0; j < cardsInRow; j++) {
                fillLinesFromCard(linesFace, cards[i][j].face, j, FACE_VERTIC_BORDER, FACE_HORIZ_BORDER);
            }
            writeLinesToFile(linesFace, bw);
        }
        bw.write(endLineFace);
        bw.write(System.lineSeparator());

        for (int i = 0; i < cardsInColumn; i++) {
            for (int j = 0; j < cardsInRow; j++) {
                fillLinesFromCard(linesBack, cards[i][j].back, cardsInRow - 1 - j, BACK_VERTIC_BORDER, BACK_HORIZ_BORDER);
            }
            writeLinesToFile(linesBack, bw);
        }
        bw.write(endLineBack);
        bw.write(System.lineSeparator());
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

    /*private void addCardFaceToLines(TwoSideCard tcard, int cardPosition) {
        fillLinesFromCard(linesFace, tcard.face, cardPosition, FACE_VERTIC_BORDER, FACE_HORIZ_BORDER);
    }

    private void addCardBackToLines(TwoSideCard tcard, int cardPosition) {
        fillLinesFromCard(linesBack, tcard.back, cardsInRow - 1 - cardPosition, '.', '.');
    }*/

    private void fillLinesFromCard(char[][] lines, Card card, int cardPos, char vertic, char horiz) {
        int borderAddedSpaceWidth = 0;
        if (leftBorderSpace) borderAddedSpaceWidth++;
        if (rightBorderSpace) borderAddedSpaceWidth++;

        int startColumn = cardPos * width + cardPos + cardPos * borderAddedSpaceWidth;
        int endColumn = (cardPos + 1) * width + (cardPos + 1) + (cardPos + 1) * borderAddedSpaceWidth;

        int topBorderHeight = 1;
        char[][] clines = card.getLines();

        Arrays.fill(lines[0], startColumn, endColumn + 1, horiz);
        if (topBorderSpace) {
            Arrays.fill(lines[1], startColumn + 1, endColumn, ' ');
            lines[1][startColumn] = vertic;
            lines[1][endColumn] = vertic;
            topBorderHeight = 2;
        }

        for (int i = 0; i < clines.length; i++) {
            lines[topBorderHeight + i][startColumn] = vertic;
            int leftBorderWidth = 1;
            if (leftBorderSpace) {
                lines[topBorderHeight + i][startColumn + 1] = ' ';
                leftBorderWidth = 2;
            }
            System.arraycopy(clines[i], 0, lines[i + topBorderHeight], startColumn + leftBorderWidth, clines[i].length);
            if (rightBorderSpace) {
                lines[topBorderHeight + i][endColumn - 1] = ' ';
            }
            lines[topBorderHeight + i][endColumn] = vertic;
        }

        if (bottomBorderSpace) {
            Arrays.fill(lines[lines.length - 1], startColumn + 1, endColumn, ' ');
            lines[lines.length - 1][startColumn] = vertic;
            lines[lines.length - 1][endColumn] = vertic;
        }
    }

    private void writeLinesToFile(char[][] lines, BufferedWriter bw) throws IOException {
        for (char[] chars : lines) {
            bw.write(chars);
            bw.write(System.lineSeparator());
        }
    }


}























