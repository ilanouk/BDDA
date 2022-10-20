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
        // Selon le modèle offset directory
        // On rentre les positions des n colonnes soit n+1 cases à remplir au début
        // Puis on ajoue les valeurs à chacune à leur position relative
        buff.position(pos);
        int x = (values.size() + 1) * 4; // n+1 cases * 4 octets
        int nextAdresse = pos + x; // Première position de la première valeur
        for (int i = 0; i < values.size(); i++) {
            buff.putInt(nextAdresse); // On ajoute au buffer l'adresse de la prochaine valeur
            buff.position(nextAdresse); // On se déplace à l'adresse de la prochaine valeur
            String type = relInfo.getTypeColonne(i).toLowerCase();
            switch (type) { // On définit trois façons de remplir le buffer selon le type de la colonne
                case "Integer":
                    int resInt = Integer.valueOf(values.get(i));
                    buff.putInt(resInt); // On ajoute la prochaine valeur dans le buffer (à son adresse selon sa
                                         // position relative)
                    nextAdresse += 4; // 1 Integer = 4 octets
                    break;
                case "Float":
                    float resFloat = Float.valueOf(values.get(i));
                    buff.putFloat(resFloat);
                    nextAdresse += 4; // 1 Float = 4 octets
                    break;
                default:
                    if (type.startsWith("String")) {
                        int len = Integer.parseInt(type.substring(0));
                        for (int j = 0; j < len && j < values.get(i).length(); i++) {
                            buff.putChar(values.get(i).charAt(j));
                        }
                    }
                    nextAdresse = values.get(i).length() * 2; // 1 String = octets
                    break;
            }

        }
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        // On va lire les valeurs dans le buffer à partir de la première position
        // relative entrée dans buffer.getInt(0)
        // On suppose que le Record a été écrit avec writeBuffer()
        String resString = "";
        this.values.clear();
        buff.position(pos);
        int nbrValeur = (buff.getInt(pos) - pos) / 4; // Représente le nombre de cases allouées au début du buffer pour
                                                      // stocker toutes les positions relatives
        buff.position(buff.getInt(pos)); // On place le pointeur à l'adresse relative de la première valeur
        for (int i = pos; i < nbrValeur; i++) {
            String type = relInfo.getTypeColonne(i).toLowerCase();
            switch (type) {
                case "Integer":
                    int resInt = buff.getInt();
                    this.values.add(String.valueOf(resInt));
                case "Float":
                    float resFLoat = buff.getFloat();
                    this.values.add(String.valueOf(resFLoat));
                default:
                    if (type.startsWith("String")) {
                        int len = Integer.parseInt(type.substring(0));
                        int j = 0;
                        char[] chaine = new char[len];
                        for (int k = pos; k < pos + len; k++) {
                            chaine[j] = buff.getChar();
                            j++;
                        }
                        resString = new String(chaine);
                        this.values.add(resString);
                    }
                    break;
            }
        }
    }

    public int getWrittenSize() {
        int res = 0;
        for (int i = 0; i < values.size(); i++) {
            String type = relInfo.getTypeColonne(i).toLowerCase();
            switch (type) {
                case "Integer":
                case "Float":
                    res += 8; // Taille d'un int ou d'un float + taille de leur adresse
                default:
                    res += values.get(i).length() * 2; // Taille d'un string
                    res += 4; // Taille de son adresse
            }
        }
        return res;
    }
}
