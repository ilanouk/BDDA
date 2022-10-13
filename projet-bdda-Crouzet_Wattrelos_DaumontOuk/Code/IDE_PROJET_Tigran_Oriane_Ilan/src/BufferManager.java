import java.io.IOException;
import java.util.ArrayList;

public class BufferManager {
    
    private static BufferManager leBufferManager = null;
    private DiskManager dManager;
    private Frame[] bufferPool; //liste des buffers
    private ArrayList<Frame> frameNonUtilisee;

    //Constructeur
    public BufferManager(){ //private avec singleton?
        this.bufferPool = new Frame[DBParams.frameCount];
        this.dManager = DiskManager.getLeDiskManager();
        this.frameNonUtilisee = new ArrayList<Frame>();  
        initBufferPool();
    }

    private void initBufferPool(){ 
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
        if(leBufferManager==null){
            leBufferManager = new BufferManager();
        }
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

        int index=-1; //Index de la frame

        for(int i=0; i<bufferPool.length;i++){

            if(bufferPool[i].getPage().equals(pageId)){ //Si la page existe deja dans bufferpool
                bufferPool[i].incrementPinCount();
                return (bufferPool[i].getBuffer()); // On retourne le buffer de la page
            }
            else if(bufferPool[i].isEmpty() && index==-1){ //S'il y a de la place, on garde l'indice/la position
                index=i;
            }
        }

        if(index!=-1){ //si une frame de vide, on "crée" la page
            bufferPool[index].setPage(pageId);
            bufferPool[index].setPinCount(1);
            bufferPool[index].setFlagDirty(false);
            dManager.readPage(pageId, bufferPool[index].getBuffer());
        }
        else{
            //Algo LRU
            //Ajouter le premier element de Frame
            Frame f = frameNonUtilisee.get(0);

            if(f.getFlagDirty()==true){ // Si frame modifiée, on sauvegarde sur disque
                dManager.writePage(f.getPage(), f.getBuffer());
                frameNonUtilisee.remove(0);
            }

            index=0;
            //Chercher l'id de la derniere frame dans le buffer
            while(index<bufferPool.length && !bufferPool[index].equals(f)){
                   index++;
            }                
            //Remplacement avec la nouvelle frame
            bufferPool[index].setPage(pageId);
            bufferPool[index].setPinCount(1);
            bufferPool[index].setFlagDirty(false);
            dManager.readPage(pageId, bufferPool[index].getBuffer());
        }
        return bufferPool[index].getBuffer();
    }


    //Décrémente pin_count et actualise flag dirty de la page
    public void freePage(PageId pageId, boolean valdirty) throws IOException{
       
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

