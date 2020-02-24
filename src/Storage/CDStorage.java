package Storage;

import Materials.CD;
import Materials.Material;

/**
 * Storage for Materials.Book resource objects. Extends Materials.Material storage. Implementing a Storage.BookStorage object Allows for saving (save())
 * , retrieving(retrieve), deleting and listing books.
 * resources.
 */
public class CDStorage extends MaterialStorage {

    //Location book objects are stored
    private static String storageLocation = "Resources/CDs/";

    /**
     * Constructor
     */
    public CDStorage(){
        super();
        filePath = storageLocation;
    }


    /**
     * Retrieves a library Book object from the storage at the filePath.
     * @param name - Name of material to retrieve
     * @return - Materials.Material object
     */
    public CD retrieve(String name){
        return (CD)super.retrieve(name);
    }


}
