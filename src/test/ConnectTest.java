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
        app.delete_table();
        app.create_table();
        app.insert_default_players();
        ArrayList<HashMap> ps = app.select_all_players();
        assertEquals(2, ps.size());
        app.create_player_if_not_exist("Bob", "User");
        ps = app.select_all_players();
        assertEquals(3, ps.size());
        assertEquals("Random P1", ps.get(0).get("name").toString());
    }



}
