
public class PageId {
	
	private int FileIdx;
	private int PageIdx;
	
	
	public PageId(int FileIdx,int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
	}
	
	public int getFile(){
		return FileIdx;
	}
	
	public int getPage(){
		return PageIdx;
	}
}
