import java.io.*;
import java.util.ArrayList;
public class DiskManager {
	
	static int numFichier=1;
	static byte[] buff;
	static ArrayList<PageId> tabPageLibre = new ArrayList<PageId>(); //tableau qui stock la liste des pages libres
	
	//Allouer une page
	public static PageId allocPage() throws IOException {
		
		boolean isAllouee = false;
		if (tabPageLibre.size() == 0) { // si le tableau des page Libre est vide alors on crée un nouveau fichier
			while(!isAllouee){
				String nomFichier = "F"+numFichier+".bdda";
				File fichier = new File("../../../DB/"+nomFichier); 
				if (!fichier.exists()){ //On crée 4 pages, et on alloue la 1ère, les autres sont indiquées comme page libres
					fichier.createNewFile();

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
		return null;
				
	}

			
	
	//Remplire la page avec l'argument buff
	public static void readPage(PageId pageId, byte[] buff) throws IOException{

		String nomFichier = DBParams.DBpath+"F"+pageId.getFile()+".bdda"; //Donne le chemin du fichier
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "r"); //Défini file en lecture r
		file.read(buff); //Le fichier lit le tampon en argument
		file.close();
	}
	
	
	//Ecrit le contenu de l'argument buff dans le fichier
	public static void writePage(PageId pageId,byte[] buff) throws IOException{
		
		String nomFichier = DBParams.DBpath+"F"+pageId.getFile()+".bdda";
		RandomAccessFile file =  new RandomAccessFile(nomFichier, "w");
		file.write(buff);
		file.close();
	}
	
	//Désalloue une page
	public static void deallocPage(PageId pageId) {
		
		tabPageLibre.add(pageId); //Ajoute la page qu'on veut désallouer aux pages libres
	}
	
	//Retourne le nb de pages allouées au disk manager
	public static int getCurrentAllocPages() {
		boolean exist = true;
		int nbPageLibre = 0;
		while(exist){
				String nomFichier = "F"+numFichier+".bdda"; //Fourni le numéro du fichier
				File fichier = new File("../../DB/"+nomFichier); // Il faut trouver un moyen de ranger le fichier dans le dossier DB
				if (fichier.exists()){ 
					nbPageLibre +=1;
				}
				else{
					exist = false;
				}
		}
		nbPageLibre *= DBParams.maxPagesPerFile; //On multiplie le nb de fichiers fois le nb de pages par fichiers
		nbPageLibre -= tabPageLibre.size(); //On enlève les pages libres existantes
		return nbPageLibre; //Retourne le nb de pages libres
	}
}
