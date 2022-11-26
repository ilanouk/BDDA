import java.io.IOException;
import java.nio.ByteBuffer;

public class DiskManagerTests {

	public static int allocTest() throws IOException {
		// Alloue 2 pages et désalloue une page. La fonction doit retourner 1
		PageId p1 = DiskManager.getLeDiskManager().allocPage();
		// PageId p2 = DiskManager.getLeDiskManager().allocPage();
		DiskManager.getLeDiskManager().deallocPage(p1);

		return (DiskManager.getLeDiskManager().getCurrentAllocPages());
	}

	public static void TestLireEcrire() throws IOException {
		ByteBuffer buff, buff1;
		ByteBuffer fin = ByteBuffer.allocate(100);
		buff = ByteBuffer.wrap("coucou".getBytes());
		buff1 = ByteBuffer.wrap("tata".getBytes());
		PageId p1 = DiskManager.getLeDiskManager().allocPage();
		PageId p2 = DiskManager.getLeDiskManager().allocPage();

		DiskManager.getLeDiskManager().writePage(p1, buff1);
		DiskManager.getLeDiskManager().writePage(p2, buff);

		DiskManager.getLeDiskManager().readPage(p1, fin);

		String message = new String(fin.array());
		System.out.println("Doit afficher tata : " + message);
	}

	public static void main(String[] args) throws IOException {
		DiskManager.recupTabPageLibre();

		DBParams.DBpath = "../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		DBParams.frameCount = 2;

		TestLireEcrire();
		System.out.println(" current alloc page : " + DiskManager.getLeDiskManager().getCurrentAllocPages());
		// System.out.println("tableau :
		// "+DiskManager.getLeDiskManager().afficherTab());
		DiskManager.sauvegardeTabPageLibre();
	}
}
