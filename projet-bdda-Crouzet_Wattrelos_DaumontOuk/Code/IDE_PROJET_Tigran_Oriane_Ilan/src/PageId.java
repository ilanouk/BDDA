
public class PageId {
	
	private int FileIdx;
	private int PageIdx;
	private int pin_count;
	private boolean valdirty;
	
	
	public PageId(int FileIdx,int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
		this.pin_count=0;
		this.valdirty=false;
	}
	
	public int getFile(){
		return FileIdx;
	}
	
	public int getPage(){
		return PageIdx;
	}

	public int getPin_Count(){
		return pin_count;
	}

	public void setPin_Count(int pin_count){
		this.pin_count=pin_count;
	}

	public boolean getValDirty(){
		return valdirty;
	}

	public void setValDirty(boolean valdirty){
		this.valdirty=valdirty;
	}
}
