import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class BufferManagerTest {
    
    /*CREER UNE PAGEID ET l'afficher
     * Allouez (avec le DiskManager) 2 pages, écrivez dans ces pages puis essayez de lire pour
     * vérifier que le contenu lu est bien celui qui a été écrit.
     * Puis essayez de passer à un nombre de pages supérieur à 2, déclenchant ainsi la politique de
     * remplacement ; 
     * vérifiez encore une fois que tout se passe bien pour les lectures et écritures
     * 
     */

    //Test des fonctions getPage, freePage et flushAll
    public static void getPageTest() throws FileNotFoundException, IOException{

        BufferManager bm = BufferManager.leBufferManager();
        DiskManager dm = DiskManager.getLeDiskManager();
        PageId pageId = dm.allocPage();
        byte[] buf = bm.getPage(pageId);

        System.out.print("GetPage : ");
        System.out.println(buf);


       /*  PageId pageTest = DiskManager.getLeDiskManager().allocPage();
        BufferManager bMTest = BufferManager.leBufferManager();
        System.out.println("pain");
        DiskManager.getLeDiskManager().writePage(pageTest, "TestBuffer".getBytes());

        //PROBLEME ICI
        System.out.println("steak");
        byte[] buffTest=bMTest.getPage(pageTest);
        System.out.println("pain");
        System.out.println("Le sandwich est pret");
        //On affiche le buffer de la page test
        System.out.println(buffTest); */
    }

    public static void freePageTest() throws IOException{

        //Créer les instances
        DiskManager dMTest = DiskManager.getLeDiskManager();
        PageId pageTest = dMTest.allocPage();
        BufferManager bMTest = BufferManager.leBufferManager();

        //Créer page dans bufferManager
        bMTest.getPage(pageTest);

        System.out.println("PageId : "+ pageTest);
        System.out.println("BufferPool avant : "+ bMTest.getBufferPool());
        
        bMTest.freePage(pageTest, true);
        System.out.println("BufferPool après : "+ bMTest.getBufferPool());
    }

    public static void flushAllTest() throws IOException{
        // *********** A FINIR ************
         //Créer les instances
         DiskManager dMTest = DiskManager.getLeDiskManager();
         PageId pageTest = dMTest.allocPage();
         BufferManager bMTest = BufferManager.leBufferManager();
         byte[] buffTest = bMTest.getPage(pageTest);

         byte[] essai = "TEST1".getBytes();
         
    }


    public static void main(String[] args) {
    	System.out.println("1");
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
            System.out.println("1");
            freePageTest();
            System.out.println("2");
            flushAllTest();
            System.out.println("3");
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
