import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RecordTest {
    public static void main(String[] args) throws IOException {
        ArrayList<ColInfo> liste = new ArrayList<ColInfo>();
        liste.add(new ColInfo("Nom", "varchar(10)"));
        liste.add(new ColInfo("Prenom", "varchar(10)"));
        liste.add(new ColInfo("Age", "integer"));
        liste.add(new ColInfo("Note", "real"));
        PageId id = new PageId(0, 0);
        RelationInfo relI = new RelationInfo("etudiant", liste, id);
        Record rec = new Record(relI);

        rec.addValue("Crouzet");
        rec.addValue("Oriane");
        rec.addValue("21");
        rec.addValue("15.1");

        System.out.println(rec.getWrittenSize());
        ByteBuffer buff = ByteBuffer.allocate(100);
        rec.writeToBuffer(buff, 0);
        // buff.position(0);
        for (int j = 0; j < 5; j++) {
            System.out.println("buff " + j + " " + buff.getInt());
        }
    }
}