package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Operateur;
import vue.AtelierWindow;

import java.util.List;

public class OperateurHandler {

    public static VBox getControls(List<Operateur> operateurs) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        ComboBox<Operateur> operateurSelector = new ComboBox<>();
        operateurSelector.setPromptText("Sélectionner un opérateur");
        operateurSelector.setItems(FXCollections.observableArrayList(operateurs));

        TextField codeField = new TextField();
        codeField.setPromptText("Code");

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");

        TextArea competencesArea = new TextArea();
        competencesArea.setPromptText("Compétences");
        competencesArea.setPrefRowCount(3);

        Button addBtn = new Button("Ajouter");
        Button modifyBtn = new Button("Modifier");
        Button deleteBtn = new Button("Supprimer");
        Button clearBtn = new Button("Effacer tout");

        // Ajouter
        addBtn.setOnAction(e -> {
            String code = codeField.getText().trim();
            if (code.isEmpty()) {
                AtelierWindow.showAlert("Erreur", "Code obligatoire.");
                return;
            }
            for (Operateur op : operateurs) {
                if (op.getCode().equals(code)) {
                    AtelierWindow.showAlert("Erreur", "Code déjà existant.");
                    return;
                }
            }

            Operateur o = new Operateur(
                    code,
                    nomField.getText().trim(),
                    prenomField.getText().trim(),
                    competencesArea.getText().trim()
            );
            operateurs.add(o);
            operateurSelector.setItems(FXCollections.observableArrayList(operateurs));
            clearFields(codeField, nomField, prenomField, competencesArea);
        });

        // Sélection
        operateurSelector.setOnAction(e -> {
            Operateur selected = operateurSelector.getValue();
            if (selected != null) {
                codeField.setText(selected.getCode());
                nomField.setText(selected.getNom());
                prenomField.setText(selected.getPrenom());
                competencesArea.setText(selected.getCompetences());
                codeField.setDisable(true); // Ne pas modifier le code
            }
        });

        // Modifier
        modifyBtn.setOnAction(e -> {
            Operateur selected = operateurSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun opérateur sélectionné.");
                return;
            }

            selected.setNom(nomField.getText().trim());
            selected.setPrenom(prenomField.getText().trim());
            selected.setCompetences(competencesArea.getText().trim());

            operateurSelector.setItems(FXCollections.observableArrayList(operateurs));
            clearFields(codeField, nomField, prenomField, competencesArea);
            codeField.setDisable(false);
        });

        // Supprimer
        deleteBtn.setOnAction(e -> {
            Operateur selected = operateurSelector.getValue();
            if (selected == null) {
                AtelierWindow.showAlert("Erreur", "Aucun opérateur sélectionné.");
                return;
            }
            operateurs.remove(selected);
            operateurSelector.setItems(FXCollections.observableArrayList(operateurs));
            clearFields(codeField, nomField, prenomField, competencesArea);
            codeField.setDisable(false);
        });

        // Effacer tout
        clearBtn.setOnAction(e -> {
            clearFields(codeField, nomField, prenomField, competencesArea);
            operateurSelector.getSelectionModel().clearSelection();
            codeField.setDisable(false);
        });

        box.getChildren().addAll(
                new Label("Gestion des Opérateurs :"),
                operateurSelector,
                codeField,
                nomField,
                prenomField,
                competencesArea,
                new HBox(10, addBtn, modifyBtn, deleteBtn, clearBtn)
        );

        return box;
    }

    private static void clearFields(TextField code, TextField nom, TextField prenom, TextArea competences) {
        code.clear();
        nom.clear();
        prenom.clear();
        competences.clear();
    }
}
