import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.EmptyStackException;

public class BufferManagerTest {
    
	
    public static void getPageTest() throws FileNotFoundException, IOException{

        PageId pageTest = DiskManager.getLeDiskManager().allocPage(); 
        BufferManager bMTest = BufferManager.getLeBufferManager();
        ByteBuffer buffTest=bMTest.getPage(pageTest);
        
        System.out.println("testGetPage :");
        System.out.println(Arrays.toString(buffTest.array()));
        System.out.println("************");
    }
    

    public static void freePageTest() throws IOException{

        //Créer les instances
        DiskManager dMTest = DiskManager.getLeDiskManager();
        BufferManager bMTest = BufferManager.getLeBufferManager();
        PageId pageTest = dMTest.allocPage();

        System.out.println("testFreePage :");
        //Créer page dans bufferManager
        bMTest.getPage(pageTest);

        System.out.println(pageTest);
        System.out.println("BufferPool avant : "+ Arrays.toString(bMTest.getBufferPool()));
        
        bMTest.freePage(pageTest, true);
        System.out.println("BufferPool après : "+ Arrays.toString(bMTest.getBufferPool()));
        System.out.println("************");
    }

    public static void flushAllTest() throws IOException{

         //Créer les instances
        BufferManager bMTest = BufferManager.getLeBufferManager();
        DiskManager dMTest = DiskManager.getLeDiskManager();
    	PageId pageTest = dMTest.allocPage();
        ByteBuffer buffer = ByteBuffer.allocate(DBParams.pageSize);
        ByteBuffer buff = bMTest.getPage(pageTest);

        System.out.println("testFlushAll :");
        byte[] txt = "testtt".getBytes();
        buff.put(txt);
        bMTest.freePage(pageTest, true);

        dMTest.readPage(pageTest, buffer);
        
        System.out.println("BufferPool avant : "+ Arrays.toString(bMTest.getBufferPool()));
        bMTest.flushAll();
        System.out.println("BufferPool après : "+ Arrays.toString(bMTest.getBufferPool()));
        
    }


    public static void main(String[] args) {
    	try {
            DiskManager.recupTabPageLibre();
        } catch (IOException e1) {
            System.out.println(e1);
            e1.printStackTrace();
        }
    
		DBParams.DBpath ="./DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=4;
        
        try {
            getPageTest();
            freePageTest();
            flushAllTest();
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }  
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        catch (EmptyStackException e) {
            System.out.println("ERREUR : Stack de Frame vide");
        }
    }
}