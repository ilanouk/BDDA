import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;


public class DBManager {

    private static DBManager leDBManager;

    public static DBManager getleDBManager() {
        if (leDBManager == null) {
            leDBManager = new DBManager();
        }
        return leDBManager;
    }

    public void Init() throws ClassNotFoundException, IOException { //Initialisation
        BufferManager.getLeBufferManager().initBufferPool();
        Catalog.getLeCatalog().Init();
        

    }

    public void Finish() throws IOException { 
        BufferManager.getLeBufferManager().flushAll();
        Catalog.getLeCatalog().Finish();

    }

    public void processCommand(String commande) throws Exception{

    	try {
    		
    	
	        StringTokenizer st = new StringTokenizer(commande);
	        
	        String mot1 = st.nextToken();
	        
	        if (mot1.equals("CREATE")&& st.nextToken().equals("TABLE")){ // on verifie si la commande est "CREATE TABLE"
	
	            String nomRelation = st.nextToken(); // On recupere le nom de la table dans la chaine de charactere
	
	            ArrayList <ColInfo> listeColonnes = new ArrayList<ColInfo>();
	            
	            // La suite a pour but de recuperer toute les informations entre parenthese de la chaine de caractere;
	            String chaineEntreParenthese = st.nextToken(); // on recupere tout ce qu'il y a entre parenthese
	            chaineEntreParenthese = chaineEntreParenthese.substring(1,chaineEntreParenthese.length()-1);  //On retire les parenthese de la chaine pour pouvoir travailler sur celle ci
	            StringTokenizer stringInfoCol = new StringTokenizer(chaineEntreParenthese,","); //on separe les infos de chaque colonne càd ce quil y a entre les virgules de la commande
	           
	            while(stringInfoCol.hasMoreTokens()){ //Cette boucle a pour but  de remplir listeColonne qui contient toute les infos sur les colonnes 
	                StringTokenizer typeNomSepare = new StringTokenizer(stringInfoCol.nextToken(),":");
	                String nomCol = typeNomSepare.nextToken();
	                String typeCol = typeNomSepare.nextToken();

					if (typeCol.equals("INTEGER") || typeCol.equals("REAL") || typeCol.contains("VARCHAR")){
	
	                	listeColonnes.add(new ColInfo(nomCol, typeCol));
	            	} 
				
					else{
						System.out.println("Erreur : le type de la colonne n'est pas reconnu");
						break;
					}
					
				}
				int nbrColonne = listeColonnes.size();

				CreateTableCommand createTable = new CreateTableCommand(nomRelation,nbrColonne,listeColonnes);
				createTable.execute();
	
	
	
	            
	        }
	
	
	
	
	        else if(mot1.equals("DROPDB")){
	            DropDbCommande drop = new DropDbCommande();
	            drop.execute();
				
	
	        }
	
	        
	
	        else if(mot1.equals("INSERT") && st.nextToken().equals("INTO")){
	            ArrayList<String> values = new ArrayList<>();
	            String nomRelInfo = st.nextToken();
	            String stToString = st.nextToken();
	            /*
	             * En dessous, on va initialiser une arrayList value qui 
	             * va permettre d'initialiser insertCommand. Ce tableau sont les
	             * valeurs du Record, celle qui vont être ajouté à la base de données
	             * 
	             */

				if(stToString.contains("FILECONTENTS")){// Insertion via fichier
					String nomFichier;
					String strFichier = st.nextToken();
					StringTokenizer stFichier = new StringTokenizer(strFichier,"(");
					stFichier.nextToken();//On supprime 'FILECONTENTS'
					nomFichier = stFichier.nextToken();

					nomFichier = nomFichier.replace("(","" ); // on enleve le (
					InsertFileCommand insertF = new InsertFileCommand(nomRelInfo, nomFichier);
					insertF.execute();
					
				
				}	
				else{ // Cas où insertion via ligne de commande
					stToString = st.nextToken(); // On retire values
					stToString = stToString.replace("(","" );
					stToString = stToString.replace(")","" );
		
					
					StringTokenizer stValues = new StringTokenizer(stToString,",");
					while (stValues.hasMoreTokens()){
						values.add(stValues.nextToken());
					}
					
					RelationInfo relInfo = Catalog.getLeCatalog().getRelationInfo(nomRelInfo);
		
					InsertCommand insertCommand = new InsertCommand(relInfo, values);
					insertCommand.execute();
				}
	            
	            
	        }
	        else if(mot1.equals("SELECT")){
				ArrayList<Condition> conditions = new ArrayList<Condition>();
				SelectCommand selectCommand = null;
				st.nextToken();//on saute le *
				st.nextToken();//on saute le FROM
				String nomRelInfo = st.nextToken();//on recupere le nom de la table
				if (!st.hasMoreTokens()){
					selectCommand = new SelectCommand(nomRelInfo);
					
				}
				else{
					st.nextToken();//on saute le WHERE
					while(st.hasMoreTokens()){
						String valeur;
						String condi = st.nextToken();
						StringTokenizer stCondi = new StringTokenizer(condi, "=<>"); // on separe la condition en 3 parties
						String nomColonne = stCondi.nextToken();
						String operateur = stCondi.nextToken(); // Valable que pour les operateurs uniques
						String opeOuVal=stCondi.nextToken();
						if(opeOuVal.equals("=")){
							operateur += opeOuVal;
							valeur = st.nextToken();
						}
						else{
							valeur = opeOuVal;
						}
						
						Condition condition = new Condition(nomColonne, operateur, valeur);
						conditions.add(condition);
					}
					selectCommand = new SelectCommand(nomRelInfo, conditions);
					
				}
				selectCommand.execute();
				
				
	        }
	        else{
	            System.out.println("Commande Invalide");
	        }
    	}
    	catch(NoSuchElementException e) {
    		System.out.println("La commande n'est pas bien rédiger");
    	}
        
    }
    
}