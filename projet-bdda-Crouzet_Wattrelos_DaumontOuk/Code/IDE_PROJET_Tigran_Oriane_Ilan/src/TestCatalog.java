import java.io.IOException;

public class TestCatalog {
    public static void addRelationTest() throws ClassNotFoundException, IOException { //Test de l'ajout de relation
        Catalog.getLeCatalog().Init();
        RelationInfo r = new RelationInfo("test",4,"nom","Integer");
        Catalog.getLeCatalog().addRelationInfo(r);
        Catalog.getLeCatalog().Finish();
    }

    public static void main(String[]args) throws ClassNotFoundException, IOException {
        DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=2;

        addRelationTest();
    
    }
}