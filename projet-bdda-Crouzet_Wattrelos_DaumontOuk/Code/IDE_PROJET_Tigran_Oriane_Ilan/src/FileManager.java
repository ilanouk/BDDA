import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
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

		if(pageId.toString().length() < sizeRecord){ //OUI? NON? PageId.getFile()
			buffM.freePage(pageId, false);
			return addDataPage(relInfo);
		}
		else{
			return null;
		}
	}

	//Utiliser une méthode du TP4 Record pour écrire le record dans le pageId
	public RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		byte[] buffer = buffM.getPage(pageId);
		RecordId recordId = new RecordId(pageId, 0);

		//Enregistrer record dans pageId

		buffM.freePage(pageId, true);
		
		return recordId;
		
	}

	// A FINIR !!!
	//Renvoie la liste des records stockés dans la pageId
	public List<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId){
		BufferManager buffM = BufferManager.getLeBufferManager();

		//Utiliser une methode du TP4 Record

		return null;
		
	}

	// A FINIR !!!
	//Renvoie les pageId des pages de données, ceux de la HeaderPage
	public List<PageId> getAllDataPages (RelationInfo relInfo){
		return null;
	}

	//Insertion d'un record dans une relation
	public RecordId InsertRecordIntoRelation (Record record) throws IOException {
		return this.writeRecordToDataPage(record, this.getFreeDataPageId(null, 0));
	}

	//Lister tous les records dans une relation
	public List<Record> GetAllRecords (RelationInfo relInfo) throws IOException{
		return this.getRecordsInDataPage(relInfo, this.getFreeDataPageId(relInfo, 0));
	}
}