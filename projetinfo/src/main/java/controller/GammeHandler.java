package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Gamme;
import model.Machine;
import model.Operation;
import model.Produit;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class GammeHandler {

    private static final List<Gamme> gammes = new ArrayList<>();

    // À adapter avec tes données disponibles
    private static final List<Machine> allMachines = new ArrayList<>();
    private static final List<Operation> allOperations = new ArrayList<>();
    private static final List<Produit> allProduits = new ArrayList<>();

    public static VBox getControls() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Gamme> gammeSelector = new ComboBox<>();
        gammeSelector.setPromptText("Sélectionner une gamme");
        gammeSelector.setItems(FXCollections.observableArrayList(gammes));

        TextField refField = new TextField();
        refField.setPromptText("Référence de la gamme");

        ListView<Machine> machineList = new ListView<>(FXCollections.observableArrayList(allMachines));
        machineList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        machineList.setPrefHeight(100);
        machineList.setPlaceholder(new Label("Aucune machine"));

        ListView<Produit> produitList = new ListView<>(FXCollections.observableArrayList(allProduits));
        produitList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        produitList.setPrefHeight(100);
        produitList.setPlaceholder(new Label("Aucun produit"));

        ListView<Operation> operationList = new ListView<>(FXCollections.observableArrayList(allOperations));
        operationList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        operationList.setPrefHeight(100);
        operationList.setPlaceholder(new Label("Aucune opération"));

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
                if (g.getRefGamme().equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            ArrayList<Machine> selectedMachines = new ArrayList<>(machineList.getSelectionModel().getSelectedItems());
            ArrayList<Produit> selectedProduits = new ArrayList<>(produitList.getSelectionModel().getSelectedItems());
            ArrayList<Operation> selectedOperations = new ArrayList<>(operationList.getSelectionModel().getSelectedItems());

            Gamme newGamme = new Gamme(selectedMachines, ref, selectedOperations, selectedProduits);
            gammes.add(newGamme);
            gammeSelector.setItems(FXCollections.observableArrayList(gammes));
            clearFields(refField, machineList, produitList, operationList);
        });

        // Sélectionner une gamme
        gammeSelector.setOnAction(e -> {
            Gamme selected = gammeSelector.getValue();
            if (selected != null) {
                refField.setText(selected.getRefGamme());
                refField.setDisable(true);
                machineList.getSelectionModel().clearSelection();
                produitList.getSelectionModel().clearSelection();
                operationList.getSelectionModel().clearSelection();

                selected.getListeMachine().forEach(machineList.getSelectionModel()::select);
                selected.getListeProduit().forEach(produitList.getSelectionModel()::select);
                selected.getListeOperation().forEach(operationList.getSelectionModel()::select);
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Gamme selected = gammeSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucune gamme sélectionnée.");
                return;
            }

            selected.setListeMachine(new ArrayList<>(machineList.getSelectionModel().getSelectedItems()));
            selected.setListeProduit(new ArrayList<>(produitList.getSelectionModel().getSelectedItems()));
            selected.setListeOperation(new ArrayList<>(operationList.getSelectionModel().getSelectedItems()));

            gammeSelector.setItems(FXCollections.observableArrayList(gammes));
            clearFields(refField, machineList, produitList, operationList);
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
            clearFields(refField, machineList, produitList, operationList);
            refField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(refField, machineList, produitList, operationList);
            gammeSelector.getSelectionModel().clearSelection();
            refField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Gammes :"),
                gammeSelector,
                refField,
                new Label("Machines :"), machineList,
                new Label("Produits :"), produitList,
                new Label("Opérations :"), operationList,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField ref, ListView<?> mList, ListView<?> pList, ListView<?> oList) {
        ref.clear();
        mList.getSelectionModel().clearSelection();
        pList.getSelectionModel().clearSelection();
        oList.getSelectionModel().clearSelection();
    }
}
