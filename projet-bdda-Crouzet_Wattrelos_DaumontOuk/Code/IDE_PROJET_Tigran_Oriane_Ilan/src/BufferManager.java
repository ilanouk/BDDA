import java.io.File;
import java.nio.Buffer;

public class BufferManager {
    
    // CRRER 2 METHODES DANS DM, PAGEEXISTE ET SETBUFFER/GETBUFFER

    private byte[] buff; 

    //Retourne un des buffers associés à une frame
    public byte[] getPage(PageId pageId){
        //Buffer = contenu page désignée par pageId
        // recuperer le buffer de diskmanager
        //S'occuper du remplacement (LRU,clock) du contenu d'une frame
        
        
        for(int i=0;i<DBParams.maxPagesPerFile;i++){ //On parcoure toutes les pages
            for(int j=0;j<DBParams.pageSize;j++){ // On parcoure tous les fichiers de chaque pages
                //%%%%%%%%%%%%
                if( /*CREER !DiskManager.pageIdExiste(pageId) && */pageId.getPin_Count()!=0){ //Si la page n'existe pas et aucun utilisateur dessus
                    //%%%%%%%%%%%%
                    // buff=DiskManager.setBuffer(pageId);
                }
                else{
                    //choisir fichier libre donc algo LRU/clock
                    //lire contenu page demandée dans le fichier
                }
                //pin_count++;
            }
        }
        return buff; // Retourne le buffer avec le contenu de la page
    }

    //Décrémente pin_count et actualise flag dirty de la page
    public void freePage(PageId pageId, boolean valdirty){
        
        pageId.setPin_Count(0);
        pageId.setValDirty(false);
    }

    //Ecriture des pages modifiées et remise a 0 des flags/contenus dans buffers
    public void flushAll(){
        //Ecriture des pages où flag dirty=true sur disque
        //Remise à 0 de tous les flags/infos et contenus des buffers
        //Rajouter un appel a la methode, dans la méthode Finish du DBManager
        
        for(int i=0;i<DBParams.maxPagesPerFile;i++){ //On parcoure toutes les pages
            for(int j=0;j<DBParams.pageSize;j++){ // On parcoure tous les fichiers de chaque pages
                /*if(pageId.getValDirty()==true){
                    ECRITURE DES PAGES Où DIRTY=TRUE
                    Faire un freePage?
                }*/

            }
        }
    }
}

