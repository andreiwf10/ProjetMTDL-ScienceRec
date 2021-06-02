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

public class HomepageAdmin implements Initializable{

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnenrollstudent;

    @FXML
    private Button btnenrollprof;

    @FXML
    private Button btnenrollscientist;


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
        Parent root = FXMLLoader.load(getClass().getResource("ArticlesAdmin.fxml"));
        Stage enrollStage = new Stage();
        Scene scene = new Scene(root);
        enrollStage.setTitle("ScienceREC - Articles - Admin");
        enrollStage.setScene(scene);
        enrollStage.show();

    }



    public void launchEnrollStudent(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollStudent.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Student");
        accountStage.setScene(scene);
        accountStage.show();

    }

    public void launchEnrollScientist(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollScientist.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Scientist");
        accountStage.setScene(scene);
        accountStage.show();

    }

    public void launchEnrollProfessor(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("EnrollProfessor.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll Professor");
        accountStage.setScene(scene);
        accountStage.show();

    }

    public void launchEnroll(ActionEvent actionEvent) throws IOException {

        Stage homepageStage = (Stage) btnLogOut.getScene().getWindow();
        homepageStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("Enroll.fxml"));
        Stage accountStage = new Stage();
        Scene scene = new Scene(root);
        accountStage.setTitle("ScienceREC - Enroll");
        accountStage.setScene(scene);
        accountStage.show();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}