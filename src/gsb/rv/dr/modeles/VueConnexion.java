/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr.modeles;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import gsb.rv.dr.entities.Visiteur;
import gsb.rv.dr.technique.Session;
import gsb.rv.dr.technique.ConnexionBD;
import gsb.rv.dr.technique.ConnexionException;
import gsb.rv.dr.modeles.ModelesGsbRv;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author root
 */
public class VueConnexion extends Dialog{
    
    public void start(Stage primaryStage) {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Authentification");
    dialog.setHeaderText("Saisir vos données de connexion");
    

    // Set the button types.
    ButtonType connexion = new ButtonType("Se Connecter", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(connexion, ButtonType.CANCEL);

    // Create the username and password labels and fields.
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

    // Enable/Disable login button depending on whether a username was entered.
    Node loginButton = dialog.getDialogPane().lookupButton(connexion);
    loginButton.setDisable(true);

    // Do some validation (using the Java 8 lambda syntax).
    username.textProperty().addListener((observable, oldValue, newValue) -> {
        loginButton.setDisable(newValue.trim().isEmpty());
    });

    dialog.getDialogPane().setContent(grid);

    // Request focus on the username field by default.
    Platform.runLater(() -> username.requestFocus());

    // Convert the result to a username-password-pair when the login button is clicked.
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == connexion) {
            return new Pair<>(username.getText(), password.getText());
        }
        return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();

    result.ifPresent(usernamePassword -> {
        System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
    });
    }
}

