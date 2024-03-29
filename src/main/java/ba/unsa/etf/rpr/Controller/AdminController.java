package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.Dao;
import ba.unsa.etf.rpr.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController {
    public ListView lv,lv2;
    private Dao dao;
    public List<String> o;
    public TextField tf1,tf2,tfdlt;
    public DatePicker datum;
    public Button btt,ex,add,dlt,iz;
    public Label lb3;
    @FXML
    public void initialize() throws SQLException {
        dao=Dao.getInstance();
        var f=dao.getAllData();
        Collections.reverse(f);
        lv.getItems().addAll(f);
    }
    public void submit(ActionEvent actionEvent) throws SQLException {
        if (tf1.getText() != null && datum.getValue() != null) {
            tf2.setText(dao.getSpecifcUser(tf1.getText(), datum.getValue().getDayOfMonth(), datum.getValue().getMonth().toString(), datum.getValue().getYear()));

            lb3.setText(datum.getValue().getMonth().toString());
            lv2.getItems().clear();
            lv2.getItems().addAll(dao.getMonth(tf1.getText(), datum.getValue().getMonth().toString(), datum.getValue().getYear()));
        }
    }
    public void logut(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btt.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"),bundle);
        primaryStage.setTitle("Clockify");
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/img/Ikona.png"));
        primaryStage.show();
    }
    public void delete(ActionEvent actionEvent) throws IOException, SQLException {
        if(tfdlt.getText()!=null){
            dao.deleting(tfdlt.getText());
            var f=dao.getAllData();
            Collections.reverse(f);
            lv.getItems().clear();
            lv.getItems().addAll(f);
        }
    }
    public void addUser(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form.fxml"),bundle);
        Parent root=loader.load();
        primaryStage.setTitle("Clockify");
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/img/Ikona.png"));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }
    public void report(ActionEvent actionEvent){
        try {
            new Report().showReport(dao.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }
}
