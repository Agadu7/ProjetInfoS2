package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Produit;
import model.Gamme;
import vue.AtelierWindow;

public class ProduitHandler {

    public static VBox getControls(Gamme gamme) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        // Récupération des produits de la gamme
        ComboBox<Produit> produitSelector = new ComboBox<>();
        produitSelector.setPromptText("Sélectionner un produit");
        produitSelector.setItems(FXCollections.observableArrayList(gamme.getListeProduit()));

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

            for (Produit p : gamme.getListeProduit()) {
                if (p.getCodeProduit().equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            Produit p = new Produit(
                    ref,
                    descArea.getText().trim()
            );
            gamme.getListeProduit().add(p);
            produitSelector.setItems(FXCollections.observableArrayList(gamme.getListeProduit()));
            clearFields(codeField, descArea);
        });

        // Sélection d'un produit existant
        produitSelector.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected != null) {
                codeField.setText(selected.getCodeProduit());
                descArea.setText(selected.getdProduit());
                codeField.setDisable(true);
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }

            selected.setdProduit(descArea.getText().trim());
            produitSelector.setItems(FXCollections.observableArrayList(gamme.getListeProduit()));
            clearFields(codeField, descArea);
            codeField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Produit selected = produitSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun produit sélectionné.");
                return;
            }

            gamme.getListeProduit().remove(selected);
            produitSelector.setItems(FXCollections.observableArrayList(gamme.getListeProduit()));
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
                new Label("Gestion des Produits de la Gamme :"),
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
