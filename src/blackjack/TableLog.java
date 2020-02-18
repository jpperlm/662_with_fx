package blackjack;

import java.io.Serializable;

// INTENT: Track certain statistics about the Black Jack Table including the
//      number of times the table has been started as well as the number of hands dealt
public class TableLog implements Serializable {

    Integer table_start_count = 0;
    Integer hands_dealt_count = 0;

    public TableLog(){}

    public void incrementStart() {
        this.table_start_count++;
    }

    public void incrementHandsDealt() {
        this.hands_dealt_count++;
    }

    public Integer getStartCount() {
        return this.table_start_count;
    }

}
