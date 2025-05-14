import java.io.*;
import java.util.*;

public class ExtractionEvenement {
    private Map<String, List<String>> evenementsParMachine = new HashMap<>();

    public void chargerEvenements(String cheminFichier) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(cheminFichier));

        String ligneLue;
        while ((ligneLue = in.readLine()) != null) {
            StringTokenizer t = new StringTokenizer(ligneLue, ";");
            String machine = t.nextToken();
            String evenement = t.nextToken(); 

            evenementsParMachine.putIfAbsent(machine, new ArrayList<>());
            evenementsParMachine.get(machine).add(evenement);
        }

        in.close();
    }

    public List<String> getEvenements(String machine) {
        return evenementsParMachine.getOrDefault(machine, new ArrayList<>());
    }
}