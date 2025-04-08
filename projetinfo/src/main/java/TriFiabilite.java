import java.io.*;
import java.util.*;

public class TriFiabilite {
    private Map<String, Integer> fiabiliteParMachine = new HashMap<>();
    private Set<String> evenementsPositifs = new HashSet<>(Arrays.asList("OK", "Maintenance réussie"));

    public void chargerFiabilite(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(cheminFichier));

        String ligneLue;
        while ((ligneLue = in.readLine()) != null) {
            StringTokenizer t = new StringTokenizer(ligneLue, " ");
            String machine = t.nextToken();
            String evenement = t.nextToken();

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

