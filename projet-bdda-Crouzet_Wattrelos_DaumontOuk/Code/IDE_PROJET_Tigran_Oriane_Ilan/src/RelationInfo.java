import java.util.ArrayList;

public class RelationInfo{
    private String nomRelation;
    private int nbrColonne;
    private ArrayList<ColInfo> Colonnes = new ArrayList<ColInfo>();
    
    public String getNom(){
        return nomRelation;
    }
    public int getNbrColonne(){
        return nbrColonne;
    }
    public ArrayList<ColInfo> getColonnes(){
        return Colonnes;
    }

}