package blackjack;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// Represents a Deck (of cards)
// A deck typically has 52 cards but this is customizable based
//  from the two files cards.csv and suits.csv.
// Potentially break this into a parent or abstract class and then
//  have a `RegularDeck` child class that is made up of 52 cards
public class Deck {
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck(){
        this.reset();
    }

    // Clears the cards from the Deck and re-initializes them from the specified files
    private void reset(){
        this.cards.clear();

        try {
            String suits_path = "/Users/jasonperlman/IdeaProjects/BlackJackFX/src/blackjack/suits.csv";
            String numbers_path = "/Users/jasonperlman/IdeaProjects/BlackJackFX/src/blackjack/cards.csv";
            FutureTask<ArrayList<String[]>> future_suits = new FutureTask<ArrayList<String[]>>(new FileLoader(suits_path));
            FutureTask<ArrayList<String[]>> future_numbs = new FutureTask<ArrayList<String[]>>(new FileLoader(numbers_path));
            Thread t1 = new Thread(future_suits);
            Thread t2 = new Thread(future_numbs);
            t1.start();
            t2.start();
            ArrayList<String[]> suits = future_suits.get();
            ArrayList<String[]> cards = future_numbs.get();
            for (String[] suit_items: suits){
                String suit_short = suit_items[1];
                String suit_long = suit_items[2];
                for (String[] items: cards) {
                    ArrayList<Integer> act = new ArrayList<>();
                    for (String val: items[3].split("/") ){
                        act.add(Integer.parseInt(val));
                    }
                    this.cards.add(new Card(Integer.parseInt(items[0]), items[1], items[2], suit_short, suit_long, act));
                }
            }
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }


    }
    // Getters
    public ArrayList<Card> cards() {
        return this.cards;
    }

}
