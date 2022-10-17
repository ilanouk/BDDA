import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class BufferManagerTest {
    
	
    public static void getPageTest() throws FileNotFoundException, IOException{

        /*BufferManager bm = BufferManager.leBufferManager();
        DiskManager dm = DiskManager.getLeDiskManager();
        PageId pageId = dm.allocPage();
        byte[] buf = bm.getPage(pageId);

        System.out.println("GetPage : "+Arrays.toString(buf));*/

        PageId pageTest1 = DiskManager.getLeDiskManager().allocPage();
        PageId pageTest2 = DiskManager.getLeDiskManager().allocPage();
        PageId pageTest3 = DiskManager.getLeDiskManager().allocPage();
        
        BufferManager bMTest = BufferManager.leBufferManager();
        
        DiskManager.getLeDiskManager().writePage(pageTest1, "TestBuffer1".getBytes());
        DiskManager.getLeDiskManager().writePage(pageTest2, "TestBuffer2".getBytes());
        DiskManager.getLeDiskManager().writePage(pageTest3, "TestBuffer3".getBytes());

        byte[] buffTest=bMTest.getPage(pageTest3);
        System.out.println("testGetPage");
        //On affiche le buffer de la page test
        System.out.println(Arrays.toString(buffTest));
    }
    

    public static void freePageTest() throws IOException{

        //Créer les instances
        DiskManager dMTest = DiskManager.getLeDiskManager();
        BufferManager bMTest = BufferManager.leBufferManager();
        PageId pageTest = dMTest.allocPage();

        System.out.println("testFreePage");
        //Créer page dans bufferManager
        bMTest.getPage(pageTest);

        System.out.println(pageTest);
        System.out.println("BufferPool avant : "+ Arrays.toString(bMTest.getBufferPool()));
        
        bMTest.freePage(pageTest, true);
        System.out.println("BufferPool après : "+ Arrays.toString(bMTest.getBufferPool()));
    }

    public static void flushAllTest() throws IOException{
        // *********** A FINIR ************
         //Créer les instances
         DiskManager dMTest = DiskManager.getLeDiskManager();
         PageId pageTest = dMTest.allocPage();
         BufferManager bMTest = BufferManager.leBufferManager();
         byte[] buffTest = new byte[DBParams.pageSize];
         
         byte [] buff = bMTest.getPage(pageTest);
         System.out.println("FreePageTest");
         
         byte[] txt = "TEST1".getBytes();
         buff=txt;
         
         bMTest.freePage(pageTest, true);
         dMTest.readPage(pageTest, buffTest);
         System.out.println("Avant flushAll : "+Arrays.toString(buffTest));
         bMTest.flushAll();
         
         dMTest.readPage(pageTest, buffTest);
         System.out.println("Après flushAll : "+ Arrays.toString(buffTest));
    }


    public static void main(String[] args) {
    	try {
            DiskManager.recupTabPageLibre();
        } catch (IOException e1) {
            System.out.println(e1);
            e1.printStackTrace();
        }
    
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=2;
        
        try {
            getPageTest();
            freePageTest();
            //flushAllTest(); //********** BUG ************************
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }  
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
