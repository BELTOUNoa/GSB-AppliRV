/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import gsb.rv.dr.entities.Visiteur;
import gsb.rv.dr.technique.Session;
import gsb.rv.dr.technique.ConnexionBD;
import gsb.rv.dr.technique.ConnexionException;
import gsb.rv.dr.modeles.ModelesGsbRv;
import gsb.rv.dr.modeles.VueConnexion;
import java.rmi.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 *
 * @author root
 */
public class GSBRVDR extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        
        
        
        BorderPane root = new BorderPane();
        
        
        
        MenuBar barreMenus = new MenuBar();
        Menu menuFichier = new Menu("Fichiers");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem itemSeConnecter = new MenuItem("Se Connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se Deconnecter");
        MenuItem itemConsulter = new MenuItem("Consulter");
        MenuItem itemHesitants = new MenuItem("Hesitant");
        MenuItem itemQuitter = new MenuItem("Quitter");
        ButtonType btnOui = new ButtonType("Oui");
        ButtonType btnNon = new ButtonType("Non");
        
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN));
        
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        itemSeConnecter.setOnAction((ActionEvent actionEvent) -> {
            System.out.println("1");
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Authentification");
            dialog.setHeaderText("Saisir vos données de connexion");
            ButtonType connexion = new ButtonType("Se Connecter", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(connexion, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            TextField username = new TextField();
            username.setPromptText("Username");
            PasswordField password = new PasswordField();
            password.setPromptText("Password");
            grid.add(new Label("Username:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(password, 1, 1);
            Node loginButton = dialog.getDialogPane().lookupButton(connexion);
            loginButton.setDisable(true);
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });
            dialog.getDialogPane().setContent(grid);
            Platform.runLater(() -> username.requestFocus());
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == connexion) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });
            Optional<Pair<String, String>> reponse = dialog.showAndWait();
            
                if(reponse.isPresent()){
                try {
                    Visiteur visiteur1 =
                        ModelesGsbRv.seConnecter(username.getText(),password.getText());
                } catch (ConnexionException ex) {
                    Logger.getLogger(GSBRVDR.class.getName()).log(Level.SEVERE, null, ex);
                }
                    barreMenus.getMenus().clear();
                    menuFichier.getItems().clear();
                    menuFichier.getItems().add(itemSeDeconnecter);
                    menuFichier.getItems().add(itemQuitter);
                    menuRapports.getItems().add(itemConsulter);
                    menuPraticiens.getItems().add(itemHesitants);
                    barreMenus.getMenus().add(menuFichier);
                    barreMenus.getMenus().add(menuRapports);
                    barreMenus.getMenus().add(menuPraticiens);
                    primaryStage.setTitle(Session.getLeVisiteur().toString());    
                    }
            
        });
        itemSeDeconnecter.setOnAction((ActionEvent actionEvent) -> {
            barreMenus.getMenus().clear();
            menuFichier.getItems().clear();
            menuFichier.getItems().add(itemSeConnecter);
            menuFichier.getItems().add(itemQuitter);
            barreMenus.getMenus().add(menuFichier);
            primaryStage.setTitle("GSB RV");

        });
        itemQuitter.setOnAction((ActionEvent actionEvent) -> {
            Alert alertQuitter = new Alert (Alert.AlertType.CONFIRMATION);
            
            alertQuitter.setTitle("Quitter");
            alertQuitter.setHeaderText("Demande de Confirmation");
            alertQuitter.setContentText("Voulez-Vous quitter l'application?");
            alertQuitter.getButtonTypes().setAll(btnOui,btnNon);
            Optional<ButtonType> response= alertQuitter.showAndWait();
            if( response.get()==btnOui){
                Platform.exit();
            }
        });
       
        
        root.setTop(barreMenus);
      
        
        Scene scene = new Scene(root, 650, 350);
        primaryStage.setTitle("GSB RV");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
