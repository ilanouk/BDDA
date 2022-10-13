import java.util.ArrayList;

public class RelationInfo {
    private String nomRelation;
    private int nbrColonne;
    private ArrayList<ColInfo> Colonnes = new ArrayList<ColInfo>();
    private String[] typeColonne;

    public RelationInfo() {

    }

    public String getNom() {
        return nomRelation;
    }

    public int getNbrColonne() {
        return nbrColonne;
    }

    public ArrayList<ColInfo> getColonnes() {
        return Colonnes;
    }

    public String[] getTypeCollone() {
        return typeColonne;
    }
}