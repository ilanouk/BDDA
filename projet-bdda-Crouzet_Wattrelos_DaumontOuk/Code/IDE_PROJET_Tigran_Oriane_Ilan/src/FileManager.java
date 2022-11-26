import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	//OK
	// Pour la relation désignée par relInfo, ca renvoie le pageId où il reste assez de place pour insérer le record
	private PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws IOException{

		PageId pageIdHeaderPage = relInfo.getHeaderPageId();
		HeaderPage hP = new HeaderPage(pageIdHeaderPage);

		return hP.getDPEnoughSpace(sizeRecord);
	}

	//PRESQUE
	//Ecrit l'enregistrement record dans la data page de pageId & renvoie son recordId
	private RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(pageId);
		RecordId recordId = new RecordId(pageId, 0);

		record.writeToBuffer(hP.gByteBuffer(), pageId.getPage());
		buffM.freePage(pageId, true);
		
		return recordId;
		
	}

	// A FINIR !!!
	//Renvoie la liste des records stockés dans la pageId
	private ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(pageId);
		Record record = new Record(relInfo);
		ArrayList<Record> allRecord = new ArrayList<Record>();
		int idx=0;

		// while(allRecord.get(idx)!=null){
		// 	idx++;
		// }

		record.readFromBuffer(hP.gByteBuffer(), pageId.getPage());
		allRecord.add(idx, record);

		return allRecord;
		
	}

	// A FINIR !!!
	//Renvoie les pageId des pages de données, ceux de la HeaderPage
	//Ici relInfo permet d'avoir le type (integer, real)
	public ArrayList<PageId> getAllDataPages (RelationInfo relInfo){
		ArrayList<PageId> dataPages = new ArrayList<PageId>();

		

		return dataPages;
	}

	//OK
	//Insertion d'un record dans une relation
	public RecordId InsertRecordIntoRelation (Record record) throws IOException {
		int recSize = record.getWrittenSize();
		return writeRecordToDataPage(record, getFreeDataPageId(null, recSize));
	}

	//OK
	//Lister tous les records dans une relation
	public List<Record> GetAllRecords (RelationInfo relInfo) throws IOException{
		return getRecordsInDataPage(relInfo, getFreeDataPageId(relInfo, 0));
	}


}