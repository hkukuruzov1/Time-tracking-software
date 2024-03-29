package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.Dao;
import ba.unsa.etf.rpr.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    public Button login;
    public TextField lbl;
    public PasswordField pass;
    private Dao dao;
    public Label time;
    public MenuItem eng,bs,fr,ab;
    @FXML
    void initialize() throws SQLException {
    dao= Dao.getInstance();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        lbl.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                pass.requestFocus();
            }
        });
        pass.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                login.requestFocus();
                try {
                    actionLogin(null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void actionExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    public void actionLogin(ActionEvent actionEvent) throws SQLException, IOException {
        if(dao.doesUserExist(lbl.getText(),pass.getText()))
        {
            User k= dao.getUser(lbl.getText(),pass.getText());
            if(dao.isItAdmin(k.getUsername(),k.getPassword())){
                Stage stage = (Stage) login.getScene().getWindow();
                stage.close();
                Stage primaryStage=new Stage();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"),bundle);
                Parent root=loader.load();
                primaryStage.setTitle("Clockify");
                primaryStage.setScene(new Scene(root, 300, 350));
                primaryStage.setMaximized(true);
                primaryStage.setResizable(false);
                primaryStage.getIcons().add(new Image("/img/Ikona.png"));
                primaryStage.show();
            }
            else{
                Stage stage = (Stage) login.getScene().getWindow();
                stage.close();
                Stage primaryStage=new Stage();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/worker.fxml"),bundle);
                Parent root=loader.load();
                WorkTimeController newone=loader.getController();
                newone.setController(this);
                primaryStage.setTitle("Clockify");
                primaryStage.setScene(new Scene(root, 450, 200));
                primaryStage.setMinHeight(200);
                primaryStage.setMinWidth(450);
                primaryStage.getIcons().add(new Image("/img/Ikona.png"));
                primaryStage.show();
            }
        }
        else{
            lbl.textProperty().setValue("");
            pass.textProperty().setValue("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            ResourceBundle rb = ResourceBundle.getBundle("Translation", Locale.getDefault());
            alert.setTitle(rb.getString("Error"));
            alert.setHeaderText(rb.getString("gpa"));
            alert.setContentText(rb.getString("np"));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/img/Ikona.png").toString()));
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }
    public void englishLanguage(ActionEvent actionEvent){
        Stage stage = (Stage) login.getScene().getWindow();
        Locale.setDefault(new Locale("en", "US"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), ResourceBundle.getBundle("Translation"));
        try {
            stage.setScene(new Scene(loader.load(),400,350));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void bosnianLanguage(ActionEvent actionEvent){
        Stage stage = (Stage) login.getScene().getWindow();
        Locale.setDefault(new Locale("bs", "BA"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), ResourceBundle.getBundle("Translation"));
        try {
            stage.setScene(new Scene(loader.load(),400,350));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void frenchLanguage(ActionEvent actionEvent){
        Stage stage = (Stage) login.getScene().getWindow();
        Locale.setDefault(new Locale("fr", "FR"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), ResourceBundle.getBundle("Translation"));
        try {
            stage.setScene(new Scene(loader.load(),400,350));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void aboutWindow(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"),bundle);
        Parent root=loader.load();
        primaryStage.setTitle("About");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root,300,250));
        root.requestFocus();
        primaryStage.getIcons().add(new Image("/img/Ikona.png"));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }
}
