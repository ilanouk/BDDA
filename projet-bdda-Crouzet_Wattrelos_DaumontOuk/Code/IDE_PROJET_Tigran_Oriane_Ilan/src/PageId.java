
public class PageId {
	
	private int FileIdx;
	private int PageIdx;
	private int pin_count;
	private boolean valdirty;
	
	//Initialisation
	public PageId(int FileIdx,int PageIdx) {
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
		this.pin_count=0;
		this.valdirty=false;
	}
	
	//Retourne le numéro de fichier
	public int getFile(){
		return FileIdx;
	}
	
	//Retourne le numéro de page
	public int getPage(){
		return PageIdx;
	}

	//Retourne le nb d'utilsateur sur une page
	public int getPin_Count(){
		return pin_count;
	}

	//Modifier le pin_count
	public void setPin_Count(int pin_count){
		this.pin_count=pin_count;
	}

	//Retourne si une page a été modifiée
	public boolean getValDirty(){
		return valdirty;
	}

	//Modifier le flag dirty
	public void setValDirty(boolean valdirty){
		this.valdirty=valdirty;
	}
}
