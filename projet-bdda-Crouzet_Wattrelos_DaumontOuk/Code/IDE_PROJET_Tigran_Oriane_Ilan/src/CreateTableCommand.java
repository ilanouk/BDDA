import java.io.IOException;
import java.util.ArrayList;

// Classe qui permet de creer une table
public class CreateTableCommand{
    private String nomRelationInfo;
    private int nbrColonnes;
    private ArrayList<ColInfo> colonnes = new ArrayList<ColInfo>();




    public CreateTableCommand(String nomRelationInfo,int nbrColonne,ArrayList<ColInfo>colonnes){
        this.colonnes=colonnes;
        this.nbrColonnes=nbrColonne;
        this.nomRelationInfo=nomRelationInfo;

    }
    public void execute() throws IOException{
        PageId pageid = FileManager.getFileManager().createNewHeaderPage();
        RelationInfo relation = new RelationInfo(nomRelationInfo,nbrColonnes,colonnes);
        Catalog.getLeCatalog().addRelationInfo(relation);
    }
}