import java.io.IOException;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class DBManager {

    private static DBManager leDBManager;

    public static DBManager getleDBManager() {
        if (leDBManager == null) {
            leDBManager = new DBManager();
        }
        return leDBManager;
    }

    public void Init() throws ClassNotFoundException, IOException { //Initialisation
        BufferManager.getLeBufferManager().initBufferPool();
        Catalog.getLeCatalog().Init();
        

    }

    public void Finish() throws IOException { 
        BufferManager.getLeBufferManager().flushAll();
        Catalog.getLeCatalog().Finish();

    }

    public void processCommand(String commande){
        switch (commande){

            case "CREATE TABLE" : 
              Xcommande  = new CreateTableCommand();
              Xcommande.execute();




        }
    }
}