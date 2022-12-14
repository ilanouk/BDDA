import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FileManager {
	
	private static FileManager leFileManager=new FileManager();

	public static FileManager getFileManager(){
		return leFileManager;
	}
	
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
		PageId pageIdHp = relInf.getHeaderPageId();
		ByteBuffer buffer = buffM.getPage(pageIdHp);
		PageId pageId = readPageIdFromPageBuffer(buffer, true);

		if(pageId.getFile()==-1){
			return this.addDataPage(relInf);
		}
		
		//Libérer page allouée auprès du Buffer Manager
		buffM.freePage(pageIdHp, false);
		
		return pageId;
	}

	//Ecrit l'enregistrement record dans la data page de pageId & renvoie son recordId
	public RecordId writeRecordToDataPage (Record record, PageId pageId) throws IOException, IndexOutOfBoundsException{
		BufferManager bm = BufferManager.getLeBufferManager();
		ByteBuffer headerPage = bm.getPage(pageId);
		ByteBuffer bufCurPageId = bm.getPage(pageId);
		int freeSlot = 0, freePos = 0, cursor = 16 ; ////////// relinfo.getHeaderPageId

		for(int i = 16; i < 16 + 16; i++){ /////////// relInfo.calculSlotCount
			if(bufCurPageId.get(i) == 0){
				freeSlot++;
				if(freeSlot == 1){
					freePos = i - 16;
				}
			}
		}

		for(int i = 0; i < freePos; i++){
			cursor += 16; /////////////
		}

		record.writeToBuffer(bufCurPageId, cursor);
		bufCurPageId.put(16 + freePos, (byte)1);
		if(freeSlot == 1){
			PageId prevPage = readPageIdFromPageBuffer(bufCurPageId, true);
			PageId nextPage = readPageIdFromPageBuffer(bufCurPageId, false);

			ByteBuffer prevBuf = bm.getPage(prevPage);
			ByteBuffer nextBuf = bm.getPage(nextPage);

			writePageIdToPageBuffer(pageId, headerPage, false);
			writePageIdToPageBuffer(pageId, bufCurPageId, true); //////////////

			if(!prevPage.equals(pageId)){ ///////////////
				writePageIdToPageBuffer(nextPage, prevBuf, false);
				writePageIdToPageBuffer(prevPage, nextBuf, true);
			} else {
				writePageIdToPageBuffer(nextPage, headerPage, true);
				writePageIdToPageBuffer(pageId, nextBuf, true); //////////////
				
			}

			bm.freePage(prevPage, true);
			bm.freePage(nextPage, true);

		}

		bm.freePage(pageId, true);
		bm.freePage(pageId, true); /////////////////


		return new RecordId(pageId, freePos);
		
	}

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

	public ArrayList<PageId> getAllPageId(RelationInfo relInfo) throws IOException{
		HeaderPage hP = new HeaderPage(relInfo.getHeaderPageId());
		ArrayList<PageId> allPageId = new ArrayList<PageId>();

		//allPageId.add(idx, hP.getPageId());
		//idx++;
		
		for (PageId pageId : hP.getAllDataPageId()) {
			allPageId.add(pageId);
		}

		return allPageId;
	}
	
	//Insertion d'un record dans une relation
	public RecordId InsertRecordIntoRelation (Record record) throws Exception {
		int recSize = record.getWrittenSize();
		return this.writeRecordToDataPage(record, getFreeDataPageId(record.getRelInfo(), recSize)); 
	}

	//Lister tous les records dans une relation
	public ArrayList<Record> GetAllRecords (RelationInfo relInfo) throws Exception{
		ArrayList<Record> allRecord = new ArrayList<Record>();
		ArrayList<PageId> allPageId = getAllPageId(relInfo);
		while (allPageId.size()!=0) {
			allRecord.addAll(getRecordsInDataPage(relInfo, allPageId.get(0)));
			allPageId.remove(0);
	
		}
		return allRecord;
	}

	public PageId readPageIdFromPageBuffer(ByteBuffer buf, boolean first)
	{
		int pageIdint = first? buf.getInt(0): buf.getInt(3);

		int fileIdx = pageIdint / 10;
		int pageIdx = pageIdint % 10;
		PageId pageId = new PageId(fileIdx, pageIdx); 

		return pageId;
    }

	public void writePageIdToPageBuffer(PageId pageId, ByteBuffer buf, boolean first)
	{
		String tmp = pageId.getFile() + "" + pageId.getPage();

		int pageIdInt = Integer.valueOf(tmp);

		if(first)
			buf.putInt(0, pageIdInt);
		else
			buf.putInt(8, pageIdInt);
    }
}