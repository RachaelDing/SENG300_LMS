package Users;

import Materials.Material;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {
	ArrayList<Material> checkedOut = new ArrayList<Material>();
	int fees = 0;
	int amount_paid = 0;
	public Customer(String username, String password, String idNumber) {
		super(username, password, idNumber);
	}
	//Adds a material to the list of materials that the user has checked out.
	public void checkOut(Material someMaterial) {
		checkedOut.add(someMaterial);
	}
	public void checkIn(Material someMaterial) {
		checkedOut.remove(someMaterial);
	}
	//Adds fees to the user account, but only allows adding fees greather than 0.
	public void addFee(int newFee) {
		if (newFee > 0) {
			fees += newFee;
		}
	}

	public int getFee(){return this.fees;}
	public int getAmountPaid(){return this.amount_paid;}
	
	public String getType() {return "C";}
	public ArrayList<Material> getMaterial() {
		return checkedOut;
	}



}
