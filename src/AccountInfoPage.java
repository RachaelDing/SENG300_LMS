
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import Materials.Book;
import Materials.Material;
import Materials.Material.Status;
import Storage.BookStorage;
import Storage.UserStorage;
import Users.Customer;
import Users.User;

/*
 * Not implemented.
 */

public class AccountInfoPage extends JPanel {
	// private JLabel notImplement;
	private JButton btnLogOut;
	private JButton btnchangeUP;
	private JButton btnAddAccount;
	private JPanel borrowedBooksPanel;
	private User user;
	public static ChangePassword changePassword;
	private JLabel lblBookInfo;
	private JLabel title;
	private JLabel userName;
	private JLabel title2;
	private JLabel title3;
	private JLabel title4;
	private JLabel title5;
	private JLabel feeDue;
	private JLabel amountPaidLabel;
	private JLabel amountPaid;

	/**
	 * Create the frame.
	 * @param user - user who login
	 */
	public AccountInfoPage(User user) {
		this.user = user;
		initComponents();
		// this.setLocationRelativeTo(null); //set screen to center
		createEvents(this);
	}

	/**
	 * Create and initialize components that are common for any user account
	 */
	private void initComponents() {
		setBounds(100, 100, 800, 600);
		setBackground(new Color(119, 136, 153));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		title = new JLabel("Welcome");
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(89, 31, 147, 40);
		add(title);

		userName = new JLabel(user.getUsername());
		userName.setFont(new Font("Tahoma", Font.BOLD, 26));
		userName.setBounds(228, 31, 222, 40);
		add(userName);

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
		
		
		if (user.getType().equals("C")) {
			addLabelsForC();
		} 	
		else if (user.getType().equals("L")) {
			addScrollPaneForL();
		}
		else {
			addAddAccountBtn();
		}
		
		btnchangeUP = new JButton("Edit");
		btnchangeUP.setToolTipText("");

		btnchangeUP.setBounds(597, 459, 109, 29);
		btnchangeUP.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnchangeUP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnchangeUP);

		title4 = new JLabel("Change username\r\n");
		title4.setBounds(587, 420, 147, 18);
		title4.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		add(title4);

		title5 = new JLabel("or Password");
		title5.setBounds(609, 439, 99, 14);
		title5.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		add(title5);

	}

	/**
	 * Add add account button and borrowed book info for librarian account
	 */
	private void addScrollPaneForL() {
		addAddAccountBtn();

		BookStorage bS = new BookStorage();
		String[] bookList = bS.listResources();
		Vector<Book> borrowedBooks = new Vector<Book>();

		for (String fileName : bookList) {
			String bookName = fileName.substring(0, fileName.lastIndexOf("."));
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			bS = new BookStorage();
			Book book = bS.retrieve(bookName);
			if (suffix.equals(".txt") && book.getStatus() == Status.CHECKOUT) {
				borrowedBooks.add(book);
			}
		}
		
		title3 = new JLabel("Borrowed books:");
		title3.setFont(new Font("Tahoma", Font.BOLD, 16));
		title3.setBounds(89, 165, 233, 26);
		add(title3);

		borrowedBooksPanel = new JPanel();
		for (Book b : borrowedBooks) {
			System.out.println(b.getName());
			lblBookInfo = new JLabel(b.getName());
			lblBookInfo.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			borrowedBooksPanel.add(lblBookInfo);
		}
		borrowedBooksPanel.setLayout(new BoxLayout(borrowedBooksPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(borrowedBooksPanel);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(101, 200, 400, 180);
		add(scrollPane);
	}
	
	
	/**
	 * Add add account button for admin account
	 */
	private void addAddAccountBtn() {
		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.setToolTipText("");
		btnAddAccount.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnAddAccount.setBounds(101, 459, 140, 29);
		btnAddAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnAddAccount);
		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.showSignupPage(user.getType());
			}
		});
	}
	
	
	/**
	 * Add fee info and materials labels for customer account
	 */
	private void addLabelsForC() {
		title2 = new JLabel("My Fees:");
		title2.setFont(new Font("Tahoma", Font.BOLD, 16));
		title2.setBounds(116, 103, 106, 26);
		add(title2);
		
		feeDue = new JLabel(String.valueOf(user.getFee()));
		feeDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		feeDue.setBounds(269, 107, 106, 18);
		add(feeDue);
		
		amountPaidLabel = new JLabel("Amount paid:");
		amountPaidLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		amountPaidLabel.setBounds(376, 103, 156, 26);
		add(amountPaidLabel);
		
		amountPaid = new JLabel(String.valueOf(user.getAmountPaid()));
		amountPaid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		amountPaid.setBounds(569, 103, 106, 26);
		add(amountPaid);


		title3 = new JLabel("My Materials :");
		title3.setFont(new Font("Tahoma", Font.BOLD, 16));
		title3.setBounds(89, 165, 156, 26);
		add(title3);

		int labelYPos = 200;
		for (Material m : ((Customer) user).getMaterial()) {
			lblBookInfo = new JLabel(m.getName());
			lblBookInfo.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblBookInfo.setBounds(244, labelYPos, 500, 50);
			add(lblBookInfo);
			labelYPos += 60;
		}
	}
	
	/**
	 * Create the events
	 */
	private void createEvents(AccountInfoPage frame) {
		btnchangeUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AccountInfoPage.changePassword = new ChangePassword();
				changePassword.setVisible(true);
			}
		});
	}

	/**
	 * reload the page for customer account
	 */
	public void reloadForC() {
		removeAll();
		int labelYPos = 200;
		String username = "";
		username = user.getUsername();
		UserStorage uS = new UserStorage();
		user = uS.retrieve(username, UserStorage.Identifier.USERNAME);
		for (Material m : ((Customer) user).getMaterial()) {
			JLabel lblBookInfo = new JLabel(m.getName());
			lblBookInfo.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblBookInfo.setBounds(244, labelYPos, 500, 50);
			add(lblBookInfo);
			labelYPos += 60;
		}
		feeDue = new JLabel(String.valueOf(user.getFee()));
		feeDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		feeDue.setBounds(269, 107, 106, 18);

		add(title);
		add(title2);
		add(feeDue);
		add(title3);
		add(userName);
		add(btnLogOut);
		add(btnchangeUP);
		add(title4);
		add(title5);
		add(amountPaidLabel);
		add(amountPaid);
	}
	
	/**
	 * reload the page for librarian account
	 */
	public void reloadForL() {
		borrowedBooksPanel.removeAll();
		BookStorage bS = new BookStorage();
		String[] bookList = bS.listResources();
		Vector<Book> borrowedBooks = new Vector<Book>();
		
		for (String fileName : bookList) {
			String bookName = fileName.substring(0, fileName.lastIndexOf("."));
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			bS = new BookStorage();
			Book book = bS.retrieve(bookName);
			if (suffix.equals(".txt") && book.getStatus() == Status.CHECKOUT) {
				borrowedBooks.add(book);
			}
		}
		for (Book b : borrowedBooks) {
			System.out.println(b.getName());
			lblBookInfo = new JLabel(b.getName());
			lblBookInfo.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			borrowedBooksPanel.add(lblBookInfo);
		}

	}
}