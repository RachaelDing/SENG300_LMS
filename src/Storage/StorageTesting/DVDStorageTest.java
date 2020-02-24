package Storage.StorageTesting;

import Materials.DVD;
import Materials.DVD;
import Materials.Material;
import Storage.DVDStorage;
import Storage.DVDStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DVDStorageTest {

    static String[] actors = {"actor1", "actor2", "actor3", "actor4"};
    static DVD dvd = new DVD("TestName", Material.Status.AVAILABLE, "TestDirector", actors);
    static DVDStorage dvdStorage = new DVDStorage();

     @BeforeAll
    public static void setup(){
        dvdStorage.save(dvd);
    }

    @Test
    public void testSerializeDeserialize(){

        dvdStorage.save(dvd);
        DVD testDVD = dvdStorage.retrieve("TestName");
        assertEquals(dvd.getName(), testDVD.getName());
        assertEquals(dvd.getStatus(), testDVD.getStatus());
        assertEquals(dvd.getDirector(), testDVD.getDirector());
    }

    @Test
    public void testChangeBook(){
        String name = "TestName";
        assertTrue(dvdStorage.exists(name));
        DVD testDVD = dvdStorage.retrieve(name);
        testDVD.changeStatus(Material.Status.CHECKOUT);
        dvdStorage.save(testDVD);
        DVD DVD2 = dvdStorage.retrieve(name);
        assertEquals(DVD2.getStatus(), Material.Status.CHECKOUT);
    }

    @Test
    public void testListBooks(){

        String[] res = dvdStorage.listResources();
        String[] correctRes = {"TestName.txt"};
        for(String resource : correctRes){
            if(!Arrays.asList(res).contains(resource))
                assertTrue(false);
        }
        assertTrue(true);
    }

    @AfterAll
    public static void cleanup(){
         dvdStorage.deleteResouce("TestName");

    }
}
