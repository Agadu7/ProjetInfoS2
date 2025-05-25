package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class ProduitHandler {

    public static class Produit {
        String reference;
        String nom;
        String description;
        List<String> machinesNecessaires;

        Produit(String reference, String nom, String description, List<String> machinesNecessaires) {
            this.reference = reference;
            this.nom = nom;
            this.description = description;
            this.machinesNecessaires = new ArrayList<>(machinesNecessaires);
        }

        @Override
        public String toString() {
            return reference + " - " + nom;
        }
    }

    private static final List<Produit> produits = new ArrayList<>();

    public static VBox getControls() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Produit> produitSelector = new ComboBox<>();
        produitSelector.setPromptText("Sélectionner un produit");
        produitSelector.setItems(FXCollections.observableArrayList(produits));

        TextField refField = new TextField();
        refField.setPromptText("Référence");

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);

        TextField machinesField = new TextField();
        machinesField.setPromptText("Machines nécessaires (séparées par des virgules)");

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
            for (Produit p : produits) {
                if (p.reference.equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            Produit p = new Produit(
                    ref,
                    nomField.getText().trim(),
                    descArea.getText().trim(),
                    List.of(machinesField.getText().split(","))
            );
            produits.add(p);
            produitSelector.setItems(FXCollections.observableArrayList(produits));
            clearFields(refField, nomField, descArea, machinesField);
        });

        // Sélection d'un produit existant
        produitSelector.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected != null) {
                refField.setText(selected.reference);
                nomField.setText(selected.nom);
                descArea.setText(selected.description);
                machinesField.setText(String.join(",", selected.machinesNecessaires));
                refField.setDisable(true); // On évite de modifier la référence
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }

            selected.nom = nomField.getText().trim();
            selected.description = descArea.getText().trim();
            selected.machinesNecessaires = List.of(machinesField.getText().split(","));
            produitSelector.setItems(FXCollections.observableArrayList(produits));
            clearFields(refField, nomField, descArea, machinesField);
            refField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }
            produits.remove(selected);
            produitSelector.setItems(FXCollections.observableArrayList(produits));
            clearFields(refField, nomField, descArea, machinesField);
            refField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(refField, nomField, descArea, machinesField);
            produitSelector.getSelectionModel().clearSelection();
            refField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Produits :"),
                produitSelector,
                refField,
                nomField,
                descArea,
                machinesField,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField ref, TextField nom, TextArea desc, TextField mach) {
        ref.clear();
        nom.clear();
        desc.clear();
        mach.clear();
    }
}

