package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Machine;
import model.Poste;
import vue.AtelierWindow;

import java.util.ArrayList;
import java.util.List;

public class PosteHandler {

    private static final List<Poste> postes = new ArrayList<>();

    public static VBox getControls(List<Machine> machinesDisponibles) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Poste> posteSelector = new ComboBox<>();
        posteSelector.setPromptText("Sélectionner un poste");
        posteSelector.setItems(FXCollections.observableArrayList(postes));

        TextField refField = new TextField();
        refField.setPromptText("Référence du poste");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);

        //Sélection multiple de machines associées
        ListView<Machine> machineListView = new ListView<>();
        machineListView.setItems(FXCollections.observableArrayList(machinesDisponibles));
        machineListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        machineListView.setPrefHeight(100);

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

            for (Poste p : postes) {
                if (p.getRefPoste().equals(ref)) {
                    AtelierWindow.showAlert("Erreur", "Référence déjà existante.");
                    return;
                }
            }

            Poste newPoste = new Poste(
                    ref,
                    descArea.getText().trim(),
                    new ArrayList<>(machineListView.getSelectionModel().getSelectedItems())
            );

            postes.add(newPoste);
            posteSelector.setItems(FXCollections.observableArrayList(postes));
            clearFields(refField, descArea, machineListView);
        });

        // Sélection
        posteSelector.setOnAction(e -> {
            Poste selected = posteSelector.getValue();
            if (selected != null) {
                refField.setText(selected.getRefPoste());
                descArea.setText(selected.getdPoste());
                refField.setDisable(true);

                machineListView.getSelectionModel().clearSelection();
                for (Machine m : selected.getListeMachine()) {
                    machineListView.getSelectionModel().select(m);
                }
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Poste selected = posteSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun poste sélectionné.");
                return;
            }

            selected.setdPoste(descArea.getText().trim());
            selected.setListeMachine(new ArrayList<>(machineListView.getSelectionModel().getSelectedItems()));
            posteSelector.setItems(FXCollections.observableArrayList(postes));
            clearFields(refField, descArea, machineListView);
            refField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Poste selected = posteSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun poste sélectionné.");
                return;
            }

            postes.remove(selected);
            posteSelector.setItems(FXCollections.observableArrayList(postes));
            clearFields(refField, descArea, machineListView);
            refField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(refField, descArea, machineListView);
            posteSelector.getSelectionModel().clearSelection();
            refField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Postes :"),
                posteSelector,
                refField,
                descArea,
                new Label("Machines associées :"),
                machineListView,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField refField, TextArea descArea, ListView<Machine> machineList) {
        refField.clear();
        descArea.clear();
        machineList.getSelectionModel().clearSelection();
    }
}

