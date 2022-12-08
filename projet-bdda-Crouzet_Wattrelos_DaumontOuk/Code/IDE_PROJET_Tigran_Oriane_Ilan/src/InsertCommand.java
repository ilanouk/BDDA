import java.io.IOException;
import java.util.ArrayList;

public class InsertCommand {
    private RelationInfo rel;
    private ArrayList<String> values;

    public InsertCommand( RelationInfo rel, ArrayList<String> values){
        this.rel = rel;
        this.values = values;
    }

    public void execute() throws Exception{
        Record rec = new Record(rel,values);
        FileManager.getFileManager().InsertRecordIntoRelation(rec);
    
    }


}   