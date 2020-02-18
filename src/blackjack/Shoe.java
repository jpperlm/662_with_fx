package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

// Represents a Shoe (a blackjack term)
// A shoe is a shuffled set of decks, typically 1-8 decks
public class Shoe {
    Integer numDecks;
    Integer currentCard = 0;
    ArrayList<Deck> decks = new ArrayList<Deck>();
    ArrayList<Card> cards = new ArrayList<Card>();

    public Shoe( Integer decks ){
        this.numDecks = decks;
        this.resetDecks();
        this.shuffle();
    }

    // Setter
    public void setCards(ArrayList<Card> c) {
        this.cards = c;
    }

    // INTENT: Get the current count of the shoe
    // EXAMPLE: currentCard = 2. First two cards in Shoe are King, and Queen. Should return -2
    // PRECONDITIONS: this.cards is a non null ArrayList<Card> variable
    // POSTCONDITIONS: Returns the summed value of all played cards based on card.getCountValue()
    public int count() {
        return this.cards
                .subList(0,this.currentCard)
                .stream()
                .map(c->c.getCountValue())
                .reduce(0,(p,n)->p+n);
    }

    // If a shoe has less than half cards remaining, it is not playable
    public Boolean isPlayable() {
        return this.currentCard < this.cards.size() / 2;
    }
    //    Actions

    // Deals a card to the Seat passed thru the params
    public void dealCard(Seat s) {
        s.addCard(cards.get(this.currentCard));
        this.currentCard++;
    }

    public Card getLastCard() {
        return this.cards.get(this.currentCard-1);
    }

    // Shuffles the entire shoe (cards)
    private void shuffle() {
        Collections.shuffle(this.cards);
        this.currentCard = 0;
    }

    // INTENT: Reset the Shoe. A shoe is effectively a stack of decks (n decks in length)
    // EXAMPLE: If numDecks = 2, after this function is run cards will contain two full decks of cards (104 cards)
    // PRECONDITION: numDecks is set to a non null integer value
    private void resetDecks(){
        this.decks.clear();
        this.cards.clear();
        this.currentCard = 0;
        IntStream.range(0, this.numDecks).forEach(i -> this.decks.add(new Deck()));
        this.decks.stream().forEach(d-> this.cards.addAll(d.cards()));
    }
}
