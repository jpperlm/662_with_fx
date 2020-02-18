package test;

import blackjack.BlackJackTable;
import blackjack.History;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryTest {
    @Test
    public void historyTest() {
        BlackJackTable b = new BlackJackTable(1,3, 4);
        History<BlackJackTable, String> h = new History<>();
        h.add(b);
        h.addItem("ITEM ADDED");
        assertEquals(h.items().size(), 1);
    }
}
