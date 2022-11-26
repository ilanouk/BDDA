import java.io.IOException;

public class FileManagerTest {
    public static void main(String[] args) throws IOException {
        
        DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=4;

        FileManager fM = new FileManager();
        fM.createNewHeaderPage();
    }
}
