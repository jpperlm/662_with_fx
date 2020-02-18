package blackjack;

// Custom Exception for when a player tries to occupy too many seats
public class MaxSeatExeception extends Exception {
    int max;
    MaxSeatExeception(int num) {
        max = num;
    }
    public String toString() {
        return ("MaxSeatException: Players can only occupy a maximum of " + this.max + " seats");
    }
}
