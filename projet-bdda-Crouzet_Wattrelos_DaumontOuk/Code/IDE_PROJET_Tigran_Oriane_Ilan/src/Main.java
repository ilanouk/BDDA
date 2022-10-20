import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println("coucou");
		DBParams.DBpath = " ../../DB";
		DBParams.maxPagesPerFile = 4;
		DBParams.pageSize = 4096;
		DBParams.frameCount = 2;

		DBManager.getleDBManager().Init(); //Initialisation du DBManager
		
		Scanner sc = new Scanner(System.in);
		StringTokenizer strtoken;

		while (true){// Boucle de traitement des commandes

			System.out.println("Entrez une commande"); 
			String commande = sc.next();
			
			if (commande.equals("EXIT")){ // Si la commande est exit alors on sort de la boucle de traitement de commande
				break;
			}

			strtoken = new StringTokenizer(commande);
			if (strtoken.nextToken().equals("Create")&&strtoken.nextToken().equals("Table"));



		}
		DBManager.getleDBManager().Finish();
		sc.close();
	}
}
