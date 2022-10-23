import java.io.IOException;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();
	
	//allocation d’une nouvelle page via AllocPage du DiskManager et écriture dans la page allouée
	public static PageId CreateNewHeaderPage() throws IOException
	{
		//Création des instances
		PageId pageId = DiskManager.getLeDiskManager().allocPage();
		DiskManager diskM = DiskManager.getLeDiskManager();
        BufferManager buffM = BufferManager.getLeBufferManager();
		byte[] buffer = buffM.getPage(pageId);
		
		return pageId;
		// pas fini
	}
	public static PageId addDataPage(RelationInfo relInf) throws IOException {
		PageId p = DiskManager.getLeDiskManager().allocPage();
		int x=0;
		int y=0;
		return p;
		
	}

}
