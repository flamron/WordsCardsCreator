package by.renat;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyCardTest {

    @Test
    public void CardWriteToUp() {
        Card c = new MyCard(10, 20);
        try {
            c.writeToCard("Renat tests this class", Card.ALIGN.UP);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        String str = c.toStringWithoutBorders();
        String expected = String.format(
                "Renat tests this cla%n" +
                "ss                  %n" +
                "                    %n" +
                "                    %n" +
                "                    %n" +
                "                    %n" +
                "                    %n" +
                "                    %n" +
                "                    %n" +
                "                    ");
        if (!str.equals(expected)) {
            System.out.println("Expected string:");
            System.out.println(expected);
            System.out.println("Received string:");
            System.out.println(str);
            fail();
        }
    }

    @Test
    public void CardWriteToDown() {
        Card c = new MyCard(10, 20);
        try {
            c.writeToCard("Renat tests this class", Card.ALIGN.DOWN);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        String str = c.toStringWithoutBorders();
        String expected = String.format(
                "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "Renat tests this cla%n" +
                        "ss                  ");
        if (!str.equals(expected)) {
            System.out.println("Expected string:");
            System.out.println(expected);
            System.out.println("Received string:");
            System.out.println(str);
            fail();
        }
    }

    @Test
    public void CardWriteToCenter() {
        Card c = new MyCard(10, 20);
        try {
            c.writeToCard("Renat tests this class", Card.ALIGN.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        String str = c.toStringWithoutBorders();
        String expected = String.format(
                "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "Renat tests this cla%n" +
                        "ss                  %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    ");
        if (!str.equals(expected)) {
            System.out.println("Expected string:");
            System.out.println(expected);
            System.out.println("Received string:");
            System.out.println(str);
            fail();
        }
    }

    @Test
    public void CardWriteCombine() {
        Card c = new MyCard(10, 20);
        try {
            c.writeToCard("Super english word", Card.ALIGN.UP);
            c.writeToCard("Just transcription", Card.ALIGN.CENTER);
            c.writeToCard("Just two line exmaple of using", Card.ALIGN.DOWN);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        String str = c.toStringWithoutBorders();
        String expected = String.format(
                "Super english word  %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "Just transcription  %n" +
                        "                    %n" +
                        "                    %n" +
                        "                    %n" +
                        "Just two line exmapl%n" +
                        "e of using          ");
        if (!str.equals(expected)) {
            System.out.println("Expected string:");
            System.out.println(expected);
            System.out.println("Received string:");
            System.out.println(str);
            fail();
        }
        try {
            c.writeToCard("small add up", Card.ALIGN.UP);
            c.writeToCard("new transcription", Card.ALIGN.CENTER);
            c.writeToCard("new two lines super example", Card.ALIGN.DOWN);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        str = c.toStringWithoutBorders();
        expected = String.format(
                "Super english word  %n" +
                        "small add up        %n" +
                        "                    %n" +
                        "new transcription   %n" +
                        "Just transcription  %n" +
                        "                    %n" +
                        "new two lines super %n" +
                        "example             %n" +
                        "Just two line exmapl%n" +
                        "e of using          ");
        if (!str.equals(expected)) {
            System.out.println("Expected string:");
            System.out.println(expected);
            System.out.println("Received string:");
            System.out.println(str);
            fail();
        }
    }
}