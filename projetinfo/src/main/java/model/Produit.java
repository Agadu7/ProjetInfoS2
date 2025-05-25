package model;

public class Produit {
    public String codeProduit;
    public String dProduit;

    public Produit(String codeProduit, String dProduit) {
        this.codeProduit = codeProduit;
        this.dProduit=dProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }
    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }
    public String getdProduit() {
        return dProduit;
    }
    public void setdProduit(String dProduit) {
        this.dProduit = dProduit;
    }
    
}
