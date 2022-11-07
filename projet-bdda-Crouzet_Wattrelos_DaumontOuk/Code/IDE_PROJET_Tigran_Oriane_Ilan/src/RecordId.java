public class RecordId {

    //Attributs
    private PageId pageId; //indique la page à laquelle appartient le record
    private int slotIdx; //’indice de la case du slot directory qui pointe vers le record

    //Constructeur
    public RecordId(PageId pageId, int slotIdx){
        this.pageId=pageId;
        this.slotIdx=slotIdx;
    }

    //Méthodes
    public PageId getPageId(){
        return pageId;
    }

    public int getSlotIdx(){
        return slotIdx;
    }
}