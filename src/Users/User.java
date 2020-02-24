package Users;

import java.io.Serializable;
import java.util.ArrayList;

import Materials.Material;

public abstract class User implements Serializable {
	//Class variables / methods. Used to keep track of created users.
	private static ArrayList<User> allUsers = new ArrayList<User>();
	public static User getUser(String userName) {
		for (User someUser: allUsers) {
			if (someUser.getUsername() == userName) {
				return someUser;
			}
		}
		return null;
	}
	public static ArrayList<User> getAllUsers(){return allUsers;}
	//Recomend generating user objects on file load and adding them to this list so they can be searched for later.
	public static void addUser(User someUser) {
		allUsers.add(someUser);
	}
	
	//Object variables / methods.
	private String username;
	private String password;
	private String name;
	private String idNumber;
	private ArrayList<String> permissions = new ArrayList<String>();
	private boolean accDisabled = false;
	public User(String username, String password, String idNumber) {
		allUsers.add(this);
		this.username = username;
		this.password = password;
		this.idNumber = idNumber;
	}
	public void setName(String name) {this.name = name;}
	public void setPassword(String password) {this.password = password;}
	public boolean checkPassword(String password) {
		if (this.password.equals(password)){
			return true;
		}
		return false;
	}
	public String getUsername() {return this.username;}

	public String getIDNumber(){return this.idNumber;}
	
	public int getFee() {return 2;}
	public int getAmountPaid() {return 2;}
	
	public String getType() {return "";}
	public boolean getAccDisabled() {return this.accDisabled;}
	public void enable() {this.accDisabled = false;}
	public void disable() {this.accDisabled = true;}
	public String getStatus() {
		if (this.accDisabled)
			return "DISABLED";
		else
			return "ENABLED";
	}

	public String toString(){
		return String.format("UserName: %-15sID: %-15sAccount Type: %-3sStatus: %s", getUsername(), getIDNumber(), getType(), getStatus());
	}
}
