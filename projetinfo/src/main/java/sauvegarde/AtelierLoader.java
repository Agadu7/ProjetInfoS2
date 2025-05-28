package sauvegarde;

import java.io.*;
import java.util.*;

import model.*;

public class AtelierLoader {

    public static List<Atelier> chargerAteliersDepuisFichier(String chemin) throws IOException {
        List<Atelier> ateliers = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(chemin));
        String ligne;

        // Variables de travail pour chaque atelier
        int codeAtelier = 0;
        List<Operateur> operateurs = new ArrayList<>();
        List<Machine> machines = new ArrayList<>();
        List<Poste> postes = new ArrayList<>();
        List<Gamme> gammes = new ArrayList<>();
        List<Operation> operations = new ArrayList<>();
        List<Produit> produits = new ArrayList<>();
        List<Equipement> equipements = new ArrayList<>();
        ChefAtelier chef = null;

        Map<String, Machine> machineMap = new HashMap<>();
        Map<String, Poste> posteMap = new HashMap<>();
        Map<String, Produit> produitMap = new HashMap<>();
        Map<String, Operation> operationMap = new HashMap<>();

        while ((ligne = reader.readLine()) != null) {
            ligne = ligne.trim();
            if (ligne.isEmpty()) continue;

            if (ligne.startsWith("ATELIER:")) {
                // On sauvegarde l'atelier précédent s'il y en a un
                if (!machines.isEmpty() || !operateurs.isEmpty()) {
                    Atelier atelier = new Atelier(codeAtelier, new ArrayList<>(operateurs), new ArrayList<>(machines), new ArrayList<>(postes), new ArrayList<>(gammes));
                    ateliers.add(atelier);
                    if (chef != null) {
                        // Associe le chef à cet atelier
                        chef = new ChefAtelier(chef.nom, atelier);
                    }
                    // Réinitialisation pour l'atelier suivant
                    operateurs.clear(); machines.clear(); postes.clear(); gammes.clear(); operations.clear(); produits.clear(); equipements.clear();
                    machineMap.clear(); posteMap.clear(); produitMap.clear(); operationMap.clear();
                }
                codeAtelier++;
            } else if (ligne.startsWith("NOM:")) {
                // Nom ignoré ici mais peut être utilisé
            } else if (ligne.startsWith("CHEF:")) {
                String[] parts = ligne.substring(5).split(";");
                chef = new ChefAtelier(parts[0], null);
            } else if (ligne.startsWith("MACHINE:")) {
                String[] data = ligne.substring(8).split(" ");
                for (String m : data) {
                    String[] vals = m.split(";");
                    Machine machine = new Machine(vals[0], vals[1], Float.parseFloat(vals[2]), Float.parseFloat(vals[3]), vals[4], Float.parseFloat(vals[5]), Float.parseFloat(vals[6]), Float.parseFloat(vals[7]));
                    machines.add(machine);
                    machineMap.put(vals[0], machine);
                }
            } else if (ligne.startsWith("PRODUIT:")) {
                String[] data = ligne.substring(8).split(" ");
                for (String p : data) {
                    String[] vals = p.split(";");
                    Produit prod = new Produit(vals[0], vals[1]);
                    produits.add(prod);
                    produitMap.put(vals[0], prod);
                }
            } else if (ligne.startsWith("OPERATEUR:")) {
                String[] data = ligne.substring(11).split(" ");
                for (String o : data) {
                    String[] vals = o.split(";");
                    operateurs.add(new Operateur(vals[0], vals[1], vals[2], vals[3]));
                }
            } else if (ligne.startsWith("OPERATION:")) {
                String[] data = ligne.substring(10).split(" ");
                for (String o : data) {
                    String[] vals = o.split(";");
                    Operation op = new Operation(vals[0], vals[1], vals[2], Float.parseFloat(vals[3]));
                    operations.add(op);
                    operationMap.put(vals[0], op);
                }
            } else if (ligne.startsWith("POSTE:")) {
                String[] data = ligne.substring(6).split(" ");
                for (String p : data) {
                    String[] vals = p.split(";");
                    List<Machine> listeM = new ArrayList<>();
                    if (vals.length > 2) {
                        for (String ref : vals[2].split(",")) {
                            Machine m = machineMap.get(ref);
                            if (m != null) listeM.add(m);
                        }
                    }
                    Poste poste = new Poste(vals[0], vals[1], listeM);
                    postes.add(poste);
                    posteMap.put(vals[0], poste);
                }
            } else if (ligne.startsWith("EQUIPEMENT:")) {
                String[] data = ligne.substring(11).split(" ");
                for (String e : data) {
                    String[] vals = e.split(";");
                    ArrayList<Machine> listeM = new ArrayList<>();
                    for (String ref : vals[2].split(",")) {
                        if (machineMap.containsKey(ref)) listeM.add(machineMap.get(ref));
                    }
                    ArrayList<Poste> listeP = new ArrayList<>();
                    for (String ref : vals[3].split(",")) {
                        if (posteMap.containsKey(ref)) listeP.add(posteMap.get(ref));
                    }
                    equipements.add(new Equipement(vals[0], vals[1], listeM, listeP));
                }
            } else if (ligne.startsWith("GAMME:")) {
                String[] data = ligne.substring(6).split(";");
                List<Machine> mList = new ArrayList<>();
                for (String ref : data[0].split(",")) {
                    Machine m = machineMap.get(ref);
                    if (m != null) mList.add(m);
                }
                List<Operation> oList = new ArrayList<>();
                for (String ref : data[2].split(",")) {
                    Operation o = operationMap.get(ref);
                    if (o != null) oList.add(o);
                }
                List<Produit> pList = new ArrayList<>();
                for (String ref : data[3].split(",")) {
                    Produit p = produitMap.get(ref);
                    if (p != null) pList.add(p);
                }
                gammes.add(new Gamme(mList, data[1], oList, pList));
            }
        }

        // Dernier atelier
        if (!machines.isEmpty() || !operateurs.isEmpty()) {
            Atelier atelier = new Atelier(codeAtelier, operateurs, machines, postes, gammes);
            ateliers.add(atelier);
        }

        reader.close();
        return ateliers;
    }
}