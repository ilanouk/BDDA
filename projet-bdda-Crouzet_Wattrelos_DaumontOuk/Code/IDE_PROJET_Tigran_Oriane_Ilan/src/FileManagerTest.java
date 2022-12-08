import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManagerTest {
    public static void main(String[] args) throws Exception {
        
        DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=4;
        
        FileManager fM = new FileManager();
        BufferManager bM = new BufferManager();
        ArrayList<ColInfo> liste = new ArrayList<ColInfo>();
        liste.add(new ColInfo("Nom", "VARCHAR(10)"));
        liste.add(new ColInfo("Prenom", "VARCHAR(10)"));
        liste.add(new ColInfo("Age", "INTEGER"));
        liste.add(new ColInfo("Note", "REAL"));
        PageId id = new PageId(1, 0);
        RelationInfo relInfo = new RelationInfo("etudiant", liste, id);
        Record rec = new Record(relInfo);

        rec.addValue("Crouzet");
        rec.addValue("Oriane");
        rec.addValue("21");
        rec.addValue("15.1");
        
        System.out.println("Test HeaderPage:");
        System.out.println(fM.createNewHeaderPage());
        
        System.out.println("*******");
        System.out.println("Test addDataPage:");
        System.out.println(fM.addDataPage(relInfo));
        System.out.println(fM.addDataPage(relInfo));
        
        System.out.println("*******");
        System.out.println("Test freeDataPage:");
        System.out.println(fM.getFreeDataPageId(relInfo, 3000));
        
        System.out.println("*******");
        System.out.println("Test writeRecordToDataPage:");
        System.out.println(fM.writeRecordToDataPage(rec, id));

        System.out.println("*******");
        System.out.println("Test getAllRecords:");
        System.out.println(fM.GetAllRecords(relInfo));
    }
}
