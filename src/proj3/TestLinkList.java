package proj3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestLinkList {

    // These are named gxx, where xx is the day of the month of
    // December in 2020 (because it's easier to make tests this way)
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    GregorianCalendar g3 = new GregorianCalendar();
    GregorianCalendar g5 = new GregorianCalendar();
    GregorianCalendar g10 = new GregorianCalendar();
    GregorianCalendar g15 = new GregorianCalendar();
    GregorianCalendar g20 = new GregorianCalendar();

    @Before
    public void dateSetup() {
        try {
            // Names are just based on the Greg Calendar that they
            // are used for like above
            Date d3 = df.parse("12/3/2020");
            g3.setTime(d3);
            Date d5 = df.parse("12/5/2020");
            g5.setTime(d5);
            Date d10 = df.parse("12/10/2020");
            g10.setTime(d10);
            Date d15 = df.parse("12/15/2020");
            g15.setTime(d15);
            Date d20 = df.parse("12/20/2020");
            g20.setTime(d20);
        } catch (Exception e) {
            System.out.println("Get wrecked nerd it didn't work, LOL!");
        }
    }

    @Test
    public void addingGamesTests() {
        MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();

        Console console1 = new Console("Console1", g3, g10, null, ConsoleTypes.PlayStation4);
        Game game2 = new Game("Game2", g3, g10, null, "title2",
                new Console("Game2", g3, g10, null, ConsoleTypes.PlayStation4));
        Game game3 = new Game("Game3", g3, g5, null, "title3",
                new Console("Game3", g3, g5, null, ConsoleTypes.PlayStation4));
        Game game4 = new Game("Game4", g3, g5, null, "title4",
                new Console("Game4", g3, g5, null, ConsoleTypes.PlayStation4));
        Game game5 = new Game("Game5", g3, g5, null, "title5",
                new Console("Game5", g3, g5, null, ConsoleTypes.PlayStation4));
        Game game6 = new Game("Game6", g3, g15, null, "title5",
                new Console("Game6", g3, g15, null, ConsoleTypes.PlayStation4));

        list.add(console1);
        list.add(game2);
        list.add(game3);
        list.add(game4);
        list.add(game5);
        list.add(game6);

        // Correct order based on the above rentals added would be:
        // Game/Console (g/c): g3, g4, g5, g2, g6, c1
        assertEquals("Game3", list.get(0).getNameOfRenter());
        assertEquals("Game4", list.get(1).getNameOfRenter());
        assertEquals("Game5", list.get(2).getNameOfRenter());
        assertEquals("Game2", list.get(3).getNameOfRenter());
        assertEquals("Game6", list.get(4).getNameOfRenter());
        assertEquals("Console1", list.get(5).getNameOfRenter());
    }

    @Test
    public void addingConsolesTests() {
        MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();

        Console console1 = new Console("Console1", g3, g10, null, ConsoleTypes.PlayStation4);
        Console console2 = new Console("Console2", g3, g5, null, ConsoleTypes.PlayStation4);
        Console console0 = new Console("Console0", g3, g5, null, ConsoleTypes.PlayStation4);
        Game game1 = new Game("Game1", g3, g10, null, "title2",
                new Console("Game1", g3, g10, null, ConsoleTypes.PlayStation4));
        Game game2 = new Game("Game2", g3, g5, null, "title3",
                new Console("Game2", g3, g5, null, ConsoleTypes.PlayStation4));
        Console console3 = new Console("Console3", g3, g15, null, ConsoleTypes.PlayStation4);
        Console console5 = new Console("Console5", g3, g15, null, ConsoleTypes.PlayStation4);
        Console console4 = new Console("Console4", g3, g15, null, ConsoleTypes.PlayStation4);

        list.add(console1);
        list.add(console2);
        list.add(console0);
        list.add(game1);
        list.add(game2);
        list.add(console3);
        list.add(console5);
        list.add(console4);

        // Correct order based on the above rentals added would be:
        // Game/Console (g/c): g2, g1, c2, c1, c3, c4, c5
        assertEquals("Game2", list.get(0).getNameOfRenter());
        assertEquals("Game1", list.get(1).getNameOfRenter());
        assertEquals("Console0", list.get(2).getNameOfRenter());
        assertEquals("Console2", list.get(3).getNameOfRenter());
        assertEquals("Console1", list.get(4).getNameOfRenter());
        assertEquals("Console3", list.get(5).getNameOfRenter());
        assertEquals("Console4", list.get(6).getNameOfRenter());
        assertEquals("Console5", list.get(7).getNameOfRenter());
    }

}
