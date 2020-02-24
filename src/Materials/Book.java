package Materials;

import java.io.*;

/**
 * A book library resource.
 */
public class Book extends Material implements Serializable {

    private String author;
    
    private int bookFee;

    /**
     * Contructor
     * @param name - name of book
     * @param status - status of book
     * @param author - book author
     */
    public Book(String name, Status status, String author, int fee){
        super(name, status);
        this.author = author;
        this.bookFee = fee;
    }

    /**
     * Gets the author of the book
     * @return - book author
     */
    public String getAuthor(){return this.author;}
    public int getBookFee(){return this.bookFee;}
}
