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
    public static byte[] getPageTest() throws IOException{

        //Allouer 2 pages
        PageId page1 = DiskManager.getLeDiskManager().allocPage();
        PageId page2 = DiskManager.getLeDiskManager().allocPage();
        
        //Créer contenu des bytes des pages
        byte[] contenu1 = "C'est la 1".getBytes();
        byte[] contenu2 = "C'est la 2".getBytes();

        //Ecrire les bytes dans les pages
        DiskManager.getLeDiskManager().writePage(page1, contenu1);
        DiskManager.getLeDiskManager().writePage(page2, contenu2);

        //Mettre sur allPages les pages 1 et 2
        byte[] allPages = BufferManager.leBufferManager().getPage(page1);
        allPages = BufferManager.leBufferManager().getPage(page2);

        return allPages;
    }

    public static void main(String[] args) throws IOException {
		DiskManager.recupTabPageLibre();
		
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;

        System.out.println(getPageTest());


    }

}
