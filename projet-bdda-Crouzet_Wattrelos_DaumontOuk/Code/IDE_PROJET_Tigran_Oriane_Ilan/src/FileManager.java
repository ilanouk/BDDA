import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();

	public static FileManager getFileManager(){
		return leFileManager;
	}
	
	//OK
	//allocation d’une nouvelle page via AllocPage du DiskManager et écriture dans la page allouée
	public PageId createNewHeaderPage() throws IOException {
		//Création des instances
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = buffM.getDManager().allocPage() ;
		HeaderPage hp= new HeaderPage(pageId);
		hp.setTailleZero();
		
		//Libérer page allouée auprès du Buffer Manager
		buffM.freePage(pageId,true);

		return pageId;
	}

	//OK
	//Ajoute une page de données vide au Heap File correspondant à la relation relInfo
	public PageId addDataPage(RelationInfo relInf) throws IOException {
		//Création des instances
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = buffM.getDManager().allocPage();
		HeaderPage hP = new HeaderPage(relInf.getHeaderPageId());
		
		hP.addNewDataPage(pageId); //sauvegarde automatiquement la Data Page & le Heap File

		return pageId;
	}
	//méthode getFreeDataPage, qui prend en paramètre un entier sizeRecord et une relationInfo, retourne pour la relationInfo donnée, la page où il reste assez de place pour insérer un enregistrement de taille sizeRecord
	public PageId getFreeDataPageId( RelationInfo relInf, int sizeRecord) throws Exception {
		//Création des instances
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(relInf.getHeaderPageId());
		PageId pageId = hP.getDPEnoughSpace(sizeRecord);
		
		//Libérer page allouée auprès du Buffer Manager
		buffM.freePage(pageId, false);
		System.out.println("page : " + pageId);
		return pageId;
	}

	//PRESQUE
	//Ecrit l'enregistrement record dans la data page de pageId & renvoie son recordId
	public RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(pageId);
		RecordId recordId = new RecordId(pageId, 0);

		record.writeToBuffer(hP.gByteBuffer(), pageId.getPage());
		buffM.freePage(pageId, true);
		
		return recordId;
		
	}

	//A VERIFIER
	//Renvoie la liste des records stockés dans la pageId
	public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(pageId);
		ArrayList<Record> records = new ArrayList<Record>();
		Record record = new Record(relInfo);
		
		record.readFromBuffer(hP.gByteBuffer(), pageId.getPage());
		records.add(record);
		
		buffM.freePage(pageId, false);
		
		return records;
	}

	//A VERIFIER
	public ArrayList<PageId> getAllPageId(RelationInfo relInfo) throws IOException{
		System.out.println("relinfo " +relInfo);
		HeaderPage hP = new HeaderPage(relInfo.getHeaderPageId());
		ArrayList<PageId> allPageId = new ArrayList<PageId>();

		//allPageId.add(idx, hP.getPageId());
		//idx++;
		
		for (PageId pageId : hP.getAllDataPageId()) {
			allPageId.add(pageId);
		}

		return allPageId;
	}
	
	//A VERIFIER 
	//Insertion d'un record dans une relation
	public RecordId InsertRecordIntoRelation (Record record) throws Exception {
		int recSize = record.getWrittenSize();
		return this.writeRecordToDataPage(record, getFreeDataPageId(record.getRelInfo(), recSize)); 
	}

	//A VERIFIER
	//Lister tous les records dans une relation
	public ArrayList<Record> GetAllRecords (RelationInfo relInfo) throws IOException{
		ArrayList<Record> allRecord = new ArrayList<Record>();
		ArrayList<PageId> allPageId = getAllPageId(relInfo);
		while (allPageId.size()!=0) {
			allRecord.addAll(getRecordsInDataPage(relInfo, allPageId.get(0)));
			allPageId.remove(0);
	
		}
		return allRecord;
	}
}