import java.io.IOException;

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
    public static void getPageTest() throws IOException{
        //LA PAGETEST NE FONCTIONNE PAS
        //DiskManager dMTest = DiskManager.getLeDiskManager();
        PageId pageTest = DiskManager.getLeDiskManager().allocPage();
        System.out.println("tkt ca marche");
        BufferManager bMTest = BufferManager.leBufferManager();
        DiskManager.getLeDiskManager().writePage(pageTest, "TestBuffer".getBytes());
        byte[] buffTest=bMTest.getPage(pageTest);
        //On affiche le buffer de la page test
        System.out.println(buffTest);
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


    public static void main(String[] args) throws IOException {
		DiskManager.recupTabPageLibre();
		
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
        DBParams.frameCount=2;

        getPageTest();
        freePageTest();
        flushAllTest();
    }
}
