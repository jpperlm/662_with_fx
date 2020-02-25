package FXRunner;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

// Used for Interaction with DB
public class Connect {
    private String dbname;

    public Connect(String dbname) {
        this.dbname = dbname;
        this.setupTables();
    }
    /**
     * Connects to a the database and returns the connection
     */
    private Connection connect() {
        Connection conn = null;
        String url = "jdbc:sqlite:/Users/jasonperlman/IdeaProjects/BlackJackFX/" + this.dbname + ".db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Wrapper to call all required setup table functions
    // Creates the player_types, and player tables
    // Inserts defaults into both tables
    public void setupTables() {
        this.create_player_types_table();
        this.create_player_table();
        this.insert_default_player_types();
        this.insert_default_players();
    }

    // Drops the table for easy testing & resetting
    public void delete_players_table() {
        this.delete_generic_table("players");
    }

    // Drops the table for easy testing & resetting
    public void delete_player_types_table() {
      this.delete_generic_table("player_types");
    }

    // Finds or creates a player based on the name and type
    public void create_player_if_not_exist(String name, String type) {
        Connection conn = connect();
        try {
            // Find the item from the player_types table based on the string type.
            String type_find = "SELECT * FROM player_types where type='" + type + "'";
            Statement stmt_type = conn.createStatement();
            ResultSet rs_type = stmt_type.executeQuery(type_find);
            Integer type_id = null;
            if (rs_type != null && rs_type.next()) {
                type_id = rs_type.getInt("id");
            }

            // Find the player.
            String sql_find = "SELECT * FROM players where name='" + name + "' and type_id='" + type_id + "';";
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

            // Add the player if it doesn't exist.
            String sql = "INSERT INTO players(name, type_id, money) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, type_id);
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
        String sql = "SELECT * FROM players join player_types ON players.type_id = player_types.id ORDER BY name ASC;";
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


    // Creates the players table if it doesn't already exist
    // Contains columns id, name, and type
    private void create_player_table() {
        Connection conn = connect();
        String sql = "CREATE TABLE IF NOT EXISTS players (\n"
                + " id integer PRIMARY KEY,\n" // Primary Key
                + " name text NOT NULL,\n" // Unique Name
                + " type_id integer NOT NULL,\n" // Type of Player
                + " money integer NOT NULL,\n"
                + " FOREIGN KEY(type_id) REFERENCES player_types(id)\n"
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

    // Creates the player_types table if it doesn't already exist
    // Contains columns id, type
    private void create_player_types_table() {
        Connection conn = connect();
        String sql = "CREATE TABLE IF NOT EXISTS player_types (\n"
                + " id integer PRIMARY KEY,\n" // Primary Key
                + " type text NOT NULL\n" // Type of Player
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
    // Deletes any table with the specified name
    private void delete_generic_table(String name) {
        String sql = "DROP TABLE IF EXISTS " + name + ";" ;
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

    // Creates two dummy RANDOM default players (if they do not already exist)
    private void insert_default_players() {
        create_player_if_not_exist("Random P1", "Random");
        create_player_if_not_exist("Random P2", "Random");
    }

    // Creates two records for player types
    // ID: 0, Type: Random and ID: 1, Type: User
    private void insert_default_player_types() {
        String sql_1 = "INSERT INTO player_types(id, type) VALUES(0,'Random')";
        String sql_2 = "INSERT INTO player_types(id, type) VALUES(1,'User')";
        String sql_find = "SELECT * FROM player_types;";
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_find);
            if (rs != null && rs.next()) {return;}
            PreparedStatement pstmt_1 = conn.prepareStatement(sql_1);
            pstmt_1.executeUpdate();
            PreparedStatement pstmt_2 = conn.prepareStatement(sql_2);
            pstmt_2.executeUpdate();
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

}