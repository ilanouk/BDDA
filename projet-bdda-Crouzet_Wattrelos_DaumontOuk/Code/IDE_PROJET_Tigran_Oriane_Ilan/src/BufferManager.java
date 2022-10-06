import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class BufferManager {
    
    private static BufferManager leBufferManager = new BufferManager();
    private DiskManager dManager;
    private Frame[] bufferPool; //liste des buffers


    public BufferManager(){//private avec singleton?
        bufferPool = new Frame[DBParams.frameCount];
        dManager = DiskManager.getLeDiskManager();
        initBufferPool();
    }


    public void initBufferPool(){ //private?
        for(int i=0;i<DBParams.frameCount;i++){
            bufferPool[i]= new Frame();
        }
    }

    //Retourne le tableau des buffers
    public Frame[] getBufferPool(){
        return bufferPool;
    }

    //Modifier bufferpool
    public void setBufferPool(Frame[] bufferPool){
        this.bufferPool=bufferPool;
    }

    //Instance du buffermanager
    public static BufferManager leBufferManager(){
        return leBufferManager;
    }

    //Retourne l'instance du disque manager
    public DiskManager getDManager(){
        return dManager;
    }

    public void setDManager(DiskManager dManager){
        this.dManager=dManager;
    }

    //****************************** FONCTIONS PRINCIPALES *****************************//

    //Retourne un des buffers associés à une frame
    public byte[] getPage(PageId pageId) throws IOException{
        //Buffer = contenu page désignée par pageId
        // recuperer le buffer de diskmanager
        //S'occuper du remplacement (LRU) du contenu d'une frame
       
        int num_id=-1; //numéro id de la frame dispo

        for(int i=0; i<bufferPool.length;i++){
            if(bufferPool[i].getPage().equals(pageId)){ //Si la page existe deja
                return (bufferPool[i].getBuffer()); // On retourne le buffer de la page
            }
            if(bufferPool[i].isEmpty() && num_id==-1){
                num_id=i;
            }
        }
        if(num_id!=-1){ //Si la page n'est pas dans le bufferpool (inexistante)
            bufferPool[num_id].setPage(pageId);
            bufferPool[num_id].setPinCount(1);
            bufferPool[num_id].setFlagDirty(false);
            dManager.readPage(pageId, bufferPool[num_id].getBuffer());
        }
        else{
            //FAIRE LRU
        }
        return bufferPool[num_id].getBuffer();
    }


    //Décrémente pin_count et actualise flag dirty de la page
    public void freePage(PageId pageId, boolean valdirty) throws IOException{
        /*********  A FINIR 
       
        tant que i=0 est inférieur au nb de buffers et que pageId non trouvé, on incremente i 
        si la taille du bufferpool est inférieur à i ou(/et?) pin count de bufferpool[i]==0
            on décremente le pincount et on modifie flag dirty avec param
        */
        int i=0;

        while(i<bufferPool.length && bufferPool[i].getPage().equals(pageId)==false){
            i++;
        }
        if(bufferPool.length<i || bufferPool[i].getPinCount()==0){
            bufferPool[i].decrementPinCount();
            bufferPool[i].setFlagDirty(valdirty);
        }
    }


    //Ecriture des pages modifiées et remise a 0 des flags/contenus dans buffers
    public void flushAll() throws IOException{
        //Ecriture des pages où flag dirty=true sur disque
        //Remise à 0 de tous les flags/infos et contenus des buffers
        //Rajouter un appel a la methode, dans la méthode Finish du DBManager
        
        Frame frame=new Frame();
    
        for(int i=0;i<bufferPool.length;i++){ //On parcourt tous les buffers
            if(frame.getFlagDirty()){ //Si frame modifiée, on obtient ses pages du DManager
                dManager.writePage(frame.getPage(), frame.getBuffer());
            }
            //remise à 0 des variables des pages
            frame.setPage(new PageId(-1, 0));
            frame.setPinCount(0);
            frame.setFlagDirty(false);
        }
    }
}

