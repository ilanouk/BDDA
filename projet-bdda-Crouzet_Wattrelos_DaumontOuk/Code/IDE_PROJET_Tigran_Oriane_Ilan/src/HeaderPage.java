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
    }

    public void setDataPageCount() throws IOException{ //Incrémente le compteur du header Page de 1
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
        int intSize = 4;
        int nbPage = nBuffer.getInt(1*intSize);
        nbPage++;
        nBuffer.putInt(1*intSize, nbPage);
        BufferManager.getLeBufferManager().freePage(page,true);
    }

    public int getDataPageCount() throws IOException{
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
        int nbPage = nBuffer.getInt(0);

        BufferManager.getLeBufferManager().freePage(page, false);
        
        return nbPage;
    }

    //OK
    //Permet d'obtenir une page du DataPage où il y a assez de place
    public PageId getDPEnoughSpace(int sizeRecord) throws IOException{
        PageId dataPage = DiskManager.getLeDiskManager().allocPage();
        int nbPage = getDataPageCount();
        int taille;
        
        for(int i=12; i<nbPage*8+4;i+=8){
            taille=nBuffer.getInt(i);
            if(taille>sizeRecord){
                return null;
            }
        }
        addNewDataPage(dataPage);
        
        return page;
    }

    public void addNewDataPage(PageId dataPage) throws IOException{
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
        int fileIDX = dataPage.getFile();
        int PageIdx = dataPage.getPage();
        nBuffer.putInt(fileIDX);
        nBuffer.putInt(PageIdx);
        nBuffer.putInt(DBParams.pageSize-2*4,0); //écrire à 4088 octets de la Data Page
        nBuffer.putInt(DBParams.pageSize-4,0); // jusqu'à 4096
        setDataPageCount();
        BufferManager.getLeBufferManager().freePage(page,true);
    }
    
    //OK
    public void incrementeTaille() throws IOException{ //incrémente la taille du headrer page 
        
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
        int nbPage = nBuffer.getInt(1*4);

        nbPage++;
        nBuffer.putInt(0, nbPage);

        BufferManager.getLeBufferManager().freePage(page, true);
    }

    //OK
    public void setTailleZero() throws IOException{
        this.nBuffer = BufferManager.getLeBufferManager().getPage(page);
        nBuffer.putInt(0, 0);
        BufferManager.getLeBufferManager().freePage(page, true);
    }

    public ByteBuffer gByteBuffer(){
        return nBuffer;
    }

}
