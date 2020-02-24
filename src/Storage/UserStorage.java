package Storage;

import Users.Customer;
import Users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Allows for serializes saving and retrieving of Users.User objects. Objects will be saved at the filePath.
 * A Hashmap is utilized to keep track of userName and userID pairs so a user object can be retrieved
 * with either userName or userID. Serialize objects are saved with the userID number as a file name as
 * userID is an immutable characteristic of a user account (usernames could potentially be changed).
 */
public class UserStorage extends Storage implements Serializable {

    private String lookupFileName;                  //File to save/retrieve lookup table
    public enum Identifier {ID_NUMBER, USERNAME};   //Request id type
    private static HashMap<String, String> IDLookup = null;               //Lookup table to lookup id numbers

    /**
     * Constructor creates a new instance of UserStorage which will store and retrieve files from the path "Resources/Users"
     */
    public UserStorage(){
        super();
        filePath = "Resources/Users/";
        lookupFileName = "idlookup";
        //Retrieve lookup table from file
        IDLookup = getIDLookupInstance();
    }

    /**
     * Save a user.
     * @param user - Users.User object to save
     */
    public void save(User user){
        //Save user object
        save(user, user.getIDNumber());

        //Put user namarg0e and id into lookup table and save table
        IDLookup.put(user.getUsername(), user.getIDNumber());
        save(IDLookup, lookupFileName);
    }

    /**
     * Retrieve a user. Include Identifier.ID_Number as second parameter if first parameter is an idNumber.
     * Include Identifier.USERNAME as second parameter if first parameter is a UserName. **Returns a Users.User object which
     * must be cast to the type of user you are requesting (i.e. for Users.Customer - Users.Customer c = (Users.Customer) userStorage.retrieve("111111",
     * Identifier.ID_NUMBER)**
     * @param userID - userIDNumber or username
     * @param id - type of identifier
     * @return
     */
    public User retrieve(String userID, Identifier id){
        if(id == Identifier.ID_NUMBER){
            return (User) retrieve(userID);
        } else {
            String idNumber = IDLookup.get(userID);
            return (User) retrieve(idNumber);
        }
    }

    /**
     * Check if an entry for the given id number exists in the system.
     * @param idNumber - unique identifying number belonging to user
     * @return - true if exists
     */
    public boolean userIDExists(String idNumber){
        return exists(idNumber);
    }

    /**
     * Check if an entry for the given username exists in the system.
     * @param userName - unique username user has chosen
     * @return - true if exists
     */
    public boolean userNameExists(String userName){
        return IDLookup.containsKey(userName);
    }

    /**
     * Get the HashMap IDLookup, a new hashmap is created if one cannot be retrieved from file.
     * @return - lookup table for usernames.
     */
    public HashMap<String, String> getIDLookupInstance(){
        IDLookup = (HashMap) retrieve(lookupFileName);

        if(IDLookup == null){
            IDLookup = new HashMap<>();
            return IDLookup;
        } else {
            return IDLookup;
        }
    }

    /**
     * Gets a list of all of the current users in the system.
      * @return - Arraylist of all current user object in system.
     */
    public ArrayList<User> getUsers(){
        ArrayList users = new ArrayList();
        IDLookup = (HashMap) retrieve(lookupFileName);
        Collection<String> userIds = IDLookup.values();
        Iterator iterator = userIds.iterator();
        while(iterator.hasNext()){
            User user = this.retrieve(iterator.next().toString(), Identifier.ID_NUMBER);
            users.add(user);
        }
        return users;
     }

    /**
     * Gets a list of all current non admin users in the system.
     * @return - An arraylist of customer objects that are in the system.
     */
    public ArrayList<User> getNonAdminUser(){

        ArrayList<User> customers = new ArrayList<>();
        ArrayList<User> users = this.getUsers();

        for(User user : users){
                if (!user.getType().equals("A"))
                    customers.add(user);
        }

        return customers;
     }
}
