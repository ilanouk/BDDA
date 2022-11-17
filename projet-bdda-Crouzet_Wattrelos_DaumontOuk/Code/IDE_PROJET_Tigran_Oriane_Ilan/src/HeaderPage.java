import java.io.IOException;
import java.nio.ByteBuffer;

public class HeaderPage {
    /* Dans le nBuffer, le premeir int est le nombre de page, ensuite une datapage est stocké a la place 1 Int + n*3*Integer 
    * (*3 car le premeier c'est le fileIDX , le 2 c'est le PageIdx , le 3 c'est la taille dispo)
    */
    ByteBuffer nBuffer; //Stocke toute les donneés contenu dans le fichier HeaderPage
    PageId page;

    public HeaderPage(PageId page) throws IOException{
        this.page=page;
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
    }

    public void setDatatPageCount() throws IOException{
        int intSize = 4;
        int nbPage = nBuffer.getInt(1*intSize);
        nbPage++;
        nBuffer.putInt(1*intSize, nbPage);
        BufferManager.getLeBufferManager().freePage(page,true);
    }
}
