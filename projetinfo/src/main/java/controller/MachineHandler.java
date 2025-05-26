package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Machine;
import vue.AtelierWindow;

public class MachineHandler {

    public static VBox getControls(GraphicsContext gc) {

        final ObservableList<Machine> machines = FXCollections.observableArrayList();

        ComboBox<Machine> machineSelector = new ComboBox<>();
        machineSelector.setPromptText("Sélectionner une machine");
        machineSelector.setItems(machines);

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

        TextField coutField = new TextField();
        coutField.setPromptText("Coût (€/heure)");

        // Ajout du ColorPicker
        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        Button addButton = new Button("Ajouter Machine");

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String description = descField.getText().trim();
                String reference = refField.getText().trim();

                float x = Float.parseFloat(xField.getText());
                float y = Float.parseFloat(yField.getText());
                float width = Float.parseFloat(wField.getText());
                float height = Float.parseFloat(hField.getText());
                float cout = Float.parseFloat(coutField.getText());

                // Vérification des bornes
                if (x < 0 || y < 0 || width <= 0 || height <= 0 ||
                        x + width > AtelierWindow.ATELIER_WIDTH || y + height > AtelierWindow.ATELIER_HEIGHT) {
                    AtelierWindow.showAlert("Erreur", "Dimensions hors de l'atelier.");
                    return;
                }
                if(Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(width) || Float.isNaN(height) || Float.isNaN(cout)) {
                    AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
                    return;
                }

                // Vérification des chevauchements
                for (Machine m : machines) {
                    if (m.overlaps(x, y, width, height)) {
                        AtelierWindow.showAlert("Erreur", "Une machine existe déjà à cet emplacement.");
                        return;
                    }
                }

                Machine newMachine = new Machine(
                        reference,
                        description,
                        x,
                        y,
                        name,
                        cout,
                        width,
                        height
                );

                // Récupération de la couleur choisie
                Color machineColor = colorPicker.getValue();

                // Création et ajout
                Machine machine = new Machine(name, description, x, y, reference, width, height, cout);
                machines.add(machine);

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
                coutField.clear();

                // Remise à la couleur par défaut
                colorPicker.setValue(Color.LIGHTBLUE);

                machines.add(newMachine);

                AtelierWindow.showAlert("Succès", "Machine ajoutée !");
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
            }
        });

        // Sélection d'une machine existante
        machineSelector.setOnAction(e -> {
            Machine selected = machineSelector.getValue();
            if (selected != null) {
                nameField.setText(selected.refMachine);
                descField.setText(selected.dMachine);
                nameField.setText(selected.type);
                xField.setText(String.valueOf(selected.x));
                yField.setText(String.valueOf(selected.y));
                wField.setText(String.valueOf(selected.Largeur));
                hField.setText(String.valueOf(selected.Hauteur));
                coutField.setText(String.valueOf(selected.cout));
                refField.setDisable(true); // On évite de modifier la référence
            }
        });

        Button Fiabilite = new Button("Calculer la fiabilité de la machine sélectionnée");
        styleButton(Fiabilite);
        Fiabilite.setOnAction(e -> {
            String ref = refField.getText().trim();
            Machine selectedMachine = machines.stream()
                    .filter(m -> m.getRefMachine().equals(ref))
                    .findFirst()
                    .orElse(null);

            if (selectedMachine == null) {
                AtelierWindow.showAlert("Erreur", "Aucune machine trouvée avec cette référence.");
                return;
            }
            else{
                //double fiabilite = selectedMachine.calculerFiabilite();
                double fiabilite = 95.0; // Valeur fictive pour l'exemple
                AtelierWindow.showAlert("Fiabilité", "La fiabilité de la machine " + selectedMachine.getRefMachine() + " est de " + fiabilite + "%.");
            }
        });

        box.getChildren().addAll(
                machineSelector,
                new Label("Ajouter une machine :"),
                new HBox(5, nameField, refField),
                new HBox(5, xField, yField, wField, hField),
                descField,
                new Label("Prix de fonctionnement (€/heure) :"),
                coutField,
                new Label("Couleur :"),
                colorPicker,
                addButton,
                Fiabilite
        );

        return box;
    }
    private static void styleButton(Button btn) {
        btn.setStyle("-fx-background-color: #2980b9; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 10;");
    }
}

