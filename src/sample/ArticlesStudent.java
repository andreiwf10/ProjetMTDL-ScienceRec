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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ArticlesStudent implements Initializable {

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

    @FXML
    private Button backHomepage;

    @FXML
    void back(ActionEvent actionEvent) throws IOException {
        Stage timetableStage = (Stage) backHomepage.getScene().getWindow();
        timetableStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));

        Stage articleStage = new Stage();
        Scene scene = new Scene(root);
        articleStage.setTitle("ScienceREC - Homepage - Student");
        articleStage.setScene(scene);
        articleStage.show();

    }

    @FXML
    private Button btn1;

    @FXML
    private TextField searchField;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn_setrating;

    @FXML
    private TextField txt_rating;

    @FXML
    private TableView<Articles> table_articles;

    @FXML
    private TableView<Articles> table_list;

    @FXML
    private TableColumn<Articles, String> col_nume;

    @FXML
    private TableColumn<Articles, String> col_hyperlinks;

    @FXML
    private TableColumn<Articles, String> col_category;

    @FXML
    private TableColumn<Articles, String> col_mylist;

    @FXML
    private TableColumn<Articles, String> col_rating;

    @FXML
    private TableColumn<Articles, String> col_listCategory;

    @FXML
    private TableColumn<Articles, String> col_listHyperlink;



    ObservableList<Articles> listM;
    ObservableList<Articles> MyList;





    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void search(){

        FilteredList<Articles> filteredData = new FilteredList<>(listM,e -> true);

        searchField.setOnKeyReleased(e -> {

            searchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

                filteredData.setPredicate((Predicate<? super Articles>) article -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFiler = newValue.toLowerCase();
                    if(article.getNume().toLowerCase().contains(newValue)) {
                        return true;
                    } else if (article.getCategorie().toLowerCase().contains(lowerCaseFiler)){
                        return true;
                    }
                    return false;
                });

            }));
            SortedList<Articles> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table_articles.comparatorProperty());
            table_articles.setItems(sortedData);
        });

    }



    public void Open_article() {

        Articles article;

        article = table_articles.getSelectionModel().getSelectedItem();

        String filePath = article.getHyperlinks();
        File file = new File(filePath);

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void Add_article(ActionEvent event) {

        Hyperlink articleHyperlink = new Hyperlink();

        conn = mysqlconnect.ConnectDB();
        String sql = "insert into list (nume,rating,categorie,hyperlink) values (?,?,?,?)";

        try {
                pst = conn.prepareStatement(sql);
                pst.setString(1,table_articles.getSelectionModel().getSelectedItem().nume);
                pst.setString(2,table_articles.getSelectionModel().getSelectedItem().rating);
                pst.setString(3,table_articles.getSelectionModel().getSelectedItem().categorie);
                pst.setString(4,table_articles.getSelectionModel().getSelectedItem().hyperlinks);
                pst.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Article added");
                alert.setHeaderText(null);
                alert.setContentText("Article added successfully to list!");
                alert.showAndWait();

            //Table refresh

                MyList.clear();
                PreparedStatement ps = conn.prepareStatement("select * from list");
                rs = ps.executeQuery();

                while (rs.next()) {

                    MyList.add(new Articles(rs.getString("nume"),rs.getString("rating"),rs.getString("categorie"),rs.getString("hyperlink")));

                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void Delete_article(ActionEvent event) {

        Hyperlink articleHyperlink = new Hyperlink();

        conn = mysqlconnect.ConnectDB();
        String sql = "delete from list where hyperlink = ?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,table_list.getSelectionModel().getSelectedItem().hyperlinks);
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article deleted");
            alert.setHeaderText(null);
            alert.setContentText("Article deleted successfully from list!");
            alert.showAndWait();

            //Table refresh

            MyList.clear();
            PreparedStatement ps = conn.prepareStatement("select * from list");
            rs = ps.executeQuery();

            while (rs.next()) {

                MyList.add(new Articles(rs.getString("nume"),rs.getString("rating"),rs.getString("hyperlink")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void Set_rating() {

        conn = mysqlconnect.ConnectDB();
        String sql = "update list set rating = ? where nume = ?";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_rating.getText());
            pst.setString(2,table_list.getSelectionModel().getSelectedItem().nume);
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article rating updated");
            alert.setHeaderText(null);
            alert.setContentText("Article rating updated successfully!");
            alert.showAndWait();

            //Table Refresh1
            MyList.clear();

            PreparedStatement ps = conn.prepareStatement("select * from list");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MyList.add(new Articles(rs.getString("nume"),rs.getString("rating"),rs.getString("hyperlink"),rs.getString("categorie")));

            }

            txt_rating.setText("");
            txt_rating.requestFocus();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    public void Delete_rating() {

        conn = mysqlconnect.ConnectDB();
        String sql = "update list set rating = ? where nume = ?";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1,null);
            pst.setString(2,table_list.getSelectionModel().getSelectedItem().nume);
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article rating deleted");
            alert.setHeaderText(null);
            alert.setContentText("Article rating deleted successfully!");
            alert.showAndWait();

            //Table Refresh
            MyList.clear();

            PreparedStatement ps = conn.prepareStatement("select * from list");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MyList.add(new Articles(rs.getString("nume"),rs.getString("rating"),rs.getString("hyperlink")));

            }

            txt_rating.setText("");
            txt_rating.requestFocus();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_nume.setCellValueFactory(new PropertyValueFactory<Articles,String>("nume"));
        col_category.setCellValueFactory(new PropertyValueFactory<Articles,String>("categorie"));
        col_hyperlinks.setCellValueFactory(new PropertyValueFactory<Articles,String>("hyperlinks"));

        listM = mysqlconnect.getArticles();
        table_articles.setItems(listM);

        col_mylist.setCellValueFactory(new PropertyValueFactory<Articles,String>("nume"));
        col_rating.setCellValueFactory(new PropertyValueFactory<Articles,String>("categorie"));

        MyList = mysqlconnect.getMyList();
        table_list.setItems(MyList);

    }

}