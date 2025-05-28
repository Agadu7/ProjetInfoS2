package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Atelier;
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

        // Ajout du ColorPicker
        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        Button suppButton = new Button("Supprimer Machine");
        suppButton.setOnAction(e -> {
            Machine selected = machineSelector.getValue();
            if (selected != null) {
                machines.remove(selected);
                gc.clearRect(AtelierWindow.ATELIER_X, AtelierWindow.ATELIER_Y, AtelierWindow.ATELIER_WIDTH, AtelierWindow.ATELIER_HEIGHT);
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

                    // Mise à jour des attributs de la machine sélectionnée
                    selected.type = name;
                    selected.dMachine = description;
                    selected.refMachine = reference;
                    selected.x = x;
                    selected.y = y;
                    selected.Largeur = width;
                    selected.Hauteur = height;
                    selected.cout = cout;

                    // Récupération de la couleur choisie
                    Color machineColor = colorPicker.getValue();

                    //Suppression de l'ancienne machine
                    machines.remove(selected);
                    gc.clearRect(AtelierWindow.ATELIER_X, AtelierWindow.ATELIER_Y, AtelierWindow.ATELIER_WIDTH, AtelierWindow.ATELIER_HEIGHT);

                    // Redessin de la machine avec les nouvelles valeurs
                    gc.setFill(machineColor);
                    gc.fillRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(AtelierWindow.ATELIER_X + x, AtelierWindow.ATELIER_Y + y, width, height);
                    gc.strokeText(name, AtelierWindow.ATELIER_X + 2 + x, AtelierWindow.ATELIER_Y + 2 + y);

                    // Reset champs
                    nameField.clear();
                    descField.clear();
                    refField.clear();
                    xField.clear();
                    yField.clear();
                    wField.clear();
                    hField.clear();
                    coutField.clear();
                    colorPicker.setValue(Color.LIGHTBLUE); // Reset couleur
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
                gc.strokeText(name, AtelierWindow.ATELIER_X + 2 + x, AtelierWindow.ATELIER_Y + 12 + y);

                // Reset champs
                nameField.clear();
                descField.clear();
                refField.clear();
                xField.clear();
                yField.clear();
                wField.clear();
                hField.clear();
                coutField.clear();

                colorPicker.setValue(Color.LIGHTBLUE); // Reset couleur

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

        EvenementMachine em = new EvenementMachine(null, "", "", "");

        Button Fiabilite = new Button("Calculer la fiabilité de la machine sélectionnée");
        styleButton(Fiabilite);
        Fiabilite.setOnAction(e -> {
            String ref = refField.getText().trim();
            System.out.println("Référence sélectionnée : " + ref);
            /*Machine selectedMachine = machines.stream()
                    .filter(m -> m.getRefMachine().equals(ref))
                    .findFirst()
                    .orElse(null);

            if (ref == null) {
                AtelierWindow.showAlert("Erreur", "Aucune machine trouvée avec cette référence.");
                return;
            }
            else{
                try {
                    // Charger les événements depuis le fichier
                    em.chargerEvenements("suiviMaintenance.txt");

                    // Récupérer les événements par machine
                    Map<String, List<EvenementMachine>> evenementsParMachine = em.evenementsParMachine;

                    // Calculer la fiabilité pour la machine avec la ref
                    double fiabilite = new Atelier().calculerFiabiliteMachineUnique(evenementsParMachine, ref);

                    System.out.println("Fiabilité de la machine " + ref + " : " + String.format("%.4f", fiabilite));
                } catch (IOException ex) {
                    System.out.println("Erreur lors du chargement des événements : " + ex.getMessage());
                }
            }*/ 
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

