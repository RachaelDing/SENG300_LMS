package Users;

import java.io.Serializable;

public class Librarian extends User implements Serializable {

	public Librarian(String username, String password, String idNumber) {
		super(username, password, idNumber);
		// TODO Auto-generated constructor stub
	}

	public String getType() {return "L";}
}
