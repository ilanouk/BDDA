import java.io.*;
import java.util.ArrayList;
import java.nio.*;
public class DiskManager {
	

	static int numFichier = 0;
	static ArrayList<PageId> tabPageLibre = new ArrayList<PageId>(); //tableau qui stock la liste des pages libres
	
	//Allouer une page
	public static PageId AllocPage() {
		
		
		boolean isAllouee = false;
		
		
		if (tabPageLibre.size() == 0) { // si le tableau des page Libre est vide alors on crée un nouveau fichier
			String nomFichier = "F"+numFichier+".bdda";
			//File fichier = new File(nomFichier); // Il faut trouver un moyen de ranger le fichier dans le dossier DB
			
			tabPageLibre.add( new PageId(numFichier,2));
			tabPageLibre.add( new PageId(numFichier,3));
			numFichier +=1;
			
			return new PageId(numFichier,1);
			
		}
		else {
			return tabPageLibre.remove(0); 
		}
			
	}

		
		
		
		
		
		
		
		
		
		
		
	//Remplire la page avec l'argument buff
	public static void ReadPage(PageId pageId, buff) {
		
	}
	
	//Ecrit le contenu de l'argument buff dans le fichier
	public static void WritePage(PageId pageId, buff) {
		
	}
	
	//Désalloue une page
	public static void DeallocPage(PageId pageId) {
		tabPageLibre.add(pageId);
		
	}
	
	//Retourne le nb de pages allouées au disk manager
	public static int GetCurrentAllocPages() {
		
	}
}
