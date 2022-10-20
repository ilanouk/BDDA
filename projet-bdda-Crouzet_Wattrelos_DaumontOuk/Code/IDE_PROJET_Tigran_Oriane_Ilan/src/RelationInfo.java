import java.io.Serializable;
import java.util.ArrayList;

public class RelationInfo implements Serializable{
    // Attributs
    private String nomRelation;
    private int nbrColonne;
    private ArrayList<ColInfo> colonnes = new ArrayList<ColInfo>();

    // Constructeurs
    public RelationInfo(String nomRelation, int nbrColonne, String nom, String type) {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        ColInfo col = new ColInfo(nom, type);
        colonnes.add(col);
    }

    public RelationInfo(String nomRelation, int nbrColonne, ColInfo col) {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        colonnes.add(col);
    }

    //Getter
    public String getNom() {
        return nomRelation;
    }

    public int getNbrColonne() {
        return nbrColonne;
    }

    public ArrayList<ColInfo> getColonnes() {
        return colonnes;
    }

    public String getTypeColonne(int i) {
        return colonnes.get(i).getType();
    }
}