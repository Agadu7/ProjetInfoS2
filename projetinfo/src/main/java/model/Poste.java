package model;

import java.util.ArrayList;
import java.util.List;

public class Poste {
    public String refPoste;
    public String dPoste;
    public List<Machine> listeMachine;

    public Poste(String refPoste, String dPoste, List<Machine> listeMachine) {
        this.refPoste = refPoste;
        this.dPoste = dPoste;
        this.listeMachine = new ArrayList<>(listeMachine); // copie défensive
    }

    public String getRefPoste() {
        return refPoste;
    }

    public void setRefPoste(String refPoste) {
        this.refPoste = refPoste;
    }

    public String getdPoste() {
        return dPoste;
    }

    public void setdPoste(String dPoste) {
        this.dPoste = dPoste;
    }

    public List<Machine> getListeMachine() {
        return listeMachine;
    }

    public void setListeMachine(List<Machine> listeMachine) {
        this.listeMachine = new ArrayList<>(listeMachine); // copie défensive
    }

    public String convertirEnLignePoste() {
        // On convertit les machines en une chaîne lisible ou identifiables
        StringBuilder sb = new StringBuilder();
        for (Machine m : listeMachine) {
            sb.append(m.getRefMachine()).append(","); // supposons que Machine a un getNom()
        }
        String machinesStr = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
        return refPoste + ";" + dPoste + ";" + machinesStr;
    }

    public static Poste convertirEnObjetPoste(String ligne, ArrayList<Machine> machinesDisponibles) {
        String[] parts = ligne.split(";");
        String ref = parts[0];
        String desc = parts[1];
        String[] nomsMachines = parts.length > 2 ? parts[2].split(",") : new String[0];

        ArrayList<Machine> machinesAssociees = new ArrayList<>();
        for (String nom : nomsMachines) {
            for (Machine m : machinesDisponibles) {
                if (m.getRefMachine().equals(nom.trim())) {
                    machinesAssociees.add(m);
                    break;
                }
            }
        }

        return new Poste(ref, desc, machinesAssociees);
    }

    @Override
    public String toString() {
        return refPoste;
    }
}
