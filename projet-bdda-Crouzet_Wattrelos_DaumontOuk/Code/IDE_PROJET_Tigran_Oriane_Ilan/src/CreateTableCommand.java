public class CreateTableCommand{
    private String nomRelationInfo;
    private int nbrColonnes;
    private String nomColonne;
    private String typeColonne;

    public void execute(){
        PageId pageid = FileManager.createNewHeaderPage();
        RelationInfo relation = new RelationInfo(nomRelationInfo,nbrColonnes,nomColonne,typeColonne);
        Catalog.getLeCatalog().addRelationInfo(relation);
    }
}