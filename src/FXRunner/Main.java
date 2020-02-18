package FXRunner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


// Intent: Pre-Run for the BlackJackSimulator Application. This will prompt the user to enter their name
//      And then pass this information along to BlackJackRunner
public class Main extends Application {
    @FXML
    private Text actiontarget; // A message that can be used to display error messages on the JavaFX Interface
    @FXML
    private TextField userinput; // The TextField for the user to input their name

    public static String username;
    public static void main(String[] args) {
        launch(args);
    }


    // Starts the Scene and displays the Window to the user.
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Black Jack (21) Simulator");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }

    // On shutdown trigger the BlackJackRunner class. This is equivalent to calling the class from the command line to run
    // Passes the username that was entered through as an argument
    @Override
    public void stop() {
        BlackJackRunner.main(new String[] {  username });
    }

    // INTENT: Handles when the continue button is pressed.
    //          If the TextField is empty, then show error
    //          Else, set value as username and close the window
    // TRIGGERED BY: Click of the continue button
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws InterruptedException {
        String t = userinput.getText();
        if (t.length() == 0) {
            actiontarget.setText("You must enter some characters, this cannot be null");
        } else {
            username = t;
            final Stage stage = (Stage) userinput.getScene().getWindow();
            stage.close();
        }
    }

}
