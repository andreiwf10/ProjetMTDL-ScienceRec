package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Homepage implements Initializable {

    @FXML
    private Button btnLogOut;


    @FXML
    void logout(ActionEvent actionEvent) throws IOException {
        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage loginStage = new Stage();
        Scene scene = new Scene(root);
        loginStage.setTitle("ScienceREC");
        loginStage.setScene(scene);
        loginStage.show();

    }


    public void launchArticles(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("ArticlesStudent.fxml"));
        Stage newsStage = new Stage();
        Scene scene = new Scene(root);
        newsStage.setTitle("ScienceREC - Articles - Student");
        newsStage.setScene(scene);
        newsStage.show();

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}