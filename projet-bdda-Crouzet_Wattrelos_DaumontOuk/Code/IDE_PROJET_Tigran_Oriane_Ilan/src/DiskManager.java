import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class DiskManager {
	
	private static DiskManager LeDiskManager = new DiskManager();
	private static byte[] buff;
	private static ArrayList<PageId> tabPageLibre = new ArrayList<PageId>(); //tableau qui stock la liste des pages libres
	PageId page;

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
					for (int i =0;i<DBParams.maxPagesPerFile;i++){
						tabPageLibre.add( new PageId(numFichier,i));
					}
					page = tabPageLibre.remove(0);
					return page;
				}
				
				numFichier+=1;
			}
			

		}

		else {
			page = tabPageLibre.remove(0);
			return  page;
			
		}
	}


	public String afficherTab(){
		String tab="";
		for (int i=0; i<tabPageLibre.size();i++){
			tab += (tabPageLibre.get(i).getFile()+" "+ tabPageLibre.get(i).getPage()+"\n");

		}
		return tab;
	}
		
				

			
	
	//Remplire la page avec l'argument buff
	public void readPage(PageId pageId, byte[] buff) throws IOException{
		
		String nomFichier = DBParams.DBpath+"/"+"F"+pageId.getFile()+".bdda"; //Donne le chemin du fichier
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "r"); //Défini file en lecture r
		int debutPage = DBParams.pageSize*pageId.getPage();
		//file.read(buff,debutPage,DBParams.pageSize-1);
		file.seek(debutPage);
		file.read(buff); //Le fichier lit le tampon en argument
		file.close();
	}
	
	
	//Ecrit le contenu de l'argument buff dans le fichier
	public void writePage(PageId pageId,byte[] buff) throws IOException{
		
		String nomFichier = DBParams.DBpath+"/"+"F"+pageId.getFile()+".bdda";
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "rw");

		file.seek(DBParams.pageSize*pageId.getPage());
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
		nbPageLibre -= tabPageLibre.size() ; //On enlève les pages libres existantes
		return nbPageLibre; //Retourne le nb de pages libres
	}


	// Sauvegarde le tableau de page libre
	public static void sauvegardeTabPageLibre() throws IOException{
		
		File fichierSauvegardePageLibre = new File("../../DB/fichierSauvegardePageLibre.bdda");  // Chemin du fichier à créer

		if (fichierSauvegardePageLibre.exists()){ //On supprime le fichier pour le mettre a jour en le créant par la suite
			fichierSauvegardePageLibre.delete();
		}

		fichierSauvegardePageLibre.createNewFile();
		RandomAccessFile file =  new RandomAccessFile("../../DB/fichierSauvegardePageLibre.bdda", "rw");

		for( int i=0;i<tabPageLibre.size();i++){	// On écrit les num de fichier et les num de page de sorte ou l'élement i = num fichier et i+1 = num page où i+=2
			file.write((tabPageLibre.get(i).getFile() + " ").getBytes());
			file.write(((tabPageLibre.get(i).getPage()+" ").getBytes()));
		}
		file.close();
			
	}


	// Recupere le Tableau de Page libre sauvegarder precedemment
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

			if (!texteFichier.equals(" ")){
	
				while (texte.hasMoreTokens()&& texte.countTokens() !=1){
					strFileIdx= texte.nextToken(" ") ;
					strPageIdx = texte.nextToken();
					
					pageIdx = Integer.parseInt(strPageIdx.trim());
					fileIdx = Integer.parseInt(strFileIdx.trim());

					tabPageLibre.add(new PageId(fileIdx,pageIdx));
					
				}
				file.close();

			}
		}
		
	}
}
