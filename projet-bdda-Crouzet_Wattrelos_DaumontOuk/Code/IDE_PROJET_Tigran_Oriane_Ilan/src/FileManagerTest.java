import java.io.IOException;

public class FileManagerTest {
    public static void main(String[] args) throws IOException {
        
        DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=4;

        FileManager fM = new FileManager();
        RelationInfo relInfo = new RelationInfo(null, 0, null, null);
        
        System.out.println(fM.createNewHeaderPage());
        System.out.println(fM.createNewHeaderPage());
        System.out.println(fM.createNewHeaderPage());
        
        System.out.println(fM.addDataPage(relInfo));
        
        System.out.println(fM.GetAllRecords(relInfo));
    }
}
