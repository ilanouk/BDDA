import java.io.IOException;

public class DiskManagerTests {
	public static int allocTest() throws IOException {
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
			PageId p1 = DiskManager.allocPage();
			DiskManager.allocPage();
	
			DiskManager.deallocPage(p1);
			return(DiskManager.getCurrentAllocPages());
		}
			
	public static void main(String[] args) throws IOException {
		System.out.println(allocTest());
	}
}
