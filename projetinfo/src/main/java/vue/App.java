package vue;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        display(); // appel de la méthode personnalisée
    }

    private static String btnStyle() {
        return "-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 10;";
    }

    private static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    public static void display() {
    Stage stage = new Stage();
    stage.setTitle("Gestion des Ateliers");

    // ======= Titre =======
    Label titre = new Label("Gestion des ateliers");
    titre.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
    titre.setStyle("-fx-text-fill: #2c3e50;");

    // ======= Créer un atelier =======
    Button creerAtelier = new Button("Créer un atelier");
    creerAtelier.setOnAction(e -> {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Création d'atelier");
        dialog.setHeaderText("Entrez le nom du nouvel atelier :");
        dialog.setContentText("Nom de l'atelier :");

        dialog.showAndWait().ifPresent(nomAtelier -> {
            if (!nomAtelier.trim().isEmpty()) {
                AtelierWindow.display(nomAtelier);
                stage.close();
            } else {
                showWarning("Le nom de l'atelier ne peut pas être vide.");
            }
        });
    });

    // ======= Liste des ateliers =======
    ComboBox<String> ateliers = new ComboBox<>();
    ateliers.getItems().addAll("Atelier A", "Atelier B");
    ateliers.setPromptText("Choisir un atelier");

    // ======= Ouvrir atelier existant =======
    Button ouvrirAtelier = new Button("Ouvrir l'atelier sélectionné");
    ouvrirAtelier.setOnAction(e -> {
        String selection = ateliers.getValue();
        if (selection != null) {
            AtelierWindow.display(selection);
            stage.close();
        } else {
            showWarning("Veuillez sélectionner un atelier.");
        }
    });

    // ======= Supprimer atelier =======
    Button supprimerAtelier = new Button("Supprimer l'atelier sélectionné");
    supprimerAtelier.setOnAction(e -> {
        String selection = ateliers.getValue();
        if (selection != null) {
            Alert confirm = new Alert(Alert.AlertType.WARNING);
            confirm.setTitle("Confirmation de suppression");
            confirm.setHeaderText("Voulez-vous vraiment supprimer l'atelier : " + selection + " ?");
            confirm.setContentText("Cette action est irréversible.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ateliers.getItems().remove(selection);
                    ateliers.setValue(null);

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Suppression réussie");
                    info.setHeaderText(null);
                    info.setContentText("Atelier supprimé avec succès.");
                    info.showAndWait();
                }
            });
        } else {
            showWarning("Veuillez sélectionner un atelier à supprimer.");
        }
    });

    // ======= Bouton quitter =======
    Button quitter = new Button("Quitter");
    quitter.setOnAction(e -> stage.close());

    // ======= Style commun =======
    creerAtelier.setStyle(btnStyle());
    ouvrirAtelier.setStyle(btnStyle());
    supprimerAtelier.setStyle(btnStyle());
    quitter.setStyle(btnStyle());

    // ======= Layout central =======
    VBox centerBox = new VBox(20, creerAtelier, ateliers, ouvrirAtelier, supprimerAtelier);
    centerBox.setAlignment(Pos.CENTER);

    // ======= Layout principal =======
    BorderPane root = new BorderPane();
    root.setTop(titre);
    root.setCenter(centerBox);
    root.setBottom(quitter);

    BorderPane.setAlignment(titre, Pos.CENTER);
    BorderPane.setMargin(titre, new Insets(30, 0, 10, 0));
    BorderPane.setAlignment(quitter, Pos.CENTER);
    BorderPane.setMargin(quitter, new Insets(30));

    root.setStyle("-fx-background-color: linear-gradient(to bottom, #ecf0f1, #bdc3c7);");

    // ======= Responsive Sizing =======
    creerAtelier.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 4));
    ouvrirAtelier.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 4));
    supprimerAtelier.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 4));
    quitter.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 4));
    ateliers.prefWidthProperty().bind(Bindings.divide(stage.widthProperty(), 4));
    titre.fontProperty().bind(Bindings.createObjectBinding(
            () -> Font.font("Arial", FontWeight.EXTRA_BOLD, stage.getHeight() / 20),
            stage.heightProperty()
    ));

    // ======= Scene =======
    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
}



    public static void main(String[] args) {
        launch(args); // lance l'application
    }
}
