import java.io.*;

public class DropDbCommande {
    
    public void execute(){
        int i=0;
        File f= new File(DBParams.DBpath + "/F0.bdda");
        while (f.exists()){
            i+=1;
            f.delete();
            f= new File(DBParams.DBpath+ "/F" + i + ".bdda");

        }
        File fichierSauvegarde = new File(DBParams.DBpath +"/FichierSauvegardePageLibre.bdda");
        File catalogsv = new File(DBParams.DBpath +"/Catalog.sv"); // Doit on supprimer ca
        fichierSauvegarde.delete();
        catalogsv.delete();
    }
}