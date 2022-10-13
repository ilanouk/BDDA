import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;;

public class Catalog {
    private Catalog leCatalog = new Catalog();
    private ArrayList<RelationInfo> listeRelation = new ArrayList<RelationInfo>();

    public Catalog getLeCatalog() {
        return leCatalog;
    }

    public void Init() throws ClassNotFoundException, IOException {
        load();
    }

    public void Finish() throws IOException {
        save();
    }

    public void addRelationInfo(RelationInfo relation) {
        listeRelation.add(relation);
    }

    public RelationInfo getRelationInfo(String nom) {
        for (int i = 0; i < listeRelation.size(); i++) {
            if (listeRelation.get(i).getNom().equals(nom)) {
                return listeRelation.get(i);
            }

        }
        return null;
    }

    public void save() throws IOException { //Sauvegarde le Catalogue dans le fichier Catalog.sv 
        File f = new File(DBParams.DBpath + "/Catalog.sv");
        FileOutputStream out = new FileOutputStream(f);
        ObjectOutputStream o = new ObjectOutputStream(out);
        o.writeObject(leCatalog);
        o.close();
        out.close();
    }

    public void load() throws IOException, ClassNotFoundException { // charge le Catalogue sauvegarder dans le fichier Catalog.sv
                                                                   
        File f = new File(DBParams.DBpath + "/Catalog.sv");
        if (f.exists()){
             FileInputStream out = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(out);
            leCatalog = (Catalog) o.readObject();
            o.close();
            out.close();
        }
       
    }
}