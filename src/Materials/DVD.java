package Materials;

import java.io.Serializable;

/**
 * A DVD library resource.
 */
public class DVD extends Material implements Serializable {

    private String director;
    private int length; //length in minutes
    private String[] actors;

    /**
     * Contructor
     * @param name - name of DVD
     * @param status - status of DVD
     * @param director - DVD director
     */
    public DVD(String name, Status status, String director){
        super(name, status);
        this.director = director;
    }

    /**
     * Constructs a DVD object with a list of actors in the movie
     * @param name - name of DVD
     * @param status - status of resource in library
     * @param director - movie director
     * @param actors - Actors in movie
     */
    public DVD(String name, Status status, String director, String[] actors){
        super(name, status);
        this.director = director;
        this.actors = actors;
    }

    /**
     * Gets the director of the movie
     * @return - director
     */
    public String getDirector(){return this.director;}

    /**
     * Get the list of actors in the movie
     * @return - list of actors
     */
    public String[] getActors(){
        if(this.actors == null){
            return new String[0];
        } else {
            return this.actors;
        }
    }
}
