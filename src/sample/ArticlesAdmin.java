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
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ArticlesAdmin implements Initializable {


    @FXML
    private Button btnback;

    @FXML
    void back(ActionEvent actionEvent) throws IOException {
        Stage timetableStage = (Stage) btnback.getScene().getWindow();
        timetableStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("HomepageAdmin.fxml"));

        Stage articleStage = new Stage();
        Scene scene = new Scene(root);
        articleStage.setTitle("ScienceREC - Homepage - Admin");
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
    private TextField txt_category;

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



    ObservableList<Articles> listM;





    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void search(){

        FilteredList<Articles> filteredData = new FilteredList<>(listM, e -> true);

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
        String sql = "insert into articole (nume,hyperlinks) values (?,?)";

        try {
//            pst = conn.prepareStatement(sql);
//            pst.setString(1,table_articles.getSelectionModel().getSelectedItem().nume);
//            pst.setString(2,table_articles.getSelectionModel().getSelectedItem().rating);
//            pst.setString(3,table_articles.getSelectionModel().getSelectedItem().hyperlinks);
//            pst.execute();

            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("C:\\Users\\andrei.vaciu2911\\Desktop\\ProjetMTDL-ScienceRec\\src\\ArticlesPDF"));
            File selectedFile = fc.showOpenDialog(null);

            if(selectedFile != null){

                pst = conn.prepareStatement(sql);
                pst.setString(1,selectedFile.getName());
                pst.setString(2,selectedFile.getAbsolutePath());
                pst.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Article added");
                alert.setHeaderText(null);
                alert.setContentText("Article added successfully to list!");
                alert.showAndWait();

            //Table refresh

                listM.clear();
                PreparedStatement ps = conn.prepareStatement("select * from articole");
                rs = ps.executeQuery();

                while (rs.next()) {

                    listM.add(new Articles(rs.getString("nume"),rs.getString("categorie"),rs.getString("hyperlinks")));

                }

            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Select a file!");
                alert.setHeaderText(null);
                alert.setContentText("Please select a file!");
                alert.showAndWait();

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    public void Delete_article(ActionEvent event) {

        Hyperlink articleHyperlink = new Hyperlink();

        conn = mysqlconnect.ConnectDB();
        String sql = "delete from articole where hyperlinks = ?";

        Articles article;

        article = table_articles.getSelectionModel().getSelectedItem();
        String filePath = article.getHyperlinks();
        File file = new File(filePath);

        try {


            pst = conn.prepareStatement(sql);
            pst.setString(1,article.getHyperlinks());
            pst.execute();

            //Table refresh

            listM.clear();
            PreparedStatement ps = conn.prepareStatement("select * from articole");
            rs = ps.executeQuery();

            while (rs.next()) {

                listM.add(new Articles(rs.getString("nume"),rs.getString("categorie"),rs.getString("hyperlinks")));

            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article deleted");
            alert.setHeaderText(null);
            alert.setContentText("Article deleted successfully!");
            alert.showAndWait();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void Set_category() {

        conn = mysqlconnect.ConnectDB();
        String sql = "update articole set categorie = ? where nume = ?";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_category.getText());
            pst.setString(2,table_articles.getSelectionModel().getSelectedItem().nume);
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article cateogry updated");
            alert.setHeaderText(null);
            alert.setContentText("Article category updated successfully!");
            alert.showAndWait();

            //Table Refresh
            listM.clear();

            PreparedStatement ps = conn.prepareStatement("select * from articole");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listM.add(new Articles(rs.getString("nume"),rs.getString("categorie"),rs.getString("hyperlinks")));

            }

            txt_category.setText("");
            txt_category.requestFocus();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void Delete_category() {

        conn = mysqlconnect.ConnectDB();

        Articles article;

        article = table_articles.getSelectionModel().getSelectedItem();
        String filePath = article.getHyperlinks();
        File file = new File(filePath);

        String sql = "update articole set categorie = NULL where hyperlinks = ?";
        try {


            pst = conn.prepareStatement(sql);
            pst.setString(1,article.getHyperlinks());
            pst.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Article category deleted");
            alert.setHeaderText(null);
            alert.setContentText("Article category deleted successfully!");
            alert.showAndWait();

            //Table Refresh
            listM.clear();

            PreparedStatement ps = conn.prepareStatement("select * from articole");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listM.add(new Articles(rs.getString("nume"),rs.getString("categorie"),rs.getString("hyperlinks")));

            }

            txt_category.setText("");
            txt_category.requestFocus();

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


//        MyList = mysqlconnect.getMyList();
//        table_list.setItems(MyList);

    }

}