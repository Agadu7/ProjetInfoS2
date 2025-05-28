package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.EvenementMachine;
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

        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        Button suppButton = new Button("Supprimer Machine");
        suppButton.setOnAction(e -> {
            Machine selected = machineSelector.getValue();
            if (selected != null) {
                // Efface uniquement la zone de la machine avec la couleur de fond
                gc.setFill(Color.LIGHTGRAY); // Met ici la couleur de fond de ton atelier
                gc.fillRect(AtelierWindow.ATELIER_X + selected.x, AtelierWindow.ATELIER_Y + selected.y - 12,
                            selected.Largeur, selected.Hauteur + 14); // Un peu plus haut et plus bas pour effacer le texte

                gc.setStroke(Color.BLACK);
                gc.strokeRect(AtelierWindow.ATELIER_X + selected.x, AtelierWindow.ATELIER_Y + selected.y,
                            selected.Largeur, selected.Hauteur);

                machines.remove(selected);
                machineSelector.getSelectionModel().clearSelection();
            } else {
                AtelierWindow.showAlert("Erreur", "Aucune machine sélectionnée.");
            }
        });



        Button modifButton = new Button("Modifier Machine");
        modifButton.setOnAction(e -> {
            Machine selected = machineSelector.getValue();
            if (selected != null) {
                try {
                    String name = nameField.getText().trim();
                    String description = descField.getText().trim();
                    String reference = refField.getText().trim();

                    float x = Float.parseFloat(xField.getText());
                    float y = Float.parseFloat(yField.getText());
                    float width = Float.parseFloat(wField.getText());
                    float height = Float.parseFloat(hField.getText());
                    float cout = Float.parseFloat(coutField.getText());

                    if (x < 0 || y < 0 || width <= 0 || height <= 0 ||
                            x + width > AtelierWindow.ATELIER_WIDTH || y + height > AtelierWindow.ATELIER_HEIGHT) {
                        AtelierWindow.showAlert("Erreur", "Dimensions hors de l'atelier.");
                        return;
                    }
                    if(Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(width) || Float.isNaN(height) || Float.isNaN(cout)) {
                        AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
                        return;
                    }

                    gc.clearRect(AtelierWindow.ATELIER_X + selected.x, AtelierWindow.ATELIER_Y + selected.y, selected.Largeur, selected.Hauteur);

                    selected.type = name;
                    selected.dMachine = description;
                    selected.refMachine = reference;
                    selected.x = x;
                    selected.y = y;
                    selected.Largeur = width;
                    selected.Hauteur = height;
                    selected.cout = cout;

                    Color machineColor = colorPicker.getValue();

                    gc.setFill(machineColor);
                    gc.fillRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                    gc.strokeText(name, AtelierWindow.ATELIER_X + 2 + x, AtelierWindow.ATELIER_Y + 2 + y);

                    nameField.clear();
                    descField.clear();
                    refField.clear();
                    xField.clear();
                    yField.clear();
                    wField.clear();
                    hField.clear();
                    coutField.clear();
                    colorPicker.setValue(Color.LIGHTBLUE);

                    AtelierWindow.showAlert("Succès", "Machine modifiée !");
                } catch (NumberFormatException ex) {
                    AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
                }
            }
        });

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

                if (x < 0 || y < 0 || width <= 0 || height <= 0 ||
                        x + width > AtelierWindow.ATELIER_WIDTH || y + height > AtelierWindow.ATELIER_HEIGHT) {
                    AtelierWindow.showAlert("Erreur", "Dimensions hors de l'atelier.");
                    return;
                }
                if(Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(width) || Float.isNaN(height) || Float.isNaN(cout)) {
                    AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
                    return;
                }

                for (Machine m : machines) {
                    if (m.overlaps(x, y, width, height)) {
                        AtelierWindow.showAlert("Erreur", "Une machine existe déjà à cet emplacement.");
                        return;
                    }
                }

                Color machineColor = colorPicker.getValue();

                Machine machine = new Machine(name, description, x, y, reference, width, height, cout);
                machines.add(machine);

                gc.setFill(machineColor);
                gc.fillRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                gc.strokeText(name, AtelierWindow.ATELIER_X + 2 + x, AtelierWindow.ATELIER_Y + 12 + y);

                nameField.clear();
                descField.clear();
                refField.clear();
                xField.clear();
                yField.clear();
                wField.clear();
                hField.clear();
                coutField.clear();

                colorPicker.setValue(Color.LIGHTBLUE);

                AtelierWindow.showAlert("Succès", "Machine ajoutée !");
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Valeurs numériques invalides.");
            }
        });

        machineSelector.setOnAction(e -> {
            Machine selected = machineSelector.getValue();
            if (selected != null) {
                nameField.setText(selected.type);
                descField.setText(selected.dMachine);
                refField.setText(selected.refMachine);
                xField.setText(String.valueOf(selected.x));
                yField.setText(String.valueOf(selected.y));
                wField.setText(String.valueOf(selected.Largeur));
                hField.setText(String.valueOf(selected.Hauteur));
                coutField.setText(String.valueOf(selected.cout));
                refField.setDisable(true);
            }
        });

        EvenementMachine em = new EvenementMachine(null, "", "", "");

        Button Fiabilite = new Button("Calculer la fiabilité de la machine sélectionnée");
        styleButton(Fiabilite);
        Fiabilite.setOnAction(e -> {
            String ref = refField.getText();
            System.out.println("Référence sélectionnée : " + ref);
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
                new HBox(10,addButton,suppButton, modifButton),
                new Separator(),
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