
public class Frame {
    private PageId pageId;
    private int pinCount;
    private boolean flagDirty;
    private byte[] buffer;

    public Frame()
    {
        pageId = new PageId(-1, 0);
        pinCount = 0;
        flagDirty = false;
        //Initaliser buff?
    }

    // Getter of the pinCount property
    public int getPinCount()
    {
        return this.pinCount;
    }

    // Getter of the flagDirty property
    public boolean getFlagDirty()
    {
        return this.flagDirty;
    }

    // Getter of the buffer property
    public byte[] getBuffer()
    {
        return this.buffer;
    }

    // Setter of the pinCount property
    public void setPinCount(int pinCount)
    {
        this.pinCount = pinCount;
    }

    // Setter of the flagDirty property
    public void setFlagDirty(boolean flagDirty)
    {
        this.flagDirty = flagDirty;
    }

    // Setter of the buffer property
    public void setBuffer(byte[] buffer)
    {
        this.buffer = buffer;
    }

    public void incrementPinCount()
    {
        this.pinCount++;
    }

    public void decrementPinCount()
    {
        this.pinCount--;
    }

    public boolean isEmpty()
    {
        if (pageId.getFile() == -1)
            return (true);
        return (false);
    }
}
