package Storage;

import Materials.Book;
import Materials.Material;

import java.io.*;

/**
 * Abstract Class for the storing and retrieving of Objects. Children of this class allow for saving object,
 * retrieving objects and deleting objects.
 */
public abstract class Storage {


    //Default file path for accessing materials
    protected String filePath;

    /**
     * Constructor
     */
    public Storage(){}

    /**
     * Allows for saving materials
     * @param object - material object to be saved
     */
    public void save(Object object, String name) {
        try {

            //Create file to save to
            File f = new File(filePath + name + ".txt");
            f.createNewFile();

            //Write to file
            FileOutputStream file = new FileOutputStream(filePath + name + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);

            //Close write and file
            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create file.");
        }
    }

    /**
     * Retrieves a library Materials.Materials.Material object from the storage at the filePath.
     * @param name - Name of material to retrieve
     * @return - Materials.Materials.Material object
     */
    protected Object retrieve(String name){

        Object object = null;

        try{

            FileInputStream file = new FileInputStream(filePath+ name + ".txt");
            ObjectInputStream in = new ObjectInputStream(file);
            //Read serial object from file
            object = in.readObject();

            //Close file and reader
            in.close();
            file.close();

        } catch (FileNotFoundException e){
            System.out.println("Object does not exist");
        } catch (IOException e){
            System.out.println("Unable to read Object information");
        } catch (ClassNotFoundException e){
            System.out.println("Class not found.");
        }
        return object;
    }

    /**
     * Lists all of the Materials associated with the storage class at the filePath.
     * @return - List of library materials
     */
    public String[] listEntries(){
       File directory = new File(filePath);
       return directory.list();
    }

    /**
     * Deletes a resource from the directory at the filePath given its name.
     * @param name - Name of the resource to delete.
     */
    public void deleteEntry(String name){
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
