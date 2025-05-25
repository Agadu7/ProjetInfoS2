package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class GammeHandler {

    static class Gamme {
        String reference;
        String description;
        List<String> produits = new ArrayList<>();
        List<String> machines = new ArrayList<>();

        Gamme(String reference, String description, List<String> produits, List<String> machines) {
            this.reference = reference;
            this.description = description;
            this.produits = new ArrayList<>(produits);
            this.machines = new ArrayList<>(machines);
        }

        @Override
        public String toString() {
            return reference;
        }
    }

    private static final List<Gamme> gammes = new ArrayList<>();

    public static VBox getControls() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Gamme> gammeSelector = new ComboBox<>();
        gammeSelector.setPromptText("Sélectionner une gamme");
        gammeSelector.setItems(FXCollections.observableArrayList(gammes));

        TextField refField = new TextField();
        refField.setPromptText("Référence");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        TextField produitsField = new TextField();
        produitsField.setPromptText("Produits (séparés par des virgules)");

        TextField machinesField = new TextField();
        machinesField.setPromptText("Machines (séparées par des virgules)");

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
            for (Gamme g : gammes) {
                if (g.reference.equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            Gamme g = new Gamme(
                    ref,
                    descField.getText().trim(),
                    List.of(produitsField.getText().split(",")),
                    List.of(machinesField.getText().split(","))
            );
            gammes.add(g);
            gammeSelector.setItems(FXCollections.observableArrayList(gammes));
            clearFields(refField, descField, produitsField, machinesField);
        });

        // Sélection d'une gamme existante
        gammeSelector.setOnAction(e -> {
            Gamme selected = gammeSelector.getValue();
            if (selected != null) {
                refField.setText(selected.reference);
                descField.setText(selected.description);
                produitsField.setText(String.join(",", selected.produits));
                machinesField.setText(String.join(",", selected.machines));
                refField.setDisable(true); // On évite de modifier la référence
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Gamme selected = gammeSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucune gamme sélectionnée.");
                return;
            }

            selected.description = descField.getText().trim();
            selected.produits = List.of(produitsField.getText().split(","));
            selected.machines = List.of(machinesField.getText().split(","));
            gammeSelector.setItems(FXCollections.observableArrayList(gammes));
            clearFields(refField, descField, produitsField, machinesField);
            refField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Gamme selected = gammeSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucune gamme sélectionnée.");
                return;
            }
            gammes.remove(selected);
            gammeSelector.setItems(FXCollections.observableArrayList(gammes));
            clearFields(refField, descField, produitsField, machinesField);
            refField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(refField, descField, produitsField, machinesField);
            gammeSelector.getSelectionModel().clearSelection();
            refField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Gammes :"),
                gammeSelector,
                refField,
                descField,
                produitsField,
                machinesField,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField ref, TextField desc, TextField prod, TextField mach) {
        ref.clear();
        desc.clear();
        prod.clear();
        mach.clear();
    }
}
