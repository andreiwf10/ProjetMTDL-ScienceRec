package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage loginStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        loginStage.setTitle("ScienceREC");
        loginStage.setScene(new Scene(root, 360, 375));
        loginStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}