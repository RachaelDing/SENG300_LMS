package Storage.StorageTesting;

import Materials.CD;
import Materials.Material;
import Storage.CDStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CDStorageTest {

    static String[] songs = {"Song1", "Song2", "Song3", "Song4"};
    static CD cd = new CD("TestName", Material.Status.AVAILABLE, "TestArtist", songs);
    static CDStorage cdStorage = new CDStorage();

     @BeforeAll
    public static void setup(){
        cdStorage.save(cd);
    }

    @Test
    public void testSerializeDeserialize(){

        cdStorage.save(cd);
        CD testCD = cdStorage.retrieve("TestName");
        assertEquals(cd.getName(), testCD.getName());
        assertEquals(cd.getStatus(), testCD.getStatus());
        assertEquals(cd.getArtist(), testCD.getArtist());
    }

    @Test
    public void testChangeBook(){
        String name = "TestName";
        assertTrue(cdStorage.exists(name));
        CD testCD = cdStorage.retrieve(name);
        testCD.changeStatus(Material.Status.CHECKOUT);
        cdStorage.save(testCD);
        CD cd2 = cdStorage.retrieve(name);
        assertEquals(cd2.getStatus(), Material.Status.CHECKOUT);
    }

    @Test
    public void testListBooks(){

        String[] res = cdStorage.listResources();
        String[] correctRes = {"TestName.txt"};
        for(String resource : correctRes){
            if(!Arrays.asList(res).contains(resource))
                assertTrue(false);
        }
        assertTrue(true);
    }

    @AfterAll
    public static void cleanup(){
         cdStorage.deleteResouce("TestName");

    }
}
