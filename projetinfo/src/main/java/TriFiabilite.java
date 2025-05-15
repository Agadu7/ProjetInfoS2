import java.io.*;
import java.util.*;

public class TriFiabilite {
    private Map<String, Integer> fiabiliteParMachine = new HashMap<>();
    private Set<String> evenementsPositifs = new HashSet<>(Arrays.asList("ok", "maintenance réussie"));

    public void chargerFiabilite(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(cheminFichier));
        String ligneLue;

        while ((ligneLue = in.readLine()) != null) {
            String[] champs = ligneLue.split(";");
            if (champs.length < 6) continue; // Ignore les lignes invalides

            String machine = champs[2]; // 3e champ
            String evenement = champs[3]; // 4e champ

            fiabiliteParMachine.putIfAbsent(machine, 0);
            if (evenementsPositifs.contains(evenement)) {
                fiabiliteParMachine.put(machine, fiabiliteParMachine.get(machine) + 1);
            }
        }

        in.close();
    }

    public List<Map.Entry<String, Integer>> getMachinesTriees() {
        List<Map.Entry<String, Integer>> listeTriee = new ArrayList<>(fiabiliteParMachine.entrySet());
        listeTriee.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return listeTriee;
    }
}


