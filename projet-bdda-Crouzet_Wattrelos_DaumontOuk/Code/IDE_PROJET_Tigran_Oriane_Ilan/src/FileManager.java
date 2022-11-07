import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();

	//Méthode permettant d'écrire une pageId dans un buffer
	/*
	 * Petite indication sur la conversion bit/octet
	 * - Pour 4 octets, on utilise UTF-32
	 * - Pour 1 octet, on utilise UTF-8
	 */
	//ERREUR AVEC ENCODAGE 
	public static void ecrirePageIdDansBuffer(PageId pageId, byte[] buff, int octet) throws UnsupportedEncodingException{
		String pageIdString = pageId.getFile() +""+ pageId.getPage();

		if( octet==4 ){
			buff = pageIdString.getBytes("UTF-32");
		}
		else if( octet==8 ){
			buff = pageIdString.getBytes("UTF-64");
		}
	}
	
	//allocation d’une nouvelle page via AllocPage du DiskManager et écriture dans la page allouée
	public static PageId createNewHeaderPage() throws IOException {
		//Création des instances
		DiskManager diskM = DiskManager.getLeDiskManager();
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = diskM.allocPage();
		byte[] buffer = buffM.getPage(pageId);

		ecrirePageIdDansBuffer(new PageId(-1, 0), buffer, 0);
		ecrirePageIdDansBuffer(new PageId(-1, 0), buffer, 4);
		
		//Libérer page allouée auprès du Buffer Manager
		buffM.freePage(pageId,true);

		return pageId;
		// A TESTER PAS SUR DU FONCTIONNEMENT
	}

	public PageId addDataPage(RelationInfo relInf) throws IOException {
		//Création des instances
		DiskManager diskM = DiskManager.getLeDiskManager();
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = diskM.allocPage();
		// byte[] buffer = buffM.getPage(pageId);
		byte[] bufferHeaderPage = buffM.getPage(relInf.getHeaderPageId());

		ecrirePageIdDansBuffer(pageId, bufferHeaderPage, 8);
		ecrirePageIdDansBuffer(relInf.getHeaderPageId(), bufferHeaderPage, 0);
		// ecrirePageIdDansBuffer(pageId, buffer, 0);

		buffM.freePage(pageId, true);
		buffM.freePage(relInf.getHeaderPageId(), true);

		return pageId;
	}

	public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = relInfo.getHeaderPageId();

		if(pageId.getFile() < sizeRecord){
			buffM.freePage(pageId, false);
			return addDataPage(relInfo);
		}
		else{
			return null;
		}
	}
	public RecordId writeRecordToDataPage (Record record, PageId pageId) {
		return null;
		
	}
	public List<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId){
		return null;
		
	}
	public List<PageId> getAllDataPages (RelationInfo relInfo){
		return null;
	}
	public RecordId InsertRecordIntoRelation (Record record) {
		return null;
	}
	public List<Record> GetAllRecords (RelationInfo relInfo){
		return null;
	}
}