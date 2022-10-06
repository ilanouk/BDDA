import java.util.ArrayList;;

public class Catalog{
    private Catalog leCatalog = new Catalog();
    private ArrayList<RelationInfo> listeRelation = new ArrayList<RelationInfo>();
    private int compteur = 0;




    public Catalog getLeCatalog(){
        return leCatalog;
    }

    public void Init(){

    }

    public void Finish(){

    }
    public void addRelationInfo(RelationInfo relation){
        listeRelation.add(relation);
        compteur +=1;
    }


    public RelationInfo getRelationInfo(String nom){
        for (int i =0;i<listeRelation.size();i++){
            if(listeRelation.get(i).getNom().equals(nom)){
                return listeRelation.get(i);
            }
           
        }
        return null;
    }
}