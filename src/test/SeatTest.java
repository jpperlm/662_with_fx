package test;

import blackjack.Player;
import blackjack.Seat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatTest {
    @Test
    public void addAndRemovePlayer() {
        Player p = new Player("test", 100.0);
        Seat s = new Seat();
        s.addPlayer(p);
        assertEquals(s.getPlayer(), "test");
        s.removePlayer();
        assertEquals(s.getPlayer(), "Empty");

    }
}
