package model;

public class Operation {
    private String refOperation;
    private String dOperation;
    private String refEquipement;
    private float dureeOperation;

    public Operation(String refOperation, String dOperation, String refEquipement, float dureeOperation) {
        this.refOperation = refOperation;
        this.dOperation=dOperation;
        this.refEquipement=refEquipement;
        this.dureeOperation =dureeOperation;
    }
    
    public String getRefOperation() {
        return refOperation;
    }
    public void setRefOperation(String refOperation) {
        this.refOperation = refOperation;
    }
    public String getdOperation() {
        return dOperation;
    }
    public void setdOperation(String dOperation) {
        this.dOperation = dOperation;
    }
    public String getRefEquipement() {
        return refEquipement;
    }
    public void setRefEquipement(String refEquipement) {
        this.refEquipement = refEquipement;
    }
    public float getDureeOperation() {
        return dureeOperation;
    }
    public void setDureeOperation(float dureeOperation) {
        this.dureeOperation = dureeOperation;
    }

    
}
