package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Equipement;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class EquipementHandler {

    private static final List<Equipement> equipement = new ArrayList<>();

    public static VBox getControls() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Equipement> produitSelector = new ComboBox<>();
        produitSelector.setPromptText("Sélectionner un équipement");
        produitSelector.setItems(FXCollections.observableArrayList(equipement));

        TextField codeField = new TextField();
        codeField.setPromptText("Code de référence");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);

        Button addBtn = new Button("Ajouter");
        Button modifyBtn = new Button("Modifier");
        Button deleteBtn = new Button("Supprimer");
        Button clearBtn = new Button("Effacer tout");

        // Ajouter
        addBtn.setOnAction(e -> {
            String ref = codeField.getText().trim();
            if (ref.isEmpty()) {
                AtelierWindow.showAlert("Erreur", "Référence obligatoire.");
                return;
            }
            for (Equipement p : equipement) {
                if (p.refEquipement.equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            Equipement p = new Equipement(
                    ref,
                    descArea.getText().trim()
            );
            equipement.add(p);
            produitSelector.setItems(FXCollections.observableArrayList(equipement));
            clearFields(codeField, descArea);
        });

        // Sélection d'un produit existant
        produitSelector.setOnAction(e -> {
            Equipement selected = produitSelector.getValue();
            if (selected != null) {
                codeField.setText(selected.refEquipement);
                descArea.setText(selected.dEquipement);
                codeField.setDisable(true); // On évite de modifier la référence
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Equipement selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }

            selected.dEquipement = descArea.getText().trim();
            produitSelector.setItems(FXCollections.observableArrayList(equipement));
            clearFields(codeField, descArea);
            codeField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Equipement selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }
            equipement.remove(selected);
            produitSelector.setItems(FXCollections.observableArrayList(equipement));
            clearFields(codeField, descArea);
            codeField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(codeField, descArea);
            produitSelector.getSelectionModel().clearSelection();
            codeField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Produits :"),
                produitSelector,
                codeField,
                descArea,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField ref, TextArea desc) {
        ref.clear();
        desc.clear();
    }
}

