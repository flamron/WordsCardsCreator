package by.renat;

import java.util.Arrays;

public class MyCard implements Card {

    private class Position {
        int start;
        int end;

        Position(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static final char HORIZ = '-';
    private static final char VERTIC = '|';

    private int height;
    private int width;
    private boolean usedLines[];
    private char lines[][];

    public MyCard(int height, int width) {
        this.height = height;
        this.width = width;
        usedLines = new boolean[height];
        lines = new char[height][width];
        for(char[] line : lines) {
            Arrays.fill(line, ' ');
        }
    }

    @Override
    public String toString() {
        String ls = System.lineSeparator();
        int len = ls.length();
        StringBuilder sb = new StringBuilder((height + 2) * (width + len + 2));

        char horizLine[] = new char[width + 2];
        Arrays.fill(horizLine, HORIZ);

        sb.append(horizLine).append(ls);
        for (char[] line : lines) {
            sb.append(VERTIC).append(line).append(VERTIC).append(ls);
        }
        sb.append(horizLine);
        return sb.toString();
    }

    @Override
    public String toStringWithoutBorders() {
        String ls = System.lineSeparator();
        int len = ls.length();
        StringBuilder sb = new StringBuilder((height + 2) * (width + len));

        for (char[] line : lines) {
            sb.append(line).append(ls);
        }
        sb.delete(sb.length() - len, sb.length());
        return sb.toString();
    }

    @Override
    public void writeToCard(String text, ALIGN align) throws Exception {
        Position p;
        switch (align) {
            case UP:
                p = getPositionAlignUp(text);
                break;
            case DOWN:
                p = getPositionAlignDown(text);
                break;
            case CENTER:
                p = getPositionAlignCenter(text);
                break;
            default:
                p = new Position(-1, -1);
        }
        if (p.start == -1 || p.end == -1)
            throw new Exception("Not enougth space for text in card");
        char[] chars = text.toCharArray();
        int row = p.start;
        int col = 0;
        for (int i = 0; i < chars.length; i++) {
            if (col == width) {
                row++;
                col = 0;
            }
            lines[row][col++] = chars[i];
            if (!usedLines[row]) usedLines[row] = true;
        }
        if (row != p.end)
            throw new Exception("Impossible situation! Something wrong with rows, please check");

    }

    private Position getPositionAlignUp(String text) {
        int lCount = getLinesCount(text);
        int start = -1;
        int end = -1;
        int i = 0;
        while (i < height) {
            if (usedLines[i]) {
                if (start != -1) start = -1;
            } else {
                if (start == -1) {
                    start = i;
                    if (lCount == 1) {
                        end = i;
                        break;
                    }
                } else {
                    if (i - start + 1 == lCount) {
                        end = i;
                        break;
                    }
                }
            }
            i++;
        }
        ;
        return new Position(start, end);
    }

    private Position getPositionAlignDown(String text) {
        int lCount = getLinesCount(text);
        int start = -1;
        int end = -1;
        int i = height - 1;
        while (i >= 0) {
            if (usedLines[i]) {
                if (end != -1) end = -1;
            } else {
                if (end == -1) {
                    end = i;
                    if (lCount == 1) {
                        start = i;
                        break;
                    }
                } else {
                    if (end - i + 1 == lCount) {
                        start = i;
                        break;
                    }
                }
            }
            i--;
        }
        return new Position(start, end);
    }

    private Position getPositionAlignCenter(String text) {
        int lCount = getLinesCount(text);
        int start = -1;
        int end = -1;
        int i;
        if (height % 2 == 0)
            i = height / 2 - 1;
        else
            i = height / 2;
        //if (lCount % 2 == 0)
            i += lCount / 2;
        //else
        //    i += lCount / 2;
        while (i >= 0) {
            if (usedLines[i]) {
                if (end != -1) {
                    end = -1;
                    break;
                }
            } else {
                if (end == -1) {
                    end = i;
                    if (lCount == 1) {
                        start = i;
                        break;
                    }
                } else {
                    if (end - i + 1 == lCount) {
                        start = i;
                        break;
                    }
                }
            }
            i--;
        }
        return new Position(start, end);
    }

    private int getLinesCount(String text) {
        int length = text.length();
        if (length % width == 0) {
            return length / width;
        } else {
            return length / width + 1;
        }
    }
}
