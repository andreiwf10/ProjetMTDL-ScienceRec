package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class mysqlconnect {

    Connection conn = null;


    public static Connection ConnectDB(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sciencerec", "root", "");
            return conn;

        } catch (Exception e){

            return null;
        }
    }

    public static ObservableList<users> getDataelevi(){

        Connection conn = ConnectDB();
        ObservableList<users> list = FXCollections.observableArrayList();
        try {

            PreparedStatement ps = conn.prepareStatement("select * from elevi");
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while(rs.next()) {

                list.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }

    public static ObservableList<users> getDataprofesori(){

        Connection conn = ConnectDB();
        ObservableList<users> list = FXCollections.observableArrayList();
        try {

            PreparedStatement ps = conn.prepareStatement("select * from profesori");
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while(rs.next()) {

                list.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }


    public static ObservableList<users> getDatascientist(){

        Connection conn = ConnectDB();
        ObservableList<users> list = FXCollections.observableArrayList();
        try {

            PreparedStatement ps = conn.prepareStatement("select * from cercetatori");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                list.add(new users(Integer.parseInt(rs.getString("id")),rs.getString("username"),rs.getString("password"),rs.getString("nume"),rs.getString("prenume")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }


    public static ObservableList<Articles> getArticles(){

        Connection conn = ConnectDB();
        ObservableList<Articles> list = FXCollections.observableArrayList();
        try {

            PreparedStatement ps = conn.prepareStatement("select * from articole");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                list.add(new Articles(rs.getString("nume"),rs.getString("categorie"),rs.getString("hyperlinks")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }


    public static ObservableList<Articles> getMyList(){

        Connection conn = ConnectDB();
        ObservableList<Articles> list = FXCollections.observableArrayList();
        try {

            PreparedStatement ps = conn.prepareStatement("select * from list");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                list.add(new Articles(rs.getString("nume"),rs.getString("rating"),rs.getString("categorie"),rs.getString("hyperlink")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }



}