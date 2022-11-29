import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectCommand {
    private ArrayList<Condition> conditions;
    private RelationInfo rel;

    public SelectCommand(String nomRelation, ArrayList<Condition> conditions) { //Constructeur pour la commande select avec condition
        this.conditions = conditions;
        this.rel = Catalog.getLeCatalog().getRelationInfo(nomRelation);
    }

    public SelectCommand(String nomRelation) { //Constructeur pour le cas ou il n'y a pas de condition
        this.conditions = null;
        this.rel = Catalog.getLeCatalog().getRelationInfo(nomRelation);
    }


    public void execute() throws IOException{
        ArrayList<Record> records = FileManager.getFileManager().GetAllRecords(rel);
        ArrayList<ColInfo> colonnes = rel.getColonnes();
        // for(Record record : records){ //Pour chaque record
        //     if(conditions == null){ //Si il n'y a pas de condition alors on affiche toute les conditions
        //         System.out.println(record);
        //     }
        //     else{
        //         for(Condition condition : conditions){
        //             for(int i=0; i<colonnes.size(); i++){
        //                 if(condition.getNomColonne().equals(colonnes.get(i).getNom())){
        //                     record.ge
        //                 }
        //             }
                    
        //         }
        //     }
        //}
    }
}
