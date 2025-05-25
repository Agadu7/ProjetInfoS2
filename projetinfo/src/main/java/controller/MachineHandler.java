package controller;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import vue.AtelierWindow;

public class MachineHandler {

    public static VBox getControls(GraphicsContext gc) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        TextField refField = new TextField();
        refField.setPromptText("Référence");

        TextField xField = new TextField();
        xField.setPromptText("X");

        TextField yField = new TextField();
        yField.setPromptText("Y");

        TextField wField = new TextField();
        wField.setPromptText("Largeur");

        TextField hField = new TextField();
        hField.setPromptText("Hauteur");

        // Ajout du ColorPicker
        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        Button addButton = new Button("Ajouter Machine");

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                //String description = descField.getText().trim();
                //String reference = refField.getText().trim();

                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                double width = Double.parseDouble(wField.getText());
                double height = Double.parseDouble(hField.getText());

                // Vérification des bornes
                if (x < 0 || y < 0 || width <= 0 || height <= 0 ||
                        x + width > AtelierWindow.ATELIER_WIDTH || y + height > AtelierWindow.ATELIER_HEIGHT) {
                    AtelierWindow.showAlert("Erreur", "Dimensions hors de l'atelier.");
                    return;
                }

                // Vérification des chevauchements
                for (AtelierWindow.Machine m : AtelierWindow.machines) {
                    if (m.overlaps(x, y, width, height)) {
                        AtelierWindow.showAlert("Erreur", "Une machine existe déjà à cet emplacement.");
                        return;
                    }
                }

                // Récupération de la couleur choisie
                Color machineColor = colorPicker.getValue();

                // Création et ajout
                AtelierWindow.Machine machine = new AtelierWindow.Machine(name, x, y, width, height);
                AtelierWindow.machines.add(machine);

                // Dessin avec la couleur sélectionnée
                gc.setFill(machineColor);
                gc.fillRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                gc.strokeText(name, AtelierWindow.ATELIER_X + x + 2, AtelierWindow.ATELIER_Y + y + 12);

                // Reset champs
                nameField.clear();
                descField.clear();
                refField.clear();
                xField.clear();
                yField.clear();
                wField.clear();
                hField.clear();

                // Remise à la couleur par défaut
                colorPicker.setValue(Color.LIGHTBLUE);

                AtelierWindow.showAlert("Succès", "Machine ajoutée !");
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
            }
        });

        box.getChildren().addAll(
                new Label("Ajouter une machine :"),
                new HBox(5, nameField, refField),
                new HBox(5, xField, yField, wField, hField),
                descField,
                new Label("Couleur :"),
                colorPicker,
                addButton
        );

        return box;
    }
}
