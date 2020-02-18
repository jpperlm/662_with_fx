package test;

import blackjack.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    @Test
    public void makeBetTest() {
        Player p = new Player("test", 100.0);
        assertEquals("test", p.getName());
    }
}
