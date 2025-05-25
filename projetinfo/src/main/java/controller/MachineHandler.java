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

/*package controller;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Machine;
import vue.AtelierWindow;

public class MachineHandler {

    public static VBox getControls(GraphicsContext gc) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        TextField refField = new TextField();
        refField.setPromptText("Référence");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        TextField typeField = new TextField();
        typeField.setPromptText("Type");

        TextField coutField = new TextField();
        coutField.setPromptText("Coût");

        TextField xField = new TextField();
        xField.setPromptText("X");

        TextField yField = new TextField();
        yField.setPromptText("Y");

        TextField wField = new TextField();
        wField.setPromptText("Largeur");

        TextField hField = new TextField();
        hField.setPromptText("Hauteur");

        ColorPicker colorPicker = new ColorPicker(Color.LIGHTBLUE);

        Button addButton = new Button("Ajouter");
        Button updateButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        // --- Bouton Ajouter ---
        addButton.setOnAction(e -> {
            try {
                String ref = refField.getText().trim();
                String desc = descField.getText().trim();
                String type = typeField.getText().trim();
                float cout = Float.parseFloat(coutField.getText().trim());
                float x = Float.parseFloat(xField.getText());
                float y = Float.parseFloat(yField.getText());
                float w = Float.parseFloat(wField.getText());
                float h = Float.parseFloat(hField.getText());

                // Vérification si ref déjà existante
                for (Machine m : AtelierWindow.machines) {
                    if (m.getRefMachine().equals(ref)) {
                        AtelierWindow.showAlert("Erreur", "Une machine avec cette référence existe déjà.");
                        return;
                    }
                }

                Machine machine = new Machine(ref, desc, x, y, type, cout, w, h);
                AtelierWindow.machines.add(machine);
                drawMachine(gc, machine, colorPicker.getValue());

                clearFields(refField, descField, typeField, coutField, xField, yField, wField, hField, colorPicker);
                AtelierWindow.showAlert("Succès", "Machine ajoutée.");
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Champs numériques invalides.");
            }
        });

        // --- Bouton Modifier ---
        updateButton.setOnAction(e -> {
            try {
                String ref = refField.getText().trim();
                Machine machine = AtelierWindow.machines.stream()
                        .filter(m -> m.getRefMachine().equals(ref))
                        .findFirst()
                        .orElse(null);

                if (machine == null) {
                    AtelierWindow.showAlert("Erreur", "Machine introuvable.");
                    return;
                }

                machine.setdMachine(descField.getText().trim());
                machine.setType(typeField.getText().trim());
                machine.setCout(Float.parseFloat(coutField.getText().trim()));
                machine.setX(Float.parseFloat(xField.getText()));
                machine.setY(Float.parseFloat(yField.getText()));
                machine.setLargeur(Float.parseFloat(wField.getText()));
                machine.setHauteur(Float.parseFloat(hField.getText()));

                redrawAll(gc);

                clearFields(refField, descField, typeField, coutField, xField, yField, wField, hField, colorPicker);
                AtelierWindow.showAlert("Succès", "Machine modifiée.");
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Champs numériques invalides.");
            }
        });

        // --- Bouton Supprimer ---
        deleteButton.setOnAction(e -> {
            String ref = refField.getText().trim();
            boolean removed = AtelierWindow.machines.removeIf(m -> m.getRefMachine().equals(ref));

            if (removed) {
                redrawAll(gc);
                AtelierWindow.showAlert("Succès", "Machine supprimée.");
            } else {
                AtelierWindow.showAlert("Erreur", "Aucune machine trouvée avec cette référence.");
            }
        });

        box.getChildren().addAll(
                new Label("Gestion des Machines"),
                new HBox(5, refField, descField),
                new HBox(5, typeField, coutField),
                new HBox(5, xField, yField, wField, hField),
                new Label("Couleur pour dessin :"),
                colorPicker,
                new HBox(10, addButton, updateButton, deleteButton)
        );

        return box;
    }

    private static void drawMachine(GraphicsContext gc, Machine m, Color color) {
        gc.setFill(color);
        gc.fillRect(AtelierWindow.ATELIER_X + m.getX(), AtelierWindow.ATELIER_Y + m.getY(), m.getLargeur(), m.getHauteur());
        gc.setStroke(Color.BLACK);
        gc.strokeRect(AtelierWindow.ATELIER_X + m.getX(), AtelierWindow.ATELIER_Y + m.getY(), m.getLargeur(), m.getHauteur());
        gc.strokeText(m.getRefMachine(), AtelierWindow.ATELIER_X + m.getX() + 2, AtelierWindow.ATELIER_Y + m.getY() + 12);
    }

    private static void redrawAll(GraphicsContext gc) {
        gc.clearRect(0, 0, AtelierWindow.CANVAS_WIDTH, AtelierWindow.CANVAS_HEIGHT);
        for (Machine m : AtelierWindow.machines) {
            drawMachine(gc, m, Color.LIGHTBLUE); // couleur par défaut
        }
    }

    private static void clearFields(TextField... fields) {
        for (TextField tf : fields) {
            tf.clear();
        }
    }

    private static void clearFields(TextField refField, TextField descField, TextField typeField,
                                    TextField coutField, TextField xField, TextField yField,
                                    TextField wField, TextField hField, ColorPicker colorPicker) {
        clearFields(refField, descField, typeField, coutField, xField, yField, wField, hField);
        colorPicker.setValue(Color.LIGHTBLUE);
    }
}*/

