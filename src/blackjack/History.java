package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Intent: Create a `Logger`, a helper class that can log certain events to a parent object
// Example: A History class is created with the base of BlackJackTable. When the game is played
//            Log lines can be written such as "Game started", "Hands dealt", "Player wins"
// Precondition 1; This Generic Requires to Classes in order to be used.
public class History<T, S> {
    T base;
    ArrayList<S> items = new ArrayList<>();

    public void add (T t) {
        this.base = t;
    }

    // Intent: Adds an item to the items array and then saves the log file
    // Example: String "Player wins" is passed into addItem. This gets saved into the log file
    public void addItem (S s) {
        this.items.add(s);
        this.save(s);
    }

    public ArrayList<S> items() {
        return this.items;
    }

    // Saves the argument as a log line
    public void save(S s) {
        try {
            File file = new File("/Users/jasonperlman/IdeaProjects/Perlman_BlackJack_Tutor/src/blackjack/history.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write( this.base + "::" + s.toString() + "\n");
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
