package Materials;

import java.io.Serializable;

/**
 * A book library resource.
 */
public class CD extends Material implements Serializable {

    private String artist;
    private String[] songs;

    /**
     * Contructor
     * @param name - name of cd
     * @param status - status of cd
     * @param artist - recording artist
     */
    public CD(String name, Status status, String artist){
        super(name, status);
        this.artist = artist;
    }

    /**
     * Constructor
     * @param name - name of CD
     * @param status - status of CD
     * @param artist - recording artist
     * @param songs - List of songs
     */
    public CD(String name, Status status, String artist, String[] songs){
        super(name, status);
        this.artist = artist;
        this.songs = songs;
    }

    /**
     * Gets the recording artist
     * @return - book author
     */
    public String getArtist(){return this.artist;}
    public void setSongList(String[] songs){this.songs = songs;}
    public String[] getSongList(){return this.songs;}
}
