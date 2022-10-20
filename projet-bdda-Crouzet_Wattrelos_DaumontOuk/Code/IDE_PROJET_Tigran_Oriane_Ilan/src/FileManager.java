import java.io.IOException;

public class FileManager {
	private static FileManager leFileManager=new FileManager();
	public static PageId CreateNewHeaderPage() throws IOException
	{
		PageId n = DiskManager.getLeDiskManager().allocPage();
		
		return n;
		// pas fini
	}
	public static PageId addDataPage(RelationInfo relInf) throws IOException {
		PageId p = DiskManager.getLeDiskManager().allocPage();
		int x=0;
		int y=0;
		return p;
		
	}

}
