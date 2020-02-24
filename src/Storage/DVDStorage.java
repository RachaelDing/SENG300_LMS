package Storage;

import Materials.DVD;

/**
 * Storage for Materials.Book resource objects. Extends Materials.Material storage. Implementing a Storage.BookStorage object Allows for saving (save())
 * , retrieving(retrieve), deleting and listing books.
 * resources.
 */
public class DVDStorage extends MaterialStorage {

    //Location book objects are stored
    private static String storageLocation = "Resources/DVDs/";

    /**
     * Constructor
     */
    public DVDStorage(){
        super();
        filePath = storageLocation;
    }


    /**
     * Retrieves a library Book object from the storage at the filePath.
     * @param name - Name of material to retrieve
     * @return - Materials.Material object
     */
    public DVD retrieve(String name){
        return (DVD) super.retrieve(name);
    }


}
