public class Main {
    public static void main(String[] args) {
        System.out.println("Gaetan mon bébou à la crême");
        Atelier A = new Atelier( 1);
        Machine m1 = new Machine("1","machine_croissant" , 0, 0, "viennoiserie", 100);
        Machine m2 = new Machine("2","machine_painchocolat" , 1, 0, "viennoiserie", 100);
        Machine m3 = new Machine("3","machine_beignet" , 0, 1, "viennoiserie", 100);
        Machine m4 = new Machine("4","machine_muffin" , 1, 1, "viennoiserie", 100);
        Personne p1 = new Personne(null,"Dadouche", "Abd-Elraouf",1);
        ChefAtelier Cheffe = new ChefAtelier("Duboeuf Eleonore", A);
    }
}