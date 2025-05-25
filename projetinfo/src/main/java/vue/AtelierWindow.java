package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

import controller.GammeHandler;
import controller.GenericHandler;
import controller.MachineHandler;
import controller.ProduitHandler;

public class AtelierWindow {
    public static final double ATELIER_X = 50;
    public static final double ATELIER_Y = 50;
    public static final double ATELIER_WIDTH = 600;
    public static final double ATELIER_HEIGHT = 300;

    public static class Machine {
        public double x, y, width, height;
        public String name;

        public Machine(String name, double x, double y, double width, double height) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean overlaps(double otherX, double otherY, double otherW, double otherH) {
            return x < otherX + otherW && x + width > otherX && y < otherY + otherH && y + height > otherY;
        }
    }

    public static final List<Machine> machines = new ArrayList<>();

    public static void display(String nomAtelier) {
        Stage window = new Stage();
        window.setTitle("Atelier : " + nomAtelier);

        // Plein écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        Canvas canvas = new Canvas(screenWidth * 0.6, screenHeight * 0.8);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawAtelier(gc);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f4f8, #dfe6ec);");

        Label chefLabel = new Label("Chef : Aucun");
        chefLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField chefField = new TextField();
        chefField.setPromptText("Chef d'atelier");

        Button addChefBtn = new Button("Définir Chef");
        styleButton(addChefBtn);
        addChefBtn.setOnAction(e -> {
            chefLabel.setText("Chef : " + chefField.getText());
            chefField.clear();
        });

        ComboBox<String> entitySelector = new ComboBox<>();
        entitySelector.getItems().addAll("Machine", "Gamme", "Produit", "Employé", "Poste");
        entitySelector.setPromptText("Sélectionner un élément à gérer");
        entitySelector.setStyle("-fx-font-size: 14px;");

        VBox entityControls = new VBox(10);

        entitySelector.setOnAction(e -> {
            entityControls.getChildren().clear();
            String selected = entitySelector.getValue();
            switch (selected) {
                case "Machine":
                    entityControls.getChildren().add(MachineHandler.getControls(gc));
                    break;
                case "Gamme":
                    entityControls.getChildren().add(GammeHandler.getControls());
                    break;
                case "Produit":
                    entityControls.getChildren().add(ProduitHandler.getControls());
                    break;
                case "Employé":
                    entityControls.getChildren().add(GenericHandler.getControls("Employé"));
                    break;
                case "Poste":
                    entityControls.getChildren().add(GenericHandler.getControls("Poste"));
                    break;
            }
        });

        Button backToHomeBtn = new Button("Retour à l'accueil");
        styleButton(backToHomeBtn);
        backToHomeBtn.setOnAction(e -> {
            window.close();
            App.display();
        });

        VBox inputBox = new VBox(15,
                chefLabel,
                new HBox(10, chefField, addChefBtn),
                new Separator(),
                entitySelector,
                entityControls,
                backToHomeBtn
        );
        inputBox.setAlignment(Pos.TOP_LEFT);
        inputBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(inputBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(screenWidth * 0.3);
        scrollPane.setStyle("-fx-background: transparent;");

        HBox content = new HBox(40, canvas, scrollPane);
        content.setPadding(new Insets(10));

        mainLayout.getChildren().add(content);

        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        window.setScene(scene);
        window.setMaximized(true); // plein écran
        window.show();
    }

    private static void drawAtelier(GraphicsContext gc) {
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(ATELIER_X, ATELIER_Y, ATELIER_WIDTH, ATELIER_HEIGHT);
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void styleButton(Button btn) {
        btn.setStyle("-fx-background-color: #2980b9; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 10;");
    }
}

