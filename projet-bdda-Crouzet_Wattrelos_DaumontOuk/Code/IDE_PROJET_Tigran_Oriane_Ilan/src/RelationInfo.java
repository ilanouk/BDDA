import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class RelationInfo implements Serializable {
    // Attributs
    private String nomRelation;
    private int nbrColonne;
    private ArrayList<ColInfo> colonnes = new ArrayList<ColInfo>();
    private PageId headerPageId; // représente l’identifiant de la Header Page de la relation

    // Constructeurs
    public RelationInfo(String nomRelation, int nbrColonne, String nom, String type) throws IOException {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        ColInfo col = new ColInfo(nom, type);
        headerPageId = FileManager.getFileManager().createNewHeaderPage();
        colonnes.add(col);
    }

    public RelationInfo(String nomRelation, int nbrColonne, ColInfo col) throws IOException {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        headerPageId = FileManager.getFileManager().createNewHeaderPage();
        colonnes.add(col);
    }

    public RelationInfo(String nomRelation, int nbrColonne, ArrayList<ColInfo> colonnes) throws IOException {
        this.nomRelation = nomRelation;
        this.nbrColonne = nbrColonne;
        this.colonnes = colonnes;
        headerPageId = FileManager.getFileManager().createNewHeaderPage();
    }

    public RelationInfo(String nomRelation, ArrayList<ColInfo> colonnes, PageId hp) throws IOException {
        this.nomRelation = nomRelation;
        this.nbrColonne = colonnes.size();
        this.colonnes = colonnes;
        this.headerPageId = hp;
    }

    // Getter
    public String getNom() {
        return nomRelation;
    }

    public int getNbrColonne() {
        return nbrColonne;
    }

    public ArrayList<ColInfo> getColonnes() {
        return colonnes;
    }

    public PageId getHeaderPageId() {
        return headerPageId;
    }

    public String getTypeColonne(int i) {
        return colonnes.get(i).getType();
    }

    public void addColonne(ColInfo col) {
        colonnes.add(col);
    }
}
