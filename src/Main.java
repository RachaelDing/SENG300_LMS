import java.awt.EventQueue;

import Storage.UserStorage;
import Storage.UserStorage.Identifier;
import Users.*;

public class Main {
	public static LoginPage login;
	public static SignUpPage signup;
	public static UserInterface userpage;
	/**
	 * Performs initial setup.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UserInterface intrfcTest = new UserInterface();
					//intrfcTest.setVisible(true);
					Main.login = new LoginPage();
					Main.signup = new SignUpPage("C");
					showLoginPage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void showLoginPage() {login.setVisible(true);}
	public static void hideLoginPage() {login.setVisible(false);}
	public static void showSignupPage(String userType) {
		signup.setUserType(userType);
		signup.adjustPageContent();
		signup.setVisible(true);
	}
	public static void hideSignupPage() {signup.setVisible(false);}
	public static void showUserPage() {userpage.setVisible(true);}
	public static void hideUserPage() {userpage.setVisible(false);}
	/**
	 * Initializes the user and creates the correct interface for them.
	 * @param name: the name of the user who's logging in. 
	 */
	public static void createUserPage(String name) {
		User myUser = (User) new UserStorage().retrieve(name, Identifier.USERNAME);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String type = myUser.getType();
					UserInterface someInterface;// = new CustomerInterface(name);
					switch(type){
						case "A": someInterface = new AdminInterface(name); 
							break;
						case "L": someInterface = new LibrarianInterface(name); 
							break;
						case "C": someInterface = new CustomerInterface(name);
							break;
						default: throw new Exception("Could not identify user type");
					}
					Main.userpage = someInterface;
					Main.showUserPage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void logout() {
		signup.setUserType("C");
		hideUserPage();
		showLoginPage();
	}
}
