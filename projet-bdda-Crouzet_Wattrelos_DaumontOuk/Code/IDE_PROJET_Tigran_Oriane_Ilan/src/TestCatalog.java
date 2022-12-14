import java.io.IOException;

public class TestCatalog {
    public static void addRelationTest() throws ClassNotFoundException, IOException { //Test de l'ajout de relation
        Catalog.getLeCatalog().Init();
        
        RelationInfo r = new RelationInfo("test",4,"nom","INTEGER");
        Catalog.getLeCatalog().addRelationInfo(r);
        Catalog.getLeCatalog().Finish();
        Catalog.getLeCatalog().Init();
        System.out.println(Catalog.getLeCatalog().getRelationInfo("test").getNom());

    }

    public static void main(String[]args) throws ClassNotFoundException, IOException {
        DBParams.DBpath ="./DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=2;

        addRelationTest();
    
    }
}