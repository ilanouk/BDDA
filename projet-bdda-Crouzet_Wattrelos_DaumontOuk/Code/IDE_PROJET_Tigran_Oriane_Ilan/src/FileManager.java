import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();

	public static FileManager getFileManager(){
		return leFileManager;
	}

	//PAS SUR
	private PageId lirePageIdDepuisPageBuffer(ByteBuffer buff, boolean prem){
		int pageIdInt = prem? buff.getInt(0) : buff.getInt(3); // ajouter 3 entiers (2 pour l'info de la page et 1 pour la taille dispo)

		int fileIdx = pageIdInt/10;
		int pageIdx = pageIdInt%10;
		PageId pageId = new PageId(fileIdx, pageIdx);
		
		return pageId;
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

	//Utiliser une méthode du TP4 Record pour écrire le record dans le pageId
	//Ecrit l'enregistrement record dans la data page de pageId & renvoie son recordId
	private RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		HeaderPage hP = new HeaderPage(pageId);
		RecordId recordId = new RecordId(pageId, 0);


		//Enregistrer record dans pageId

		buffM.freePage(pageId, true);
		
		return recordId;
		
	}

	// A FINIR !!!
	//Renvoie la liste des records stockés dans la pageId
	private List<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId){
		BufferManager buffM = BufferManager.getLeBufferManager();

		//Utiliser une methode du TP4 Record

		return null;
		
	}

	// A FINIR !!!
	//Renvoie les pageId des pages de données, ceux de la HeaderPage
	public ArrayList<PageId> getAllDataPages (RelationInfo relInfo){
		ArrayList<PageId> dataPages = new ArrayList<PageId>();

		/*for(PageId : relInfo.getColonnes()){
			dataPages.addAll();
		}*/

		return dataPages;
	}

	//Insertion d'un record dans une relation

	public RecordId InsertRecordIntoRelation (Record record) throws IOException {
		int recSize = record.getWrittenSize();
		return writeRecordToDataPage(record, getFreeDataPageId(null, recSize));
	}

	//Lister tous les records dans une relation
	public List<Record> GetAllRecords (RelationInfo relInfo) throws IOException{
		return getRecordsInDataPage(relInfo, getFreeDataPageId(relInfo, 0));
	}


}