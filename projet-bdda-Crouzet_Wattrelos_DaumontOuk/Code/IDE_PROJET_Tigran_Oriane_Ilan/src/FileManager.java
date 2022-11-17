import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();

	//Méthode permettant d'écrire une pageId dans un buffer, avec prem pour savoir si c'est au debut de la page
	public static void ecrirePageIdDansBuffer(PageId pageId, ByteBuffer buff, boolean prem) throws UnsupportedEncodingException{
		String tmp = pageId.getFile()+""+pageId.getPage();
		int page = Integer.valueOf(tmp);

		if(prem){
			buff.putInt(0, page);
		}
		else{ buff.putInt(8,page); }
	}

	public static PageId lirePageIdDepuisPageBuffer(ByteBuffer buff, boolean prem){
		int pageIdInt = prem? buff.getInt(0) : buff.getInt(3); // ajouter 3 entiers (2 pour l'info de la page et 1 pour la taille dispo)

		int fileIdx = pageIdInt/10;
		int pageIdx = pageIdInt%10;
		PageId pageId = new PageId(fileIdx, pageIdx);
		
		return pageId;
	}
	
	//allocation d’une nouvelle page via AllocPage du DiskManager et écriture dans la page allouée
	public static PageId createNewHeaderPage() throws IOException {
		//Création des instances
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = buffM.getDManager().allocPage() ;
		HeaderPage hp= new HeaderPage(pageId);

		ecrirePageIdDansBuffer(new PageId(-1, 0), hp.gByteBuffer(), true);
		ecrirePageIdDansBuffer(new PageId(-1, 0), hp.gByteBuffer(), false);
		
		//Libérer page allouée auprès du Buffer Manager
		buffM.freePage(pageId,true);

		return pageId;
		// A TESTER PAS SUR DU FONCTIONNEMENT
	}

	public PageId addDataPage(RelationInfo relInf) throws IOException {
		//Création des instances
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageId = buffM.getDManager().allocPage();
		// byte[] buffer = buffM.getPage(pageId);
		HeaderPage bufferHeaderPage = new HeaderPage(pageId);
		//PageId prochainePageId =  lirePageIdDepuisPageBuffer(bufferHeaderPage.gByteBuffer(), true);

		//ecrirePageIdDansBuffer(pageId, bufferHeaderPage, false);
		
		ecrirePageIdDansBuffer(relInf.getHeaderPageId(), bufferHeaderPage.gByteBuffer(), false);
		//ecrirePageIdDansBuffer(prochainePageId, buff, true);

		
		buffM.freePage(relInf.getHeaderPageId(), true);

		return pageId;
	}

	public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		PageId pageIdHeaderPage = relInfo.getHeaderPageId();
		ByteBuffer bufferHeaderPage = buffM.getPage(pageIdHeaderPage);
		PageId pageId = lirePageIdDepuisPageBuffer(bufferHeaderPage, true);

		// Si sizeRecord trop grand ou page inexistante, return null
		if( pageId.toString().length() < sizeRecord || pageId.getFile()==-1 ){
			return null;
		}

		buffM.freePage(pageId, false);

		return pageId;
	}

	//Utiliser une méthode du TP4 Record pour écrire le record dans le pageId
	public RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException{
		BufferManager buffM = BufferManager.getLeBufferManager();
		ByteBuffer buffer = buffM.getPage(pageId);
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
	public ArrayList<PageId> getAllDataPages (RelationInfo relInfo){
		ArrayList<PageId> dataPages = new ArrayList<PageId>();

		/*for(PageId : relInfo.getColonnes()){
			dataPages.addAll();
		}*/

		return dataPages;
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