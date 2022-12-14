import java.nio.ByteBuffer;

public class Frame {
    private PageId pageIdx;
    private int pinCount;
    private boolean flagDirty;
    private ByteBuffer buff;

    public Frame(){
        pageIdx = new PageId(-1, 0);
        pinCount = 0;
        flagDirty = false;
        buff= ByteBuffer.allocate(DBParams.pageSize);
    }

    //Retourne le pin count
    public int getPinCount(){
        return this.pinCount;
    }

    //Retourne le numéro de page
	public PageId getPage(){
		return pageIdx;
	}

    //Retourne le flag dirty (page modifiée)
    public boolean getFlagDirty(){
        return this.flagDirty;
    }

    //Retourne le buffer
    public ByteBuffer getBuffer(){
        return this.buff;
    }

    //Modifier le pin count
    public void setPinCount(int pinCount){
        this.pinCount = pinCount;
    }

    //Modifier le flag dirty
    public void setFlagDirty(boolean flagDirty){
        this.flagDirty = flagDirty;
    }

    //Modifier le buffer
    public void setBuffer(ByteBuffer buff){
        this.buff = buff;
    }

    //Modifier la page
	public void setPage(PageId pageIdx){
		this.pageIdx=pageIdx;
	}

    public void incrementPinCount(){
        this.pinCount++;
    }

    public void decrementPinCount(){
        this.pinCount--;
    }
    
    public String toString() {
    	return ""+pageIdx+ " et PinCount : "+pinCount;
    }

    //Savoir si la frame est vide (true)
    public boolean isEmpty()
    {
        if (pageIdx.getFile() == -1)
            return (true);
        return (false);
    }
}
