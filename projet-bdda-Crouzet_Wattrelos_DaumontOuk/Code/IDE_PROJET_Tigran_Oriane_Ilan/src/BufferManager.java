import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public class BufferManager {
    
    private static BufferManager leBufferManager = null;
    private DiskManager dManager;
    private Frame[] bufferPool; //liste des buffers
    private Stack<Frame> frameNonUtilisee;

    //Constructeur
    public BufferManager(){ //private avec singleton?
        this.bufferPool = new Frame[DBParams.frameCount];
        this.dManager = DiskManager.getLeDiskManager();
        this.frameNonUtilisee = new Stack<Frame>();  
        initBufferPool();
    }

    public void initBufferPool(){ 
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
    public static BufferManager getLeBufferManager(){
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
            //Removes the object at the top of this stack and returns thatobject as the value of this function
            Frame f = frameNonUtilisee.pop();

            if(f.getFlagDirty()==true){ // Si frame modifiée, on sauvegarde sur disque
                dManager.writePage(f.getPage(), f.getBuffer());
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
    public void flushAll() throws IOException, FileNotFoundException{
    
    	for(int i=0; i<bufferPool.length;i++) {
    		if (bufferPool[i].getFlagDirty()) {
    			dManager.writePage(bufferPool[i].getPage(), bufferPool[i].getBuffer());
    		}
    		bufferPool[i].setPage(new PageId(-1, 0));
    		bufferPool[i].setPinCount(0);
    		bufferPool[i].setFlagDirty(false);
    	}
    }
}