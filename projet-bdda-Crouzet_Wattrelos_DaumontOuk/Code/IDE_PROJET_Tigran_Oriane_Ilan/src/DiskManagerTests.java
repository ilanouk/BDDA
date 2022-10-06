import java.io.IOException;

public class DiskManagerTests {

	public static int allocTest() throws IOException {
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
			PageId p1 = DiskManager.getLeDiskManager().allocPage();
			PageId p2 = DiskManager.getLeDiskManager().allocPage();
			//PageId p3 = DiskManager.getLeDiskManager().allocPage();
			DiskManager.getLeDiskManager().deallocPage(p1);

			return(DiskManager.getLeDiskManager().getCurrentAllocPages());
		}

		public static void TestLireEcrire()  throws IOException{
			byte[] buff , buff1;
			byte[] fin = new byte[100];
			buff = "coucou".getBytes();
			buff1 = "tata".getBytes();
			PageId p1 = DiskManager.getLeDiskManager().allocPage();
			PageId p2 = DiskManager.getLeDiskManager().allocPage();
			
			DiskManager.getLeDiskManager().writePage(p1, buff1);
			DiskManager.getLeDiskManager().writePage(p2, buff);

			DiskManager.getLeDiskManager().readPage(p1, fin);

			String message=new String(fin);
			System.out.println("Doit afficher tata : " +message);
		}
			
	
		public static void main(String[] args) throws IOException {
		DiskManager.recupTabPageLibre();
		
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		DBParams.frameCount=2;
		
		System.out.println(" current alloc page : " + DiskManager.getLeDiskManager().getCurrentAllocPages());
		//System.out.println("tableau : "+DiskManager.getLeDiskManager().afficherTab());
		DiskManager.sauvegardeTabPageLibre();
	}
}
