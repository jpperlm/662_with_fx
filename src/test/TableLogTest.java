package test;

import blackjack.TableLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Most functions in this class are getters and don't need tests.
// The major important function is the getCountValue and I am testing
//  the different possible count values
class TableLogTest {
    @Test
    public void testTableLog() {
        TableLog tl = new TableLog();
        tl.incrementStart();
        assertEquals(1, tl.getStartCount());
        tl.incrementStart();
        assertEquals(2, tl.getStartCount());
    }
}
