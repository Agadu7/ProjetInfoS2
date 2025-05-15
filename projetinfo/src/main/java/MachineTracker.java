import java.io.*;
import java.util.StringTokenizer;

public class MachineTracker {
    public static void main(String[] args) {
        final int MAX_MACHINES = 100; // capacité maximale du tableau
        String[] M = new String[MAX_MACHINES]; // tableau des machines
        int e = 0; // nombre de machines enregistrées

        try {
            BufferedReader in = new BufferedReader(new FileReader("suivi.txt"));
            String ligneLue;

            while ((ligneLue = in.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(ligneLue, " ");
                if (!t.hasMoreTokens()) continue;

                String machine = t.nextToken();
                boolean ajouter = true;

                if (e == 0) {
                    M[0] = machine;
                    e++;
                } else {
                    for (int i = 0; i < e; i++) {
                        if (machine.equals(M[i])) {
                            ajouter = false;
                            break;
                        }
                    }

                    if (ajouter && e < MAX_MACHINES) {
                        M[e] = machine;
                        e++;
                    }
                }
            }

            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Affichage des machines enregistrées 
        System.out.println("Machines enregistrées :");
        for (int i = 0; i < e; i++) {
            System.out.println(M[i]);
        }
    }
}

