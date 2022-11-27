import java.io.IOException;
import java.util.ArrayList;
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

    public void processCommand(String commande) throws IOException{


        StringTokenizer st = new StringTokenizer(commande);
        String mot1 = st.nextToken();
        
        if (mot1.equals("CREATE")&& st.nextToken().equals("TABLE")){ // on verifie si la commande est "CREATE TABLE"

            String nomRelation = st.nextToken(); // On recupere le nom de la table dans la chaine de charactere

            ArrayList <ColInfo> listeColonnes = new ArrayList<ColInfo>();

            // La suite a pour but de recuperer toute les informations entre parenthese de la chaine de caractere;
            String chaineEntreParenthese = st.nextToken(); // on recupere tout ce qu'il y a entre parenthese

            chaineEntreParenthese = chaineEntreParenthese.replace("(", "");
            chaineEntreParenthese = chaineEntreParenthese.replace(")", "");  //On retire les parenthese de la chaine pour pouvoir travailler sur celle ci

            StringTokenizer stringInfoCol = new StringTokenizer(chaineEntreParenthese,","); //on separe les infos de chaque colonne càd ce quil y a entre les virgules de la commande
           
            while(stringInfoCol.hasMoreTokens()){ //Cette boucle a pour but  de remplir listeColonne qui contient toute les infos sur les colonnes 
                StringTokenizer typeNomSepare = new StringTokenizer(stringInfoCol.nextToken(),":");
                String nomCol = typeNomSepare.nextToken();
                String typeCol = typeNomSepare.nextToken();

                listeColonnes.add(new ColInfo(nomCol, typeCol));
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
            String stToString = st.toString();
            /*
             * En dessous, on va initialiser une arrayList value qui 
             * va permettre d'initialiser insertCommand. Ce tableau sont les
             * valeurs du Record, celle qui vont être ajouté à la base de données
             * 
             */
            stToString = stToString.replace("(","" );
            stToString = stToString.replace("(","" );

            
            StringTokenizer stValues = new StringTokenizer(stToString,",");
            while (stValues.hasMoreTokens()){
                values.add(stValues.nextToken());
            }
            
            

            InsertCommand insertCommand = new InsertCommand(null, values);
            insertCommand.execute();
            
        }
        else if(mot1.equals("SELECT")){

            
        }
        else{
            System.out.println("Commande Invalide");
        }
        System.out.println("test");
    }
}