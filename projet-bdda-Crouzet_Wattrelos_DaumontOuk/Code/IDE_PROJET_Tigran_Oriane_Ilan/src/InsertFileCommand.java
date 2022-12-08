import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InsertFileCommand {
    private RelationInfo rel;
    private String fileName;

    public InsertFileCommand(String nomRelation, String fileName){
        this.rel = Catalog.getLeCatalog().getRelationInfo(nomRelation); // On recupere la relation grâce à son nom
        this.fileName = fileName;
    }

    public void execute() throws Exception{

        String filePath = ".";
        File file = new File(filePath + "/" + fileName);
        Reader reader = new FileReader(file) ;
        BufferedReader bf = new BufferedReader(reader); //On lit le fichier

        try{
            ArrayList<String> values = new ArrayList<String>();
            while (true){ //On lit le fichier ligne par ligne jusqu'a la fin
                String line = bf.readLine();
                StringTokenizer st = new StringTokenizer(line, ","); //On recupère les valeurs separé les valeurs par des virgules
    
                while(st.hasMoreTokens()){ //On ajoute les valeurs dans un tableau
                    String token = st.nextToken();
                    values.add(token);
            }
            InsertCommand insert = new InsertCommand(rel, values); //On insert les valeurs dans la base de données
            insert.execute();
            values.clear(); //On vide le tableau pour pouvoir ajouter les valeurs de la ligne suivante
            }
        }
            
        
        catch(NullPointerException n){
            bf.close();
            reader.close();
        }
        


        
        
    }
}
