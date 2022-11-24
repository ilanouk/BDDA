import java.io.*;

public class DropDbCommande {
    
    public void execute(){
        int i=0;
        File f= new File(DBParams.DBpath + "/F0.bdda");
        System.out.println(f.getAbsolutePath());
        while (f.exists()){
            
            i+=1;
            f.delete();
            f= new File(DBParams.DBpath+ "/F" + i + ".bdda");

        }
        File fichierSauvegarde = new File(DBParams.DBpath +"/fichierSauvegardePageLibre.bdda");
        File catalogsv = new File(DBParams.DBpath +"/Catalog.sv"); // Doit on supprimer ca
        fichierSauvegarde.delete();
        catalogsv.delete();
    }
}
