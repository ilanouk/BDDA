import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManagerTest {
    public static void main(String[] args) throws IOException {
        
        DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=4;
        
        FileManager fM = new FileManager();
        BufferManager bM = new BufferManager();
        RelationInfo relInfo = new RelationInfo("nomRelation", 3, "nom", "type");
        Record record = new Record(relInfo);
        PageId pageId = DiskManager.getLeDiskManager().allocPage();
        
        System.out.println("Test HeaderPage:");
        System.out.println(fM.createNewHeaderPage());
        
        System.out.println("*******");
        System.out.println("Test addDataPage:");
        System.out.println(fM.addDataPage(relInfo));
        System.out.println(fM.addDataPage(relInfo));
        
        System.out.println("*******");
        System.out.println("Test freeDataPage:");
        System.out.println(fM.getFreeDataPageId(relInfo, 30000));
        System.out.println(fM.getFreeDataPageId(relInfo, 30000));
        
        System.out.println("*******");
        System.out.println("Test writeRecordToDataPage:");
        //System.out.println(fM.writeRecordToDataPage(record, pageId));

        System.out.println("*******");
        System.out.println("Test getAllRecords:");
        //System.out.println(fM.getAllPageId(relInfo));
    }
}
