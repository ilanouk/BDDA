
public class PageId {
	
	private int FileIdx;
	private int PageIdx;
	
	//Initialisation
	public PageId(int FileIdx,int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
	}
	
	//Retourne le numéro de fichier
	public int getFile(){
		return FileIdx;
	}
	
	//Retourne le numéro de page
	public int getPage(){
		return PageIdx;
	}

	//Modifier le fichier
	public void setFile(int FileIdx){
		this.FileIdx=FileIdx;
	}

	//Modifier la page
	public void setPage(int PageIdx){
		this.PageIdx=PageIdx;
	}

}
