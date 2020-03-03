package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;


// Used To Load CSV Files into Arrays
// Implements Callable making this able to be used with threads
public class FileLoader implements Callable<ArrayList<String[]>> {
    // name of the file to open
    private String filename;

    public FileLoader(String filename) {
        this.filename = filename;
    }

    // Runs the block
    // Opens the file stored in filename
    // Reads line by line parsing each line into an ArrayList<String[]>
    // Returns the ArrayList<String[]>
    public ArrayList<String[]> call() {
        ArrayList<String[]> arr = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File(this.filename));
            while (scan.hasNextLine()) {
                arr.add(scan.nextLine().split(","));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arr;
    }
}