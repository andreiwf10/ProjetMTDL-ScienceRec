package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private TextField txtuname;

    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField txtpass;

    Connection con;

    PreparedStatement pst1;
    PreparedStatement pst2;
    PreparedStatement pst3;
    PreparedStatement pst4;
    PreparedStatement pst5;
    PreparedStatement pst6;

    ResultSet rs1;
    ResultSet rs2;
    ResultSet rs3;
    ResultSet rs4;
    ResultSet rs5;
    ResultSet rs6;


    @FXML
    void login(ActionEvent event) {

        String uname = txtuname.getText();
        String pass = txtpass.getText();
        String name = txtpass.getText();

        if ((uname.equals("") && pass.equals("")) || (uname.equals("") || pass.equals(""))) {
//            JOptionPane.showMessageDialog(null, "Username or password empty!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Missing User/Pass");
            alert.setHeaderText(null);
            alert.setContentText("Username or password empty!");
            alert.showAndWait();

        } else {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/sciencerec", "root", "");


                pst1 = con.prepareStatement("select * from elevi where username=? and password=?");
                pst1.setString(1, uname);
                pst1.setString(2, pass);
                rs1 = pst1.executeQuery();

                pst2 = con.prepareStatement("select prenume from elevi where username=?");
                pst2.setString(1,uname);
                rs2 = pst2.executeQuery();
                rs2.next();

                pst3 = con.prepareStatement("select nume from profesori where username=?");
                pst3.setString(1,uname);
                rs3 = pst3.executeQuery();
                rs3.next();

                pst4 = con.prepareStatement("select * from profesori where username=? and password=?");
                pst4.setString(1,uname);
                pst4.setString(2,pass);
                rs4 = pst4.executeQuery();

                pst5 = con.prepareStatement("select nume from cercetatori where username=?");
                pst5.setString(1,uname);
                rs5 = pst5.executeQuery();
                rs5.next();

                pst6 = con.prepareStatement("select * from cercetatori where username=? and password=?");
                pst6.setString(1,uname);
                pst6.setString(2,pass);
                rs6 = pst6.executeQuery();



//                if(rs2.next()) {
//
//                        System.out.println("Database returned: " + rs2.getString(1));
//                }


//                if (rs1.next()) { // QUERY FOR LOGIN VERIFICATION

//                    if (rs3.next() && (rs3.getString(1).equals("admin"))) { // QUERY FOR ADMIN RIGHTS VERIFICATION

                if(rs4.next()){

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Login Success! Welcome, " + rs3.getString(1) + "!");
                        alert.showAndWait();
                        Stage loginStage = (Stage) btnlogin.getScene().getWindow();

                        loginStage.close(); // Daca Loginul se face cu succes, stage-ul de login se inchide

                        Parent root = FXMLLoader.load(getClass().getResource("HomepageAdmin.fxml"));
                        Stage stageHomepage = new Stage();
                        Scene scene = new Scene(root);
                        stageHomepage.setTitle("School Manager - Homepage (ADMIN)");
                        stageHomepage.setScene(scene);
                        stageHomepage.show();

                    } else if (rs1.next()) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Success! Welcome, " + rs2.getString(1) + "!");
                    alert.showAndWait();
                    Stage loginStage = (Stage) btnlogin.getScene().getWindow();

                    loginStage.close(); // Daca Loginul se face cu succes, stage-ul de login se inchide


                    Parent root = FXMLLoader.load(getClass().getResource("ArticlesStudent.fxml"));
                    Stage stageHomepage = new Stage();
                    Scene scene = new Scene(root);
                    stageHomepage.setTitle("ScienceREC - Articles - Student");
                    stageHomepage.setScene(scene);
                    stageHomepage.show();


                } else if(rs6.next()) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Success! Welcome, " + rs5.getString(1) + "!");
                    alert.showAndWait();
                    Stage loginStage = (Stage) btnlogin.getScene().getWindow();

                    loginStage.close(); // Daca Loginul se face cu succes, stage-ul de login se inchide

                    Parent root = FXMLLoader.load(getClass().getResource("ArticlesResearchers.fxml"));
                    Stage stageHomepage = new Stage();
                    Scene scene = new Scene(root);
                    stageHomepage.setTitle("School Manager - Article -  Researchers");
                    stageHomepage.setScene(scene);
                    stageHomepage.show();

                    } else {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Failure");
                        alert.setHeaderText(null);
                        alert.setContentText("Login Failed!");
                        alert.showAndWait();
                        Stage loginStage = (Stage) btnlogin.getScene().getWindow();

                        txtuname.setText("");
                        txtpass.setText("");
                        txtuname.requestFocus();

                    }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}