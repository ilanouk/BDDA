import java.io.IOException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println("Bienvenue");
		DBParams.DBpath = "./DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		DBParams.frameCount = 2;

		//DBManager.getleDBManager().Init(); //Initialisation du DBManager
		
		Scanner sc = new Scanner(System.in);


		while (true){// Boucle de traitement des commandes

			System.out.println("Entrez une commande"); 
			String commande = sc.nextLine();
			
			if (commande.equals("EXIT")){ // Si la commande est exit alors on sort de la boucle de traitement de commande
				break;
			}

			else{
				DBManager.getleDBManager().processCommand(commande);
			}


		}
		DBManager.getleDBManager().Finish();
		sc.close();
	}
}
