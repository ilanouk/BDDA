import java.io.File;
import java.nio.Buffer;

public class BufferManager {
    
    private int pin_count=0; //Nb d'utilisateurs sur la page
    private boolean valdirty=false; //Vaut true si page modifiée
    // CREER private byte[] buff = DiskManager.getBuffer(); 

    //Retourne un des buffers associés à une frame
    public byte[] GetPage(PageId pageId){
        //Buffer = contenu page désignée par pageId
        // recuperer le buffer de diskmanager
        //S'occuper du remplacement (LRU,clock) du contenu d'une frame
        
        
        for(int i=0;i<DBParams.maxPagesPerFile;i++){ //On parcoure toutes les pages
            for(int j=0;j<DBParams.pageSize;j++){ // On parcoure tous les fichiers de chaque pages
                //%%%%%%%%%%%%
                if( /*CREER !DiskManager.pageIdExiste(pageId) && */pin_count==0){ //Si la page n'existe pas et aucun utilisateur dessus
                    if(valdirty==true){
                        //ecrire contenu sur disque
                        //valdirty=false;
                    }
                }
                else{
                    //choisir fichier libre
                    //lire contenu page demandée dans le fichier
                }
                //pin_count++;
            }
        }
        return null; // DOIT RETOURNER LE BUFFER
    }

    //Décrémente pin_count et actualise flag dirty de la page
    public void FreePage(PageId pageId, boolean valdirty){
        //Peut aussi actualiser infos sur la politique de remplacement
        pageId.setPin_Count(0);
        pageId.setValDirty(false);
    }

    //Ecriture des pages modifiées et remise a 0 des flags/contenus dans buffers
    public void FlushAll(){
        //Ecriture des pages où flag dirty=true sur disque
        //Remise à 0 de tous les flags/infos et contenus des buffers
        //Rajouter un appel a la methode, dans la méthode Finish du DBManager
        
    }

}
