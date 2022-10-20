import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EmptyStackException;

public class BufferManagerTest {
    
	
    public static void getPageTest() throws FileNotFoundException, IOException{

        PageId pageTest = DiskManager.getLeDiskManager().allocPage(); 
        BufferManager bMTest = BufferManager.getLeBufferManager();
        
        //Allouer la taille exact pour chaque page
        byte[] toWrite = new byte[DBParams.pageSize];
        byte[] test1 = "TestBuffer1".getBytes();
        for (int i = 0; i< test1.length; ++i)
        	toWrite[i] = test1[i];
        
        DiskManager.getLeDiskManager().writePage(pageTest, toWrite);

        byte[] buffTest=bMTest.getPage(pageTest);
        String str = new String(buffTest, StandardCharsets.UTF_8);
        
        System.out.println("testGetPage :");
        //On affiche le buffer de la page test
        
        /**** ICI PROBLEME D'AFFICHAGE : QUE DES 0 OU CARRéS APRèS LA PAGE INDIQUéE ****/
        
        System.out.println(str);
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
        // *********** A FINIR ************
         //Créer les instances
    	System.out.println("testFlushAll :");
    	PageId pageTest = DiskManager.getLeDiskManager().allocPage(); 
        BufferManager bMTest = BufferManager.getLeBufferManager();
        
        //Allouer la taille exact pour chaque page
        byte[] toWrite = new byte[DBParams.pageSize];
        byte[] test1 = "TestBuffer1".getBytes();
        for (int i = 0; i< test1.length; ++i)
        	toWrite[i] = test1[i];
        
        DiskManager.getLeDiskManager().writePage(pageTest, toWrite);
        
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
    
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=2;
        
        try {
            getPageTest();
            freePageTest();
            flushAllTest(); //********** BUG ************************
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