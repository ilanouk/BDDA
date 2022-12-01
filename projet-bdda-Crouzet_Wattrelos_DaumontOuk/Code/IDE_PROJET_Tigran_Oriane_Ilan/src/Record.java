import java.util.*;
import java.nio.ByteBuffer;

public class Record {
    // Attributs
    private RelationInfo relInfo;
    private ArrayList<String> values;

    // Constructeur
    public Record(RelationInfo relInfo) {
        this.relInfo = relInfo;
        this.values = new ArrayList<String>();
    }

    public Record(RelationInfo relInfo, ArrayList<String> values) {
        this.relInfo = relInfo;
        this.values = values;
    }

    // Méthodes

    // Getters
    public RelationInfo getRelInfo() {
        return relInfo;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void writeToBuffer(ByteBuffer buff, int pos) {
        // Selon le modèle offset directory
        // On rentre les positions des n colonnes soit n+1 cases à remplir au début
        // Puis on ajoute les valeurs à chacune à leur position relative
        
    	int x = (values.size() + 1) * 4; // n+1 cases * 4 octets
        int posHeaderBuff = pos;
        int adresseProchaineValeur = pos + x; // Première position de la première valeur

        for (int i = 0; i < values.size(); i++) {
        	buff.position(posHeaderBuff);
            buff.putInt(adresseProchaineValeur); // On ajoute au buffer l'adresse de la prochaine valeur
            buff.position(adresseProchaineValeur); // On se déplace à l'adresse de la prochaine valeur
            
            String type = relInfo.getTypeColonne(i).toUpperCase();
            
            switch (type) { // On définit trois façons de remplir le buffer selon le type de la colonne
                case "INTEGER":
                    int resInt = Integer.valueOf(values.get(i));
                    buff.putInt(resInt); // On ajoute la prochaine valeur dans le buffer (à son adresse selon sa
                                         // position relative)
                    adresseProchaineValeur += 4; // 1 Integer = 4 octets
                    posHeaderBuff += 4;
                    break;
                
                case "REAL":
                    float resFloat = Float.valueOf(values.get(i));
                    buff.putFloat(resFloat);
                    adresseProchaineValeur += 4; // 1 Float = 4 octets
                    posHeaderBuff += 4;
                    break;
                
                default:
                    int spaceMemory = Integer.valueOf(type.substring(8, type.length() - 1));
                    for (int j = 0; j < spaceMemory; j++) {
                        if (j >= values.get(i).length()) {
                            buff.putChar(' ');
                        } else {
                            buff.putChar(values.get(i).charAt(j));
                        }
                    }
                    adresseProchaineValeur += spaceMemory * 2;
                    posHeaderBuff += 4;
                    break;
            }
        }
        buff.position(posHeaderBuff);
        buff.putInt(adresseProchaineValeur);
        buff.position(pos);
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        // On va lire les valeurs dans le buffer à partir de la première position
        // relative entrée dans buffer.getInt(0)
        // On suppose que le Record a été écrit avec writeBuffer()
        String resString = "";
        this.values.clear();
        buff.position(pos);
        int nbrValeur = ((buff.getInt(pos) - pos) / 4) - 1; // Représente le nombre de cases allouées au début du buffer pour
                                                      // stocker toutes les positions relatives
        buff.position(buff.getInt(pos)); // On place le pointeur à l'adresse relative de la première valeur
        System.out.println("nb val " + nbrValeur);
        for (int i = 0; i < nbrValeur; i++) {
            String type = relInfo.getTypeColonne(i).toUpperCase();
            switch (type) {
                case "INTEGER":
                    int resInt = buff.getInt();
                    this.values.add(String.valueOf(resInt));
                    break;
                case "REAL":
                    float resFloat = buff.getFloat();
                    this.values.add(String.valueOf(resFloat));
                    break;
                default:
                    int len = Integer.parseInt(type.substring(8, type.length() - 1));
                    char[] chaine = new char[len];
                    for (int j = 0; j < len; j++) {
                        chaine[j] = buff.getChar();
                    }
                    resString = new String(chaine);
                    this.values.add(resString);
                    break;
            }
        }
    }

    public int getWrittenSize() {
        int res = 0;
        for (int i = 0; i < values.size(); i++) {
            String type = relInfo.getTypeColonne(i).toUpperCase();
            switch (type) {
                case "INTEGER":
                case "REAL":
                    res += 8; // Taille d'un int ou d'un float + taille de leur adresse
                    break;
                default:
                    res += Integer.parseInt(type.substring(8, type.length() - 1)) * 2; // Taille d'un string
                    res += 4; // Taille de son adresse
            }
        }
        return res+4;
    }

    public void addValue(String valeur) {
        values.add(valeur);
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < values.size(); i++) {
            res += values.get(i) + " ; ";
        }
        res += ".";
        return res;
    }
}
