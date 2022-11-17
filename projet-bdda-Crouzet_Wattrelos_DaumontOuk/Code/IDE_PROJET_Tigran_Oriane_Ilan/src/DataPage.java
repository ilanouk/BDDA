import java.nio.ByteBuffer;

public class DataPage {
    // Attributs
    ByteBuffer nBuffer;
    int nbEntrees;
    int nextPosition;

    // Constructeur
    public DataPage() {
        nbEntrees = 0;
        nextPosition = 0;
    }

    // Méthodes
    public void addRecord(Record rec) { // Ajout d'un nouveau record dans la DataPage

    }

    public Record getRecord(int pos) { // On va chercher le record à la position pos

    }

    public Record getRecord(Record rec) { // On va chercher le record rec

    }

    public void removeRecord(Record rec) { // On supprime le record rec

    }

    public int getNbEntrees() {
        return nbEntrees;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public void setNbEntrees(int nbEntrees) {
        this.nbEntrees = nbEntrees;
    }

    public void setNextPosition(int nextPosition) {
        this.nextPosition = nextPosition;
    }
}
