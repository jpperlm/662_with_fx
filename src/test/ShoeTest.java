package test;

import blackjack.Card;
import blackjack.Seat;
import blackjack.Shoe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoeTest {
    @Test
    public void testCountWorks() {
        Card c_1 = new Card(1, "A", "Ace", "H", "Hearts", 1);
        Card c_2 = new Card(7, "7", "Seven", "H", "Hearts", 7);
        Card c_3 = new Card(2, "2", "Two", "H", "Hearts", 2);
        Seat s = new Seat();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(c_1);
        cards.add(c_1);
        cards.add(c_1);
        cards.add(c_2);
        cards.add(c_2);
        cards.add(c_3);
        cards.add(c_3);
        cards.add(c_1);

        Shoe shoe = new Shoe(3);
        shoe.setCards(cards);
        assertEquals(shoe.count(), 0);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -1);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -2);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -3);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -3);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -3);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -2);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -1);
        shoe.dealCard(s);
        assertEquals(shoe.count(), -2);
    }

}
