package blackjack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a single Black Jack Table
// Has the ability to interact with a user as a real table would
// Players can sit at the table, hands will be dealt and BJ rules apply
public class BlackJackTable extends Table implements Visualize {
    Integer numDecks = 1; // Default num of Decks per shoe
    Integer minSeats = 1; // Minimum num seats to start a game
    Integer maxSeats = 3; // Max seats available at the table
    Seat dealer;
    Shoe shoe;
    ArrayList<Seat> seats = new ArrayList<>();
    History<BlackJackTable, String> seat_history = new History<>();
    TableLog log;


    public BlackJackTable(Integer minSeats, Integer maxSeats, Integer numDecks) {
        this.maxSeats = maxSeats;
        this.minSeats = minSeats;
        this.numDecks = numDecks;
        this.dealer = new Seat();
        this.dealer.makeDealer();
        seat_history.add(this);
        this.setupSeats();
        this.getTableLog();
    }

    // Runs the table, all other logic should be hidden from runner
    // For now this is going to accept an Array list of players, these will be the potential players for the game.
    public void loop(ArrayList<Player> potential_players) {
        this.log.incrementStart();
        this.saveLog();
        this.printAnnouncement("Starting Game # " + this.log.getStartCount() + " for this table.");
        this.pre_game_prep(potential_players);
        this.play_game();
    }

    // Setting up table for game, the game is not started at this point.
    private void pre_game_prep(ArrayList<Player> potential_players) {
        this.printStatus("Table Setup");
        this.seatChangeLoop(potential_players);
    }
    // Loops through the actual display and game logic
    private void play_game() {
        this.printStatus("Game Start");
        this.displaySeats();
        this.waitInput();
        this.play();
    }
    // Output results and any additional information
    private void post_game() {

    }

    @Override
    public String toString() {
        return "Black Jack Table";
    }

    // Private Logic Loop Helpers

    // Makes a new Shoe
    // While a shoe has enough cards, continue to deal hands and prompt
    //  players for their inputs
    private void play() {
        this.shoe = new Shoe(this.numDecks);
        while (this.shoe.isPlayable()){
            this.clearHand();
            this.dealHand();
            this.displayAllCardsOnTable(false);
            this.waitInput();
            this.loopPlayersTurns();
            this.loopDealer();
        }
    }

    // Loops through each player and prompts them for their move
    private void loopPlayersTurns () {
        for (Seat s: this.seats) {
            if (s.occupied) {
                Player current_player = s.getPlayerObject();
                History<Player, String> p_history = current_player.getHistoryWrapper();
                this.printStatus("Starting User " + s.getPlayer() + " turn");
                Integer hit_stand_response = 0;
                while (hit_stand_response == 0) {
                    ArrayList<Integer> current_value = s.getValue();
                    StringBuilder prompt = new StringBuilder();
                    prompt.append("Hand Total: ");
                    for (Integer i=0; i< current_value.size(); ++i) {
                        prompt.append(current_value.get(i));
                        if (i != current_value.size()-1){
                            prompt.append(" or ");
                        }
                    }
                    prompt.append(". What do you do?");
                    this.printPrompt(prompt.toString());
                    hit_stand_response = current_player.getDecision(new ArrayList<>(List.of("Hit", "Stand")));
                    if (hit_stand_response == 1) {
                        this.printBasic("User: " + s.getPlayer() + " stays.");
                        p_history.addItem("Stays");
                        this.waitInput();
                    } else {
                        this.printBasic("User: " + s.getPlayer() + " hits.");
                        p_history.addItem("Hits");
                        this.shoe.dealCard(s);
                        Card new_card = this.shoe.getLastCard();
                        this.printBasic("User: " + s.getPlayer() + " is dealt a "  + new_card.longToString()  + '.');
                        Integer min_value = Collections.min(s.getValue());
                        if (min_value > 21) {
                            this.printBasic("User: " + s.getPlayer() + " busts with a value of "  + min_value  + '.');
                            hit_stand_response = 1;
                        }
                        this.waitInput();
                        this.displaySeatsWithCards();
                    }
                }
            }
        }
    }

    // Loops through the dealers turn
    // TODO: Deal card, show card, add logic for dealer hit/stay
    private void loopDealer() {
        this.printStatus("Starting Dealers Turn" );
    }

    // Displays the current seats and prompts user if they would
    //  like to adjust the seating arrangement.
    private void seatChangeLoop(ArrayList<Player> p) {
        Boolean change_seats = true;
        while (change_seats) {
            this.displaySeats();
            change_seats = this.promptSeatChange();
            if (change_seats) {
                try {
                    this.changeSeats(p);
                } catch (MaxSeatExeception err) {
                    this.displayError(err.toString());
                }
            } else if (!this.isReady()) {
                this.displayError("There are not enough players at the table to start a game...");
                change_seats = true;
            }
        }
    }

    // Prompts the user if they would like to change the seats
    private Boolean promptSeatChange() {
        this.printSpacer();
        this.printPrompt("Would you like to change the seats?");
        this.printSpacer();
        Integer i = this.waitResponse(new ArrayList<>(List.of("Yes", "No")));
        return i == 0;
    }

    // Prompts the user for a seat to change and the player to put in the seat
    public void changeSeats(ArrayList<Player> possible_players) throws MaxSeatExeception{
        this.displaySeats();
        this.printPrompt("Which seat would you like to change?");
        this.printSpacer();
        ArrayList<String> currentSeats = new ArrayList<>();
        for (Seat s: this.seats) {
            currentSeats.add(s.getPlayer());
        }
        Integer seatNum = this.waitResponse(currentSeats);
        this.printSpacer();
        this.printPrompt("Who would you like to sit in seat #" + (seatNum + 1) + "?");
        this.printSpacer();
        Integer playerId = this.waitResponse(possible_players, "Clear Player");
        if (playerId >= possible_players.size()) {
            this.leave(seatNum);
        } else {
            this.sit(possible_players.get(playerId), seatNum);
        }
    }
    // Sits a player at the specified integer seat
    // If the player is already occupying more than 2 seats an exception is thrown
    private void sit(Player p, Integer i) throws MaxSeatExeception {
        if (this.checkCanSit(p, i, 2)) {
            seat_history.addItem("Player: " + p + " enters seat #" + i);
            this.seats.get(i).addPlayer(p);
        } else {
            throw new MaxSeatExeception(2);
        }
    };

    // Checks if a player can be added to a seat, based on the maximum allowed seats per table
    private Boolean checkCanSit(Player p, Integer i, Integer max) {
        int seat_count = 0;
        for (Seat s: this.seats) {
            if (s.getPlayerObject() == p ){
                seat_count++;
            }
        }
        return seat_count != max;
    }

    // Removes a player from a seat
    private void leave(Integer i) {
        seat_history.addItem("Player: " +  this.seats.get(i).getPlayer() + " leaves seat #" + i);
        this.seats.get(i).removePlayer();
    };


    // Private Display Helpers

    // Display the seats, formatted
    private void displaySeats() {
        this.printSpacer();
        this.printTitle("Seats");
        this.printHorizontalOptions(this.seats);
        this.printSpacer();
    }

    // Displays an error, formatted
    private void displayError(String err) {
        this.printSpacer("x");
        this.printTitle(err);
        this.printSpacer("x");
    }

    // Checks if the table is ready to start
    private Boolean isReady() {
        Integer player_count = this.getCurrentSeatedPlayerCount();
        return player_count >= this.minSeats;
    }

    // Displays all of the cards for the players at the table
    private void displayAllCardsOnTable(Boolean dealer_hand) {
        this.printSpacer();
        this.printAnnouncement("Cards Dealt!");
        this.displayDealerHand(dealer_hand);
        this.displaySeatsWithCards();
    }

    // Displays the seats with the cards
    private void displaySeatsWithCards() {
        this.printSpacer();
        this.printTitle("Current Cards");
        this.printHorizontalOptions(this.seats);
        Integer counter = 0;
        Boolean still_cards = true;
        while (still_cards) {
            still_cards = false;
            ArrayList<Card> current_cards_for_row = new ArrayList<>();
            for (Seat s: this.seats) {
                Card card = s.getCard(counter);
                if (card != null) {
                    still_cards = true;
                }
                current_cards_for_row.add(card);
            }
            if (still_cards) {
                this.printHorizontalOptions(current_cards_for_row, false);
            }
            counter++;
        }
        this.printSpacer();
    }

    // Displays the dealers hand
    private void displayDealerHand(Boolean showAll) {
        this.printSpacer();
        this.printTitle("Dealer's Cards");

        ArrayList<Card> cards = this.dealer.cards;
        if (showAll) {
            this.printHorizontalOptions(cards, false);
        } else {
            ArrayList<Object> show = new ArrayList<>();
            show.add(cards.get(0));
            show.add("Hidden");
            this.printVerticalOptions(show, false);
        }
    }

    // Gets the number of seats a player is currently occupying
    private Integer getCurrentSeatedPlayerCount() {
        Integer i = 0;
        for (Seat seat: this.seats) {
            if (seat.occupied) { ++i; }
        }
        return i;
    }


    // Private Helpers

    // Runs once, at object creation - fills seats array with number of empty seats
    private void setupSeats() {
        for (int i =0; i <this.maxSeats;++i) {
            this.seats.add(new Seat());
        }
    }

    // INTENT: Get the persistent logger for the table from storage
    // Precondition: the file src/blackjack/BlackJackTableLog.dat exists
    private void getTableLog() {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("src/blackjack/BlackJackTableLog.dat");
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            this.log = (TableLog)in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't find log, initializing");
            this.log = new TableLog();
        }
    }

    // INTENT: Save the persistent logger for the table into storage
    private void saveLog() {
        String filename = "src/blackjack/BlackJackTableLog.dat";
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.log);
            out.close();
            file.close();
        }
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    //Related To Hands & Dealing
    private void clearHand() {
        for (Seat s: this.seats) {
            s.clearCards();
        }
        this.dealer.clearCards();
    }

    // Deals the hands for a turn
    private void dealHand() {
        this.log.incrementHandsDealt();
        this.saveLog();
        this.dealToSeats();
        this.dealToDealer();
        this.dealToSeats();
        this.dealToDealer();
    }
    private void dealToDealer() {
        this.shoe.dealCard(this.dealer);
    }
    private void dealToSeats() {
        for (Seat s: this.seats) {
            if (s.occupied){
                this.shoe.dealCard(s);
            }
        }
    }


}
