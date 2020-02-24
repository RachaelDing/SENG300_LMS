package Storage;

import Materials.Book;

/**
 * Storage for Materials.Book resource objects. Extends Materials.Material storage. Implementing a Storage.BookStorage object Allows for saving (save())
 * , retrieving(retrieve), deleting and listing books.
 * resources.
 */
public class BookStorage extends MaterialStorage {

    //Location book objects are stored
    private static String storageLocation = "Resources/Books/";

    /**
     * Constructor
     */
    public BookStorage(){
        filePath = storageLocation;
    }


    /**
     * Retrieves a library Book object from the storage at the filePath.
     * @param name - Name of material to retrieve
     * @return - Materials.Material object
     */
    public Book retrieve(String name){
        return (Book)super.retrieve(name);
    }


}
