package Storage.StorageTesting;

import Materials.Book;
import Materials.Material;
import Storage.UserStorage;
import Users.Admin;
import Users.Customer;
import Users.Librarian;
import Users.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserStorageTest {


    private static Customer user1;
    private static Customer user2;
    private static Customer user3;
    private static Admin admin1;
    private static Librarian lib1;

    static User[] users = new User[5];
    static UserStorage userStorage;

    @BeforeAll
    public static void setup(){
        userStorage = new UserStorage();
        user1 = new Customer("Alice", "123456", "100000");
        users[0] = user1;
        user2 = new Customer("Bob", "testtest", "200000");
        users[1] = user2;
        user3 = new Customer("Rachel", "password", "300000");
        users[2]  = user3;
        admin1 = new Admin("Admin00", "123456", "000000");
        users[3]  = admin1;
        lib1 = new Librarian("L00", "123456", "111111");
        users[4]  = lib1;
        userStorage.save(user1);
        userStorage.save(user2);
        userStorage.save(user3);
        userStorage.save(admin1);
        userStorage.save(lib1);
    }

    @Test
    public void testSerializeDeserialize(){
        user1.addFee(5);
        userStorage.save(user1);
        Customer testUser2 = (Customer) userStorage.retrieve("100000", UserStorage.Identifier.ID_NUMBER);
        Assertions.assertEquals(user1.getUsername(), testUser2.getUsername());
        Assertions.assertEquals(5, testUser2.getFee());
    }

    @Test
    public void testChangeCustomer(){
        String id = "200000";
        Assertions.assertTrue(userStorage.exists(id));
        Customer cust = (Customer) userStorage.retrieve(id, UserStorage.Identifier.ID_NUMBER);
        cust.addFee(30);
        userStorage.save(cust);
        Customer cust2 = (Customer) userStorage.retrieve(id, UserStorage.Identifier.ID_NUMBER);
        Assertions.assertEquals(cust2.getFee(), 30);
    }

    @Test
    public void testRetrieveUserNAme(){

        Customer cust = (Customer) userStorage.retrieve("Rachel", UserStorage.Identifier.USERNAME);
        assertEquals(cust.getIDNumber(), "300000");
    }

     @AfterAll
     public static void cleanup(){
         for(User user: users){
//             userStorage.deleteEntry(user.getIDNumber());
         }
     }

}
