package FXRunner;

import blackjack.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

// Used for Interaction with DB
public class Connect {

    /**
     * Connects to a the database and returns the connection
     */
    private Connection connect() {
        Connection conn = null;
        String url = "jdbc:sqlite:/Users/jasonperlman/IdeaProjects/BlackJackFX/blackjack.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Drops the table for easy testing & resetting
    public void delete_table() {
        String sql = "DROP TABLE IF EXISTS players";
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
    }

    // Creates the players table if it doesn't already exist
    // Contains columns id, name, and type
    public void create_table() {
        Connection conn = connect();
        String sql = "CREATE TABLE IF NOT EXISTS players (\n"
                + " id integer PRIMARY KEY,\n" // Primary Key
                + " name text NOT NULL,\n" // Unique Name
                + " type text NOT NULL,\n" // Type of Player
                + " money integer NOT NULL\n"
                + ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
    }


    // Creates two dummy RANDOM default players (if they do not already exist)
    public void insert_default_players() {
        create_player_if_not_exist("Random P1", "Random");
        create_player_if_not_exist("Random P2", "Random");
    }


    // Finds or creates a player based on the name and type
    public void create_player_if_not_exist(String name, String type) {
        String sql_find = "SELECT * FROM players where name='" + name + "' and type='" + type + "';";
        String sql = "INSERT INTO players(name, type, money) VALUES(?,?,?)";
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_find);
            Boolean exists = false;
            if (rs != null && rs.next()) {
                exists = true;
            }
            if (exists) {
                System.out.println("Player " + name + " of type " + type + " already exists in the db.");
                return;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setDouble(3, 100);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
    }

    // Selects all players from the DB and returns an array of hashmap with the player information
    public ArrayList<HashMap> select_all_players() {
        String sql = "SELECT * FROM players;";
        ArrayList<HashMap> players = new ArrayList<>();
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("name", rs.getString("name"));
                hm.put("type", rs.getString("type"));
                hm.put("money", Double.toString(rs.getDouble("money")));
                players.add(hm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
        return players;
    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        Connect app = new Connect();
////        app.delete_table();
//        app.create_table();
//        app.insert_default_players();
//    }
}