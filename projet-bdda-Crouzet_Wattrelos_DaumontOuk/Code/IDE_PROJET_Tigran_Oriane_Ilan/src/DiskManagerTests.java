
public class DiskManagerTests {
	public static int allocTest(){
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
			PageId p1 = DiskManager.allocPage();
			DiskManager.allocPage();
	
			DiskManager.deallocPage(p1);
			return(DiskManager.getCurrentAllocPages());
		}
			
	public static void main(String[] args) {
		System.out.println(allocTest());
	}
}
