package test;

import blackjack.FileLoader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileLoaderTest {
    @Test
    public void test() {
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
            assertEquals(4, suits.size());
            assertEquals(13, cards.size());
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
