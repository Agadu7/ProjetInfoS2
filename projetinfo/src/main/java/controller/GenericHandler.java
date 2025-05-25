package controller;

import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class GenericHandler {
    private final String type;
    private final List<String> elements = new ArrayList<>();

    public GenericHandler(String type) {
        this.type = type;
    }

    public void ajouter(String nom) {
        if (nom == null || nom.isBlank()) {
            showAlert("Erreur", "Nom invalide pour " + type + ".");
            return;
        }
        elements.add(nom);
        showAlert("Succès", type + " ajouté : " + nom);
    }

    public void modifier(int index, String nouveauNom) {
        if (index < 0 || index >= elements.size()) {
            showAlert("Erreur", "Index invalide.");
            return;
        }
        elements.set(index, nouveauNom);
        showAlert("Succès", type + " modifié : " + nouveauNom);
    }

    public void supprimer(int index) {
        if (index < 0 || index >= elements.size()) {
            showAlert("Erreur", "Index invalide.");
            return;
        }
        String removed = elements.remove(index);
        showAlert("Succès", type + " supprimé : " + removed);
    }

    public List<String> getElements() {
        return elements;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Node getControls(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getControls'");
    }
}
