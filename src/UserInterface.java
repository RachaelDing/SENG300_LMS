import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Storage.UserStorage;
import Users.User;

import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;

public abstract class UserInterface extends JFrame {
	private String name;
	private User user;
	private AccountInfoPage accInfoTab;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	public UserInterface(String name) {
		this.name = name;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		UserStorage userStorage = new UserStorage();
		this.user = userStorage.retrieve(name, UserStorage.Identifier.USERNAME);
		initComponents();
	}

	public void initComponents() {
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		getContentPane().add(tabbedPane);

		AccountInfoPage accInfoTab = createAccountPage(user);
		
		tabbedPane.addTab("Account Information", null,accInfoTab, null);

		JPanel searchTab = new SearchPage(name);
		tabbedPane.addTab("Search", null, searchTab, null);

		//If user is a customer or librarian, update the account info page when a tab is clicked, so that the material info is always up-to-date
		if (user.getType().equals("C")) {
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					accInfoTab.reloadForC();
					accInfoTab.revalidate();
					accInfoTab.repaint();
				}
			});
		} else if (user.getType().equals("L")) {
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					accInfoTab.reloadForL();
					accInfoTab.revalidate();
					accInfoTab.repaint();
				}
			});
		}

	}

	public AccountInfoPage createAccountPage(User user) {
		AccountInfoPage result = new AccountInfoPage(user); // guarantee initialization.
		switch (user.getType()) {
		case "A":
			result = new AccountInfoPage(user);
			break;
		case "L":
			result = new AccountInfoPage(user);
			break;
		case "C":
			result = new AccountInfoPage(user);
		}
		return result;
	}
	public JTabbedPane getTabbedPane() {return this.tabbedPane;}
	public User getUser(){return this.user;}
}
