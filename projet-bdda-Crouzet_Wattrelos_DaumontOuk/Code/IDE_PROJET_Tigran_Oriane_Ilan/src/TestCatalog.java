import java.io.IOException;

public class TestCatalog {
    public static void LireEcrire() throws ClassNotFoundException, IOException {
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

        LireEcrire();
    
    }
}