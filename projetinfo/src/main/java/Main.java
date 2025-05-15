import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            ExtractionEvenement extracteur = new ExtractionEvenement();
            extracteur.chargerEvenements("projetinfo/src/main/java/suiviMaintenance.txt");

            Map<String, List<EvenementMachine>> donnees = extracteur.getEvenementsParMachine();
            CalculFiabilite calc = new CalculFiabilite();
            calc.calculerFiabilite(donnees);

        } catch (IOException e) {
            System.out.println("Erreur de lecture : " + e.getMessage());
        }
    }
}


