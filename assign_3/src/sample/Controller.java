package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField id;
    public TextField name;
    public TextField author;
    public TextField location;
    public TextField quantity;
    public Button cb;
    public Button ub;
    public Button vb;
    public Button db;
    public Button chb;
    public ListView lv1;
    public ListView lv2;
    public RadioButton bk;
    public ToggleGroup p;
    public RadioButton mda;
    private ObservableList<Book> booklist;
    private ObservableList<Media> medialist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String DB_URL = "jdbc:derby://localhost:1527/PeoplesDB;";


        booklist = FXCollections.observableArrayList();
        medialist = FXCollections.observableArrayList();
        lv1.setItems(booklist);
        lv2.setItems(medialist);

        cb.setOnAction((ActionEvent->{
            try{
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.execute("CREATE TABLE itemTable (" +
                        "ID INT NOT NULL PRIMARY KEY , " +
                        "Name CHAR(20), " +
                        "Author CHAR(20), " +
                        "Location CHAR(20), " +
                        "Quantity INT NOT NULL, " +
                        "Category CHAR(20) )");
                stmt.close();
                System.out.println("Success!...Table Created");
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }));

        ub.setOnAction(ActionEvent-> {
            try{
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = null;
                int i = Integer.parseInt(id.getText());
                String n =name.getText();
                String a = author.getText();
                String l = location.getText();
                int q =Integer.parseInt(quantity.getText());
                String cat="";
                if(bk.isSelected() == true){
                    cat = "Book";
                }
                else if(mda.isSelected() == true){
                    cat = "Media";
                }
                stmt = conn.prepareStatement("INSERT INTO itemTable ( ID, Name, Author, Location, Quantity, Category) VALUES (?,?,?,?,?,?)");
                stmt.setInt(1, i);
                stmt.setString(2, n);
                stmt.setString(3, a);
                stmt.setString(4, l);
                stmt.setInt(5, q);
                stmt.setString(6, cat);
                stmt.executeUpdate();
                System.out.println("record inserted");
        }
        catch (SQLException e){
            e.printStackTrace();
            }


        });

        vb.setOnAction(actionEvent -> {
            ResultSet resultSet = null;
            try{
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement( "SELECT * FROM ITEMTABLE");
                resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    int id = Integer.parseInt(resultSet.getString("Id"));
                    String name = resultSet.getString("Name");
                    String author = resultSet.getString("Author");
                    String location = resultSet.getString("Location");
                    int quantity = Integer.parseInt(resultSet.getString("Quantity"));
                    String category = resultSet.getString("Category");
                    if(category.contains("Book")){
                        Book b = new Book(id, name, author, location, quantity, category);
                        booklist.add(b);
                    }
                    else if(category.contains("Media")) {
                        Media m = new Media(id, name, author, location, quantity, category);
                        medialist.add(m);
                    }
                }

            } catch (Exception ex){
                var msg = ex.getMessage();
                System.out.println(msg);
            }
        });

        db.setOnAction(ActionEvent->{
            try{
                Connection conn = DriverManager.getConnection(DB_URL);
                try (Statement stmt = conn.createStatement()) {
                    String sq = "DELETE FROM itemTable WHERE NAME ='" + name.getText() + "'";
                    stmt.execute(sq);
                }
                System.out.println("Deleted " + name.getText());
                name.clear();
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        });

        //check out button not working, im trying to delete the item and then replace it eith a duplicate biut with 1 less in the quantity column
        chb.setOnAction(ActionEvent->{
            ResultSet resultSet = null;
            try {
                Connection conn = DriverManager.getConnection(DB_URL);
                try (Statement stmt = conn.createStatement()) {

                    String sq = "DELETE FROM itemTable WHERE NAME ='" + name.getText() + "'";

                    while (resultSet.next()) {
                        if (name.getText() == resultSet.getString("Name")) {
                            PreparedStatement stmnt = conn.prepareStatement( "SELECT * FROM ITEMTABLE");
                            resultSet = stmnt.executeQuery();
                            System.out.println("yes");
                            stmt.execute(sq);
                            int id = Integer.parseInt(resultSet.getString("Id"));
                            id = id - 1;
                            String name = resultSet.getString("Name");
                            String author = resultSet.getString("Author");
                            String location = resultSet.getString("Location");
                            int quantity = Integer.parseInt(resultSet.getString("Quantity"));
                            String category = resultSet.getString("Category");
                            if (category.contains("Book")) {
                                Book b = new Book(id, name, author, location, quantity, category);
                                booklist.add(b);
                            } else if (category.contains("Media")) {
                                Media m = new Media(id, name, author, location, quantity, category);
                                medialist.add(m);
                            }
                            System.out.println("checked out");
                        }
                    }
                }


                } catch (Exception ex) {
                    var msg = ex.getMessage();
                    System.out.println(msg);

            }
        });
        
    }
}
