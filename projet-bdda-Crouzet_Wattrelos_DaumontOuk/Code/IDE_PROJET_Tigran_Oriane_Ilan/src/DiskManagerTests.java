import java.io.IOException;

public class DiskManagerTests {

	public static int allocTest() throws IOException {
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
			PageId p1 = DiskManager.getLeDiskManager().allocPage();
			//PageId p2 = DiskManager.getLeDiskManager().allocPage();
			//PageId p3 = DiskManager.getLeDiskManager().allocPage();
			DiskManager.getLeDiskManager().deallocPage(p1);

			return(DiskManager.getLeDiskManager().getCurrentAllocPages());
		}

		public static void TestLireEcrire()  throws IOException{
			byte[] buff ;
			byte[] fin = new byte[100];
			buff = "coucou".getBytes();
			PageId p1 = DiskManager.getLeDiskManager().allocPage();

			DiskManager.getLeDiskManager().writePage(p1, buff);
			DiskManager.getLeDiskManager().readPage(p1, fin);

			String message=new String(fin);
			System.out.println("Doit afficher coucou : " +message);
		}
			
	public static void main(String[] args) throws IOException {
		DiskManager.recupTabPageLibre();
		
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		System.out.println("current page alloc : "+allocTest());
		TestLireEcrire();
		System.out.println("tableau : "+DiskManager.getLeDiskManager().afficherTab());
		DiskManager.sauvegardeTabPageLibre();
	}
}
