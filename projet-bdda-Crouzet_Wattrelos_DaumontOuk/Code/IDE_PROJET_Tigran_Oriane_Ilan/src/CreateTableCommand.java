import java.util.ArrayList;


public class CreateTableCommand{
    private String nomRelationInfo;
    private int nbrColonnes;
    private ArrayList<ColInfo> colonnes = new ArrayList<ColInfo>();




    public CreateTableCommand(String nomRelationInfo,int nbrColonne,ArrayList<ColInfo>colonnes){
        this.colonnes=colonnes;
        this.nbrColonnes=nbrColonne;
        this.nomRelationInfo=nomRelationInfo;

    }
    public void execute(){
        PageId pageid = FileManager.createNewHeaderPage();
        RelationInfo relation = new RelationInfo(nomRelationInfo,nbrColonnes,colonnes);
        Catalog.getLeCatalog().addRelationInfo(relation);
    }
}