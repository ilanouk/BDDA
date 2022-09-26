import java.io.*;
import java.util.ArrayList;
import java.nio.*;
public class DiskManager {
	

	static int numFichier = 0;
	static ArrayList<PageId> tabPageLibre = new ArrayList<PageId>(); //tableau qui stock la liste des pages libres
	
	//Allouer une page
	public static PageId allocPage() {
		
		boolean isAllouee = false
		if (tabPageLibre.size() == 0) { // si le tableau des page Libre est vide alors on crée un nouveau fichier
			while(!isAllouee){
				String nomFichier = "F"+numFichier+".bdda";
				File fichier = new File("../../DB/"+nomFichier); // Il faut trouver un moyen de ranger le fichier dans le dossier DB
				if (!fichier.exist()){
					tabPageLibre.add( new PageId(numFichier,2));
					tabPageLibre.add( new PageId(numFichier,3));
					numFichier +=1;
					
					return new PageId(numFichier,1);
				}
				
				
			}


		else {
			return tabPageLibre.remove(0); 
		}
				
	}

			
	
	//Remplire la page avec l'argument buff
	public static void readPage(PageId pageId, ByteBuffer buff) {
		String nomFichier = DBParams.DBPath+"F"+pageId.getFile()+".bdda";
		file.read(nomFichier*(pageId.getPage()-1)*DBParams.pageSize);
	}
	
	//Ecrit le contenu de l'argument buff dans le fichier
	public static void writePage(PageId pageId, ByteBuffer buff) {
		
	}
	
	//Désalloue une page
	public static void deallocPage(PageId pageId) {
		tabPageLibre.add(pageId);
		
	}
	
	//Retourne le nb de pages allouées au disk manager
	public static int getCurrentAllocPages() {
		
	}
}
