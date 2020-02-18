package test;

import blackjack.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Most functions in this class are getters and don't need tests.
// The major important function is the getCountValue and I am testing
//  the different possible count values
class CardTest {
    @Test
    public void getCountValueTest() {
        Card c_1 = new Card(5, "5", "Five", "H", "Hearts",5);
        Card c_2 = new Card(7, "7", "Seven", "H", "Hearts", 7);
        Card c_3 = new Card(12, "12", "Queen", "H", "Hearts", 10);

        assertEquals(c_1.getCountValue(), 1);
        assertEquals(c_2.getCountValue(), 0);
        assertEquals(c_3.getCountValue(), -1);
    }
    @Test
    public void faceValueTest() {
        Card c_1 = new Card(1, "A", "Ace", "H", "Hearts", 1);
        Card c_2 = new Card(7, "7", "Seven", "H", "Hearts", 7);
        Card c_3 = new Card(12, "Q", "Queen", "H", "Hearts", 10);

        assertEquals(c_1.faceValue(), "A");
        assertEquals(c_2.faceValue(), "7");
        assertEquals(c_3.faceValue(), "Q");
    }

    @Test
    public void fullSuit() {
        Card c_1 = new Card(1, "A", "Ace", "H", "Hearts", 1);
        assertEquals(c_1.fullSuit(), "Hearts");
    }


}
