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

public class Enroll implements Initializable {

    @FXML
    private Button btnback;

    @FXML
    void back(ActionEvent actionEvent) throws IOException {
        Stage timetableStage = (Stage) btnback.getScene().getWindow();
        timetableStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("HomepageAdmin.fxml"));

        Stage homepageStage = new Stage();
        Scene scene = new Scene(root);
        homepageStage.setTitle("ScienceREC - Homepage - Admin");
        homepageStage.setScene(scene);
        homepageStage.show();
    }



    public void launchEnrollStudent(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnback.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollStudent.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Student");
        accountStage.setScene(scene);
        accountStage.show();

    }

    public void launchEnrollScientist(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnback.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollScientist.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Scientist");
        accountStage.setScene(scene);
        accountStage.show();

    }

    public void launchEnrollProfessor(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnback.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollProfessor.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Professor");
        accountStage.setScene(scene);
        accountStage.show();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
