package test;

import blackjack.Card;
import blackjack.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    @Test
    public void deckSetupTest() {
        Deck d = new Deck();
        assertEquals(d.cards().size(), 52);
        assertEquals(d.cards().get(0) instanceof Card, true);
    }
}
