package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Operation;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class OperationHandler {

    private static final List<Operation> operations = new ArrayList<>();

    public static VBox getControls() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Operation> operationSelector = new ComboBox<>();
        operationSelector.setPromptText("Sélectionner une opération");
        operationSelector.setItems(FXCollections.observableArrayList(operations));

        TextField refField = new TextField();
        refField.setPromptText("Référence de l'opération");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(2);

        TextField equipementField = new TextField();
        equipementField.setPromptText("Référence équipement");

        TextField dureeField = new TextField();
        dureeField.setPromptText("Durée (en heures)");

        Button addBtn = new Button("Ajouter");
        Button modifyBtn = new Button("Modifier");
        Button deleteBtn = new Button("Supprimer");
        Button clearBtn = new Button("Effacer tout");

        // Ajouter
        addBtn.setOnAction(e -> {
            String ref = refField.getText().trim();
            if (ref.isEmpty()) {
                AtelierWindow.showAlert("Erreur", "Référence obligatoire.");
                return;
            }
            for (Operation op : operations) {
                if (op.refOperation.equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            try {
                float duree = Float.parseFloat(dureeField.getText().trim());

                Operation op = new Operation(
                        ref,
                        descArea.getText().trim(),
                        equipementField.getText().trim(),
                        duree
                );
                operations.add(op);
                operationSelector.setItems(FXCollections.observableArrayList(operations));
                clearFields(refField, descArea, equipementField, dureeField);
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Durée invalide.");
            }
        });

        // Sélection d'une opération existante
        operationSelector.setOnAction(e -> {
            Operation selected = operationSelector.getValue();
            if (selected != null) {
                refField.setText(selected.refOperation);
                descArea.setText(selected.dOperation);
                equipementField.setText(selected.refEquipement);
                dureeField.setText(String.valueOf(selected.dureeOperation));
                refField.setDisable(true);
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Operation selected = operationSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucune opération sélectionnée.");
                return;
            }

            try {
                selected.setdOperation(descArea.getText().trim());
                selected.setRefEquipement(equipementField.getText().trim());
                selected.setDureeOperation(Float.parseFloat(dureeField.getText().trim()));
                operationSelector.setItems(FXCollections.observableArrayList(operations));
                clearFields(refField, descArea, equipementField, dureeField);
                refField.setDisable(false);
            } catch (NumberFormatException ex) {
                AtelierWindow.showAlert("Erreur", "Durée invalide.");
            }
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Operation selected = operationSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucune opération sélectionnée.");
                return;
            }

            operations.remove(selected);
            operationSelector.setItems(FXCollections.observableArrayList(operations));
            clearFields(refField, descArea, equipementField, dureeField);
            refField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(refField, descArea, equipementField, dureeField);
            operationSelector.getSelectionModel().clearSelection();
            refField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Opérations :"),
                operationSelector,
                refField,
                descArea,
                equipementField,
                dureeField,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField ref, TextArea desc, TextField eq, TextField duree) {
        ref.clear();
        desc.clear();
        eq.clear();
        duree.clear();
    }
}

