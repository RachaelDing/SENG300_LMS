import Materials.Book;
import Materials.CD;
import Materials.DVD;
import Materials.Material;
import Storage.BookStorage;
import Storage.CDStorage;
import Storage.DVDStorage;

public class ImportResource {

    public static void main(String[] args){
//        Book b = new Book("The Great Gatsby", Material.Status.AVAILABLE, "F. Scott Fitzgerald");
//        importBook(b);
//        b = new Book("To Kill a Mockingbird", Material.Status.AVAILABLE, "Harper Lee");
//        importBook(b);
//        b = new Book("1984", Material.Status.AVAILABLE, "George Orwell");
//        importBook(b);
//        b = new Book("The Catcher in the Rye", Material.Status.AVAILABLE, "J.D. Salinger");
//        importBook(b);
//        b = new Book("Harry Potter and the Sorcerers Stone", Material.Status.AVAILABLE, "J.K. Rowling");
//        importBook(b);
//        String[] songs = {"The Once and Future Carpenter", "Live and Die", "Winter in My Heart",
//                "Pretty Girl from Michigan","I Never Knew You",	"February Seven", "Through My Prayers",
//                "Down With the Shine","A Father's First Spring", "Geraldine", 	"Paul Newman vs. The Demons",
//                "Life"};
//        CD cd = new CD("The Carpenter", Material.Status.AVAILABLE, "The Avett Brothers", songs);
//        importCD(cd);
//        String[] songs2 = {"Mansard Roof", "Oxford Comma", "A-Punk", "Cape Cod Kwassa Kwassa", "M79", "Campus",
//                "Bryn", "One (Blake's Got a New Face)", "I Stand Corrected", "Walcott", "The Kids Don't Stand a Chance"};
//        cd = new CD("Vampire Weekend", Material.Status.AVAILABLE, "Vampire Weekend", songs2);
//        importCD(cd);
        String[] actors = {"Eddie Redmayne", "Katherina Waterston"};
        DVD dvd = new DVD("Fantastic Beasts The Crimes of Grindewald", Material.Status.AVAILABLE, "David Yates", actors);
        importDVD(dvd);
        String[] actors2 = {"Viggo Mortensen", "Mahershala Ali"};
        dvd = new DVD("Green Book", Material.Status.AVAILABLE, "Peter Farelly", actors2);
        importDVD(dvd);

    }

    public static void importBook(Book book){
        BookStorage storage = new BookStorage();
        storage.save(book);
    }


    public static void importCD(CD cd){
        CDStorage storage = new CDStorage();
        storage.save(cd);
    }

    public static void importDVD(DVD dvd){
        DVDStorage storage = new DVDStorage();
        storage.save(dvd);
    }

}
