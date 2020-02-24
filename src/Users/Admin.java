package Users;

import java.io.Serializable;

public class Admin extends Librarian implements Serializable {

	public Admin(String username, String password, String idNumber) {
		super(username, password, idNumber);
		// TODO Auto-generated constructor stub
	}

	public String getType() {return "A";}
}
