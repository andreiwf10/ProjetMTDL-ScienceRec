package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class EnrollStudent implements Initializable {

    @FXML
    private Button btnback;

    @FXML
    void back(ActionEvent actionEvent) throws IOException {
        Stage timetableStage = (Stage) btnback.getScene().getWindow();
        timetableStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("Enroll.fxml"));

        Stage homepageStage = new Stage();
        Scene scene = new Scene(root);
        homepageStage.setTitle("ScienceREC - Enroll - Admin");
        homepageStage.setScene(scene);
        homepageStage.show();
    }

    @FXML
    private TableView<users> table_users;

    @FXML
    private TableColumn<users, Integer> col_id;

    @FXML
    private TableColumn<users, String> col_username;

    @FXML
    private TableColumn<users, String> col_password;

    @FXML
    private TableColumn<users, String> col_lname;

    @FXML
    private TableColumn<users, String> col_fname;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_lname;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextField searchField;


    ObservableList<users> listM;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_users () {

        conn = mysqlconnect.ConnectDB();
        String sql = "insert into elevi (id,username,password,nume,prenume) values (?,?,?,?,?)";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.setString(2, txt_username.getText());
            pst.setString(3, txt_password.getText());
            pst.setString(4, txt_lname.getText());
            pst.setString(5, txt_fname.getText());
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Student Added");
            alert.setHeaderText(null);
            alert.setContentText("Student added successfully!");
            alert.showAndWait();

        //Table Refresh
            listM.clear();

            PreparedStatement ps = conn.prepareStatement("select * from elevi");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listM.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

            txt_id.setText("");
            txt_username.setText("");
            txt_password.setText("");
            txt_lname.setText("");
            txt_fname.setText("");
            txt_id.requestFocus();


        } catch (Exception e) {

        }

    }


    public void Delete_users () {

        conn = mysqlconnect.ConnectDB();
        String sql = "delete from elevi where id = ?";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_id.getText());
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Student Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Student deleted successfully!");
            alert.showAndWait();

        //Table Refresh
            listM.clear();

            PreparedStatement ps = conn.prepareStatement("select * from elevi");
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {

                listM.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

            txt_id.setText("");
            txt_username.setText("");
            txt_password.setText("");
            txt_lname.setText("");
            txt_fname.setText("");
            txt_id.requestFocus();

        } catch (Exception e){

        }

    }


    public void Update_users() {

        conn = mysqlconnect.ConnectDB();
        String sql = "update elevi set id = ?,username = ?,password = ?,nume = ?,prenume = ? where id = ?";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_id.getText());
            pst.setString(6,txt_id.getText());
            pst.setString(2,txt_username.getText());
            pst.setString(3,txt_password.getText());
            pst.setString(4,txt_lname.getText());
            pst.setString(5,txt_fname.getText());
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Student Updated");
            alert.setHeaderText(null);
            alert.setContentText("Student updated successfully!");
            alert.showAndWait();

        //Table Refresh
            listM.clear();

            PreparedStatement ps = conn.prepareStatement("select * from elevi");
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {

                listM.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

            txt_id.setText("");
            txt_username.setText("");
            txt_password.setText("");
            txt_lname.setText("");
            txt_fname.setText("");
            txt_id.requestFocus();

        } catch (Exception e){

        }

    }

//    public void search(){
//
//        FilteredList<users> filteredData = new FilteredList<>(listM, e -> true);
//
//        searchField.setOnKeyReleased(e -> {
//
//            searchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
//
//                filteredData.setPredicate((Predicate<? super users>) article -> {
//                    if(newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFiler = newValue.toLowerCase();
//                    if(article.getNume().toLowerCase().contains(newValue)) {
//                        return true;
//                    } else if (article.getCategorie().toLowerCase().contains(lowerCaseFiler)){
//                        return true;
//                    }
//                    return false;
//                });
//
//            }));
//            SortedList<Articles> sortedData = new SortedList<>(filteredData);
//            sortedData.comparatorProperty().bind(table_articles.comparatorProperty());
//            table_articles.setItems(sortedData);
//        });
//
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_id.setCellValueFactory(new PropertyValueFactory<users,Integer>("id"));
        col_username.setCellValueFactory(new PropertyValueFactory<users,String>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<users,String>("password"));
        col_lname.setCellValueFactory(new PropertyValueFactory<users,String>("lastname"));
        col_fname.setCellValueFactory(new PropertyValueFactory<users,String>("firstname"));

        listM = mysqlconnect.getDataelevi();
        table_users.setItems(listM);

    }
}
