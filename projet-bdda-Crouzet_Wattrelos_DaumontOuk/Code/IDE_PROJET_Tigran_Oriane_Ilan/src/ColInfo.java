import java.io.Serializable;

public class ColInfo implements Serializable {
    private String nom, type;

    public ColInfo(String nom, String type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
}