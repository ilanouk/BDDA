import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;;

public class Catalog implements Serializable{  // Classe qui contient toute les inforamtions concernant les relations
    private static Catalog leCatalog = new Catalog();
    private ArrayList<RelationInfo> listeRelation = new ArrayList<RelationInfo>();

    public static Catalog getLeCatalog() {
        return leCatalog;
    }

    public void Init() throws ClassNotFoundException, IOException { //Initialisation du catalog
        load();
    }

    public void Finish() throws IOException { // méthode utilisé à la fin de l'utilisation du Catalog
        save();
    }

    public void addRelationInfo(RelationInfo relation) { //ajoute une relation 
        listeRelation.add(relation);
    }

    public RelationInfo getRelationInfo(String nom) { //retourne un RelationInfo correspondant au nom donné
        for (int i = 0; i < listeRelation.size(); i++) {
            if (listeRelation.get(i).getNom().equals(nom)) {
                return listeRelation.get(i);
            }

        }
        return null;
    }

    public boolean relationExists(String nom) { //retourne true si la relation existe
        for (int i = 0; i < listeRelation.size(); i++) {
            if (listeRelation.get(i).getNom().equals(nom)) {
                return true;
            }

        }
        return false;
    }
    //Sauvegarde le Catalog
    public void save() throws IOException { //Sauvegarde le Catalogue dans le fichier Catalog.sv 
        File f = new File(DBParams.DBpath + "/Catalog.sv");
        f.delete();//supprime le fichier s'il existe pour pouvoir le mettre àjour
        f.createNewFile();
        FileOutputStream out = new FileOutputStream(f);
        ObjectOutputStream o = new ObjectOutputStream(out);
        o.writeObject(this); // Ecrit leCatalogue dans le fichier

        //Fermeture de tout les flux
        o.flush();
        o.close();
        out.close();
    }

    //Charge le Catalog
    public void load() throws IOException, ClassNotFoundException { // charge le Catalogue sauvegarder dans le fichier Catalog.sv
                                                                   
        File f = new File(DBParams.DBpath + "/Catalog.sv");
        System.out.println(f.exists());
        if (f.exists() && f.length()!=0){
            FileInputStream out = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(out);
            leCatalog = (Catalog) o.readObject(); //lit le fichier dans lequel est sauvegarder leCatalog
            //Fermeture de tout les flux
            o.close();
            out.close();
        }
       
    }
}