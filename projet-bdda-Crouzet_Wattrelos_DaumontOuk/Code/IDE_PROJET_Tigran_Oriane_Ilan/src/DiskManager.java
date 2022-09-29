import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class DiskManager {
	
	private static DiskManager LeDiskManager = new DiskManager();
	private static byte[] buff;
	public static ArrayList<PageId> tabPageLibre = new ArrayList<PageId>(); //tableau qui stock la liste des pages libres

	public static  DiskManager getLeDiskManager(){
		return LeDiskManager;
	}


	//Allouer une page
	public PageId allocPage() throws IOException {
		
		int numFichier=1;
		if (tabPageLibre.size() == 0) { // si le tableau des page Libre est vide alors on crée un nouveau fichier
			while(true){
				String nomFichier = "F"+numFichier+".bdda";
				File fichier = new File("../../DB/"+nomFichier); 
				if (!fichier.exists()){ //On crée 4 pages, et on alloue la 1ère, les autres sont indiquées comme page libres
					
					fichier.createNewFile();
					System.out.println("num fichier actuelle : " +numFichier);
					tabPageLibre.add( new PageId(numFichier,2));
					tabPageLibre.add( new PageId(numFichier,3));
					tabPageLibre.add( new PageId(numFichier,4));
					
					return new PageId(numFichier,1);
				}
				numFichier+=1;
				
				
				
			}
		}


		else {
			return tabPageLibre.remove(0); 
		}
		
				
	}

			
	
	//Remplire la page avec l'argument buff
	public void readPage(PageId pageId, byte[] buff) throws IOException{

		String nomFichier = DBParams.DBpath+"/"+"F"+pageId.getFile()+".bdda"; //Donne le chemin du fichier
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "r"); //Défini file en lecture r
		int debutPage = DBParams.pageSize*pageId.getPage();
		//file.readFully(buff,debutPage,debutPage+DBParams.pageSize-1);
		file.read(buff); //Le fichier lit le tampon en argument
		file.close();
	}
	
	
	//Ecrit le contenu de l'argument buff dans le fichier
	public void writePage(PageId pageId,byte[] buff) throws IOException{
		
		String nomFichier = DBParams.DBpath+"/"+"F"+pageId.getFile()+".bdda";
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "rw");
		file.write(buff);
		file.close();
	}
	
	//Désalloue une page
	public  void deallocPage(PageId pageId) {
		
		tabPageLibre.add(pageId); //Ajoute la page qu'on veut désallouer aux pages libres
	}
	
	//Retourne le nb de pages allouées au disk manager
	public  int getCurrentAllocPages() {
		boolean exist = true;
		int nbPageLibre = 0;
		int numFichier=1;
		File fichier;
		while(exist){
				String nomFichier = "F"+numFichier+".bdda"; //Fourni le numéro du fichier
				fichier = new File("../../DB/"+nomFichier); // Il faut trouver un moyen de ranger le fichier dans le dossier DB
				if (fichier.exists()){ 
					nbPageLibre +=1;
					numFichier+=1;
									}
				else{
					exist = false;
				}
		}
		
		nbPageLibre *= DBParams.maxPagesPerFile; //On multiplie le nb de fichiers fois le nb de pages par fichiers
		nbPageLibre -= tabPageLibre.size(); //On enlève les pages libres existantes
		return nbPageLibre; //Retourne le nb de pages libres
	}
	public static void sauvegardeTabPageLibre() throws IOException{
		File fichierSauvegardePageLibre = new File("../../DB/fichierSauvegardePageLibre.bdda");
		fichierSauvegardePageLibre.createNewFile();
		RandomAccessFile file =  new RandomAccessFile("../../DB/fichierSauvegardePageLibre.bdda", "rw");

		for( int i=0;i<tabPageLibre.size()+1;i++){
			file.write((tabPageLibre.get(i).getFile() + " ").getBytes());
			file.write((tabPageLibre.get(i).getPage() + " ").getBytes());
			System.out.println("file : "+tabPageLibre.get(i).getFile());
			System.out.println("Page : "+tabPageLibre.get(i).getPage());
		}
		file.close();
			
	}
	public static void recupTabPageLibre() throws IOException{
		File fichier = new File("../../DB/fichierSauvegardePageLibre.bdda");
		
		if (fichier.exists()){

			RandomAccessFile file =  new RandomAccessFile("../../DB/fichierSauvegardePageLibre.bdda", "r");
			int pageIdx,fileIdx;
			String strPageIdx , strFileIdx;
			byte[] b = new byte[1000];
			file.read(b);
			String texteFichier = new String(b);
			StringTokenizer texte= new StringTokenizer(texteFichier); 

			while (texte.hasMoreTokens()){
				strFileIdx= texte.nextToken() ;
				System.out.println(strFileIdx);
				strPageIdx = texte.nextToken();

				pageIdx = Integer.parseInt(strPageIdx);
				fileIdx = Integer.parseInt(strFileIdx);

				tabPageLibre.add(new PageId(fileIdx,pageIdx));
			}
			file.close();
		}
		
	
	
	}
}
