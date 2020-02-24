package Storage.StorageTesting;

import Materials.Book;
import Materials.Material;
import Storage.BookStorage;
import Storage.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test methods for the book storage class. Tests storing, retrieving, changing and listing resources available.
 */
public class BookStorageTest {

	static Book testBook = new Book("MyTestBook", Material.Status.AVAILABLE, "TestAuthor", 5);
    static Book testBook1 = new Book("MyTestBook1", Material.Status.AVAILABLE, "TestAuthor1", 3);
    static Book testBook2 = new Book("MyTestBook2", Material.Status.AVAILABLE, "TestAuthor2", 4);
    static Book testBook3 = new Book("1984", Material.Status.AVAILABLE, "George Orwell", 5);
    static Book testBook4 = new Book("Harry Potter and the Sorcerers Stone", Material.Status.AVAILABLE, "J.K Rowling", 6);
    static Book testBook5 = new Book("the Catcher in the Rye", Material.Status.AVAILABLE, "J.D. Salinger", 4);
    static Book testBook6 = new Book("The Great Gatsby", Material.Status.AVAILABLE, "F. Scott Fitzgerald", 5);
    static Book testBook7 = new Book("To Kill a Mockingbird", Material.Status.AVAILABLE, "Harper Lee", 7);
    static Book[] books = {testBook, testBook1, testBook2};
    static BookStorage bookStorage = new BookStorage();

    @BeforeAll
    public static void setup(){
        bookStorage.save(testBook);
        bookStorage.save(testBook1);
        bookStorage.save(testBook2);
        bookStorage.save(testBook3);
        bookStorage.save(testBook4);
        bookStorage.save(testBook5);
        bookStorage.save(testBook6);
        bookStorage.save(testBook7);
    }

    @Test
    public void testSerializeDeserialize(){

        bookStorage.save(testBook);
        Book testBook2 = bookStorage.retrieve("MyTestBook");
        assertEquals(testBook.getName(), testBook2.getName());
        assertEquals(testBook.getStatus(), testBook2.getStatus());
        assertEquals(testBook.getAuthor(), testBook2.getAuthor());
    }

    @Test
    public void testChangeBook(){
        String name = "MyTestBook";
        Assertions.assertTrue(bookStorage.exists(name));
        Book testBook = bookStorage.retrieve("MyTestBook");
        testBook.changeStatus(Material.Status.AVAILABLE);
        bookStorage.save(testBook);
        Book book2 = bookStorage.retrieve(name);
        assertEquals(book2.getStatus(), Material.Status.AVAILABLE);
    }

    @Test
    public void testListBooks(){

        String[] res = bookStorage.listResources();
        String[] correctRes = {"MyTestBook2.txt", "MyTestBook.txt", "MyTestBook1.txt"};
        for(String resource : correctRes){
            if(!Arrays.asList(res).contains(resource))
                assertTrue(false);
        }
        assertTrue(true);
    }

    @AfterAll
    public static void cleanup(){
        for(Book b: books){
          //  bookStorage.deleteResouce(b.getName());
        }
    }





}
