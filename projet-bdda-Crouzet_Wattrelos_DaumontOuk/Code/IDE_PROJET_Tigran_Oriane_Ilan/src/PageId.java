
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
}
