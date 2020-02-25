package test;

import FXRunner.Connect;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Testing connection of adding and selecting new players.
class ConnectTest {
    @Test
    public void testConnection() {
        Connect app = new Connect("testjack");
        app.delete_player_types_table();
        app.delete_players_table();
        app.setupTables();

        ArrayList<HashMap> ps = app.select_all_players();
        assertEquals(2, ps.size());

        app.create_player_if_not_exist("Bob", "User");
        ps = app.select_all_players();
        assertEquals(3, ps.size());

        assertEquals("Random P1", ps.get(0).get("name").toString());
        app.delete_player_types_table();
        app.delete_players_table();
    }

    @Test
    public void showTables() {
        Connect app = new Connect("testjack");
        app.delete_player_types_table();
        app.delete_players_table();
        app.setupTables();

        app.create_player_if_not_exist("Test_1", "User");
        app.create_player_if_not_exist("Test_2", "User");
        ArrayList<HashMap> ps = app.select_all_players();
        ps.stream().forEach(a -> {
            System.out.println("Got Player from table");
            System.out.println("Name: " + a.get("name") + " Type: " + a.get("type"));
        });
    }





}
