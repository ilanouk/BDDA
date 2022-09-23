import java.io.*;
import java.nio.*;
public class DiskManager {
	
	//Allouer une page
	public static PageId AllocPage() {
		
	}
		int i;
		boolean isAllouee = false;
		while (isAllouee) {
			String nomFichier = "F"+i+".bdda";
			File fichier = new File(nomFichier); // on verifie que le fichier existe
			if (fichier.exists()) {
				//verifier  si toute les page sont alloué
				//si oui on incremente i
				//si non on alloue la page et on met isAllouee à Vrai
			}
			else {
				//Création d'un nouveau fichier et d'un nouveau PageIDD
				//DataOutputStream out = new DataOutputStream( new BufferedOutputStream( new FileOutputStream(nomFichier)));
				
				//ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);
				//buff.put("XXX".getBytes());
				//isAllouee = true;
				//allouer la premiere page et mettre isAllouee à Vrai
				
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
		
	}
	
	//Retourne le nb de pages allouées au disk manager
	public static int GetCurrentAllocPages() {
		
	}
}
