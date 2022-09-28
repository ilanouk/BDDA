import java.io.IOException;

public class DiskManagerTests {
	public static int allocTest() throws IOException {
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
			PageId p1 = DiskManager.allocPage();
			DiskManager.allocPage();
			DiskManager.deallocPage(p1);
			return(DiskManager.getCurrentAllocPages());
		}
		public static void TestLireEcrire()  throws IOException{
			byte[] buff ;
			byte[] fin = new byte[100];
			buff = "coucoue".getBytes();
			PageId p1 = DiskManager.allocPage();
			DiskManager.writePage(p1, buff);
			DiskManager.readPage(p1, fin);
			String message=fin.toString();
			System.out.println(message);
		}
			
	public static void main(String[] args) throws IOException {
		DBParams.DBpath ="../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		
		System.out.println(allocTest());
		TestLireEcrire();
	}
}
