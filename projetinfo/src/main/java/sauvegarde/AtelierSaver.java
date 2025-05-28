package sauvegarde;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.*;

public class AtelierSaver {

    public static void saveAtelierToFile(Atelier atelier, ChefAtelier chef, List<Equipement> equipements,
                                         List<Produit> produits, List<Operateur> operateurs,
                                         List<Operation> operations, List<Poste> postes, List<Gamme> gammes,
                                         String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("ATELIER: " + atelier.codeAtelier);
            writer.newLine();

            // Nom Atelier
            writer.write("NOM: " + atelier.codeAtelier);
            writer.newLine();

            // Chef Atelier
            writer.write("CHEF: " + chef.nom + ";" + atelier.codeAtelier);
            writer.newLine();

            // EQUIPEMENT
            writer.write("EQUIPEMENT:");
            for (Equipement eq : equipements) {
                writer.write(" " + eq.refEquipement + ";" + eq.dEquipement + ";");

                // Machines
                for (int i = 0; i < eq.listeMachine.size(); i++) {
                    writer.write(eq.listeMachine.get(i).refMachine);
                    if (i < eq.listeMachine.size() - 1) writer.write(",");
                }
                writer.write(";");

                // Postes
                for (int i = 0; i < eq.listePostes.size(); i++) {
                    writer.write(eq.listePostes.get(i).refPoste);
                    if (i < eq.listePostes.size() - 1) writer.write(",");
                }
                writer.write(";");
            }
            writer.newLine();

            // MACHINE
            writer.write("MACHINE:");
            for (Machine m : atelier.listeMachine) {
                writer.write(" " + m.refMachine + ";" + m.dMachine + ";" + m.x + ";" + m.y + ";" +
                             m.type + ";" + m.cout + ";" + m.Largeur + ";" + m.Hauteur + ";");
            }
            writer.newLine();

            // PRODUIT
            writer.write("PRODUIT:");
            for (Produit p : produits) {
                writer.write(" " + p.codeProduit + ";" + p.dProduit + ";");
            }
            writer.newLine();

            // OPERATEUR
            writer.write("OPERATEUR:");
            for (Operateur o : atelier.listeOperateur) {
                writer.write(" " + o.code + ";" + o.nom + ";" + o.prenom + ";" + o.competences + ";");
            }
            writer.newLine();

            // OPERATION
            writer.write("OPERATION:");
            for (Operation op : operations) {
                writer.write(" " + op.refOperation + ";" + op.dOperation + ";" +
                             op.refEquipement + ";" + op.dureeOperation + ";");
            }
            writer.newLine();

            // POSTE
            writer.write("POSTE:");
            for (Poste p : atelier.listePoste) {
                writer.write(" " + p.refPoste + ";" + p.dPoste + ";");
                for (int i = 0; i < p.listeMachine.size(); i++) {
                    writer.write(p.listeMachine.get(i).refMachine);
                    if (i < p.listeMachine.size() - 1) writer.write(",");
                }
                writer.write(";");
            }
            writer.newLine();

            // GAMME
            writer.write("GAMME:");
            for (Gamme g : gammes) {
                writer.write(" ");
                // Machines
                for (int i = 0; i < g.listeMachine.size(); i++) {
                    writer.write(g.listeMachine.get(i).refMachine);
                    if (i < g.listeMachine.size() - 1) writer.write(",");
                }
                writer.write(";");
                writer.write(g.refGamme + ";");

                // Operations
                for (int i = 0; i < g.listeOperation.size(); i++) {
                    writer.write(g.listeOperation.get(i).refOperation);
                    if (i < g.listeOperation.size() - 1) writer.write(",");
                }
                writer.write(";");

                // Produits
                for (int i = 0; i < g.listeProduit.size(); i++) {
                    writer.write(g.listeProduit.get(i).codeProduit);
                    if (i < g.listeProduit.size() - 1) writer.write(",");
                }
                writer.write(";");
            }
            writer.newLine();
            writer.newLine(); // Séparateur d'ateliers

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

