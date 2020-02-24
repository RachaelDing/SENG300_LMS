package Materials;

import java.io.Serializable;

/**
 * A library resource.
 */
public class Material implements Serializable {
	private static String available = "available"; //do not change value
	private static String checkedOut = "checkedout"; //do not change value
	private static String damaged = "damaged"; // do not change value
	private static String ordered = "ordered"; // do not change value
	//Status of the resource in the system
	public enum Status {AVAILABLE, CHECKOUT, DAMAGED, ORDERED}
	protected Status status;
	public String name;
	public String holdPersonId = "";


	/**
	 * Constructor
	 * @param name - Materials.Material name
	 * @param status - Status of material in system
	 */
	public Material(String name, Status status) {
		this.name = name;
		this.status = status;
		
	}

	/**
	 * Changes the status to the given Materials.Material.Status value.
	 * @param status - status of the book
	 */
	public void changeStatus(Status status){
		this.status = status;
	}

	/**
	 * Getter for name
	 * @return - name of resource
	 */
	public String getName(){return this.name;}

	/**
	 * Getter for status
	 * @return - status of resource
	 */
	public Status getStatus(){return this.status;}
	
	public String getHoldPersonId(){return this.holdPersonId;}
	
	public void setHoldPersonId(String id){this.holdPersonId = id;}

}
