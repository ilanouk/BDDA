import java.util.*;
import java.nio.ByteBuffer;

public class Record {
    // Attributs
    private RelationInfo relInfo;
    private ArrayList<String> values;

    // Constructeur
    public Record(RelationInfo relInfo) {
        this.relInfo = relInfo;
        values = new ArrayList<String>();
    }

    // Méthodes
    public void writeToBuffer(ByteBuffer buff, int pos) {
        //Selon le modèle offset directory
        //On rentre les positions des n colonnes soit n+1 cases à remplir au début
        //Puis on ajoue les valeurs à chacune à leur position relative
        buff.position(pos);
        int x = (values.size()+1)*4; //n+1 cases * 4 octets
        int savePos = pos; //On créée une variable de sauvegarde pour revenir à la position initiale après chaque ajout d'une valeur dans le buffer
        int nextAdresse = pos + x; //Première position de la première valeur
        for (int i = 0; i < values.size(); i++) {
            buff.putInt(nextAdresse); //On ajoue au buffer l'adresse de la prochaine valeur
            buff.position(nextAdresse); //On se déplace à l'adresse de la prochaine valeur
            String type = relInfo.getTypeColonne(i).toLowerCase();
            switch(type){ //On définit trois façons de remplir le buffer selon le type de la colonne
                case "int" : 
                    int resInt = Integer.valueOf(values.get(i));
                    buff.putInt(resInt); //On ajoute la prochaine valeur dans le buffer (à son adresse selon sa position relative)
                    savePos += 4; //Nouvelle position du buffer (+ 4 octets à chaque fois)
                    buff.position(savePos); //On remet le buffer à la prochaine position
                    nextAdresse += 4; //1 Integer = 4 octets
                    break;
                case "float":
                    float resFloat = Float.valueOf(values.get(i));
                    buff.putFloat(resFloat);
                    savePos += 4;
                    buff.position(savePos);
                    nextAdresse += 4; //1 Float = 4 octets
                    break;
                default : 
                    if(type.startsWith("string")){
                        int len = Integer.parseInt(type.substring(6));
                        for (int j = 0; j<len && j<values.get(i).length(); i++){
                            buff.putChar(values.get(i).charAt(j));
                        }
                    }
                    savePos += 4;
                    buff.position(savePos);
                    nextAdresse = ; //1 String =   octets
                    break;
            }

        }
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        this.values.clear();
        buff.position(pos);

    }

    public int getWrittenSize() {

    }
}
