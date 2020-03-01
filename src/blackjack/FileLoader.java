package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class FileLoader implements Callable<ArrayList<String[]>> {
    private String filename;

    public FileLoader(String filename) {
        this.filename = filename;
    }

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