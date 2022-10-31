import java.io.IOException;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();
	
	//allocation d’une nouvelle page via AllocPage du DiskManager et écriture dans la page allouée
	public static PageId createNewHeaderPage() throws IOException {
		//Création des instances
		DiskManager diskM = DiskManager.getLeDiskManager();
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = diskM.allocPage();
		byte[] buffer = buffM.getPage(pageId);

		//ECRIRE pageID dans buffer
		
		return pageId;
		// A FINIR !!!
	}
	public static PageId addDataPage(RelationInfo relInf) throws IOException {
		PageId p = DiskManager.getLeDiskManager().allocPage();
		int x=0;
		int y=0;
		return p;
		
	}

}
