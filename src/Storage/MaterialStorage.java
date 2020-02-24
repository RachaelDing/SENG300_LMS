package Storage;

import Materials.Book;
import Materials.Material;

import java.io.*;

/**
 * Abstract Class for the storing and retrieving of library materails. Children of this class allow for saving materials,
 * retrieving materials and deleting materials.
 */
public abstract class MaterialStorage extends Storage{

    //Default file path for accessing materials
//    protected String filePath = "Resources/Books/";

    /**
     * Constructor
     */
    public MaterialStorage(){
        super();
    }

    /**
     * Allows for saving materials
     * @param material - material object to be saved
     */
    public void save(Material material) {

            save(material, material.getName());
    }


    /**
     * Lists all of the Materials associated with the storage class at the filePath.
     * @return - List of library materials
     */
    public String[] listResources(){
       File directory = new File(filePath);
       return directory.list();
    }

    /**
     * Deletes a resource from the directory at the filePath given its name.
     * @param name - Name of the resource to delete.
     */
    public void deleteResouce(String name){
        File file = new File(filePath + name + ".txt");
        file.delete();
    }

    /**
     * Determines wether a resource of the given name exists.
     * @param name - name of resource to check
     * @return - if resource exists or not
     */
    public boolean exists(String name){
        File file = new File(filePath + name + ".txt");
        return file.exists();
    }
}
