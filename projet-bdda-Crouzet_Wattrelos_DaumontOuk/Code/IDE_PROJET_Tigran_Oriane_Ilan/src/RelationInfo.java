import java.io.Serializable;
import java.util.ArrayList;

public class RelationInfo implements Serializable{
    // Attributs
    private String nomRelation;
    private int nbrColonne;
    private ArrayList<ColInfo> colonnes = new ArrayList<ColInfo>();
    private PageId headerPageId; // représente l’identifiant de la Header Page de la relation

    // Constructeurs
    public RelationInfo(String nomRelation, int nbrColonne, PageId headerPageId, String nom, String type) {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        this.headerPageId=headerPageId;
        ColInfo col = new ColInfo(nom, type);
        colonnes.add(col);
    }

    public RelationInfo(String nomRelation, PageId headerPageId, int nbrColonne, ColInfo col) {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        this.headerPageId=headerPageId;
        colonnes.add(col);
    }

    public RelationInfo(String nomRelation, int nbrColonne, PageId headerPageId, ArrayList<ColInfo> colonnes) {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        this.headerPageId=headerPageId;
       this.colonnes=colonnes;
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

    public PageId getHeaderPageId(){
        return headerPageId;
    }

    public String getTypeColonne(int i) {
        return colonnes.get(i).getType();
    }

    public void addColonne(ColInfo col){
        colonnes.add(col);
    }
}
