import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import Storage.UserStorage;
import Users.Customer;
import Users.User;

public class AdminInterface extends LibrarianInterface {
	private EditUser editUser;
	public AdminInterface(String name) {
		super(name);
		this.editUser = new EditUser(getUser());
		getTabbedPane().addTab("Edit User", this.editUser);
	}
	
}
class EditUser extends JPanel{
	private JLabel disableMessage;
	private JTextField UTID;
	private JButton submit;
	private JLabel status;
	private JList list;
	private JScrollPane customerScroll;
	private User currentUser;
	private JButton btnLogOut;
	//Takes the CURRENT user as an input. This is not the user you are editing, but rather the admin who is currently logged in.
	EditUser(User currentUser) {
		super();
		setLayout(null);
		setBackground(new Color(119, 136, 153));
		this.currentUser = currentUser;
		
		initComponents();
		createEvents(currentUser);
	}
	private void initComponents() {
		disableMessage = new JLabel("Enter account ID:");
		disableMessage.setBounds(25, 25, 200, 30);
		add(disableMessage);
		
		submit = new JButton("Toggle status");
		submit.setBounds(597, 75, 150, 30);
		add(submit);

		setCustomerList();

		
		status = new JLabel("");
		status.setBounds(25, 100, 300, 30);
		status.setForeground(Color.RED);
		add(status);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.logout();
			}
		});
		btnLogOut.setBounds(597, 22, 104, 29);
		btnLogOut.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnLogOut);
	}

	private void createEvents(User currentUser) {
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User selectedValue = (User)list.getSelectedValue();
				if(selectedValue.getAccDisabled()){
					selectedValue.enable();
				} else {
					selectedValue.disable();
				}
				UserStorage userStorage = new UserStorage();
				userStorage.save(selectedValue);
				resetCustomerList();
				//Redraw scroll pane
				customerScroll.revalidate();
			}
		});
	}

	/**
	 * Get the users to display
	 * @return - ListModeL of user objects to display
	 */
	private DefaultListModel<User> getCustomers(){
		UserStorage userStorage = new UserStorage();
		ArrayList<User> customerObjects = userStorage.getNonAdminUser();
		DefaultListModel<User> output = new DefaultListModel();
		for(int i=0; i<customerObjects.size(); i++){
			output.add(i, customerObjects.get(i));
		}
		return output;
	}

	/**
	 * Set the list of customers to display for enabling disabling
	 */
	private void setCustomerList(){
		DefaultListModel<User> customerList = this.getCustomers();
		list = new JList(customerList);
		customerScroll = new JScrollPane(list);
		customerScroll.setBounds(25, 75, 550, 100);
		add(customerScroll);
	}

	/**
	 * Reset the customer list
	 */
	private void resetCustomerList(){
		remove(customerScroll);
		setCustomerList();
	}
}