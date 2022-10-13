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
        buff.position(pos);
        for (int i = 0; i < values.size(); i++) {
            String type = relInfo.getTypeColonne(i).toLowerCase();
            switch(type){ //On définit trois façons de remplir le buffer selon le type de la colonne
                case "int" : 
                    int resInt = Integer.valueOf(values.get(i));
                    buff.putInt(resInt);
                    break;
                case "float" :
                    float resFloat = Float.valueOf(values.get(i));
                    buff.putFloat(resFloat);
                    break;
                default : 
                    if(type.startsWith("string")){
                        int len = Integer.parseInt(type.substring(6));
                        for (int j = 0; j<len && j<values.get(i).length()){
                            buff.putChar(values.get(i).charAt(j));
                        }
                    }
                    break;
            }

        }
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {

    }
}
