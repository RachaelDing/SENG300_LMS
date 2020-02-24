import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import Materials.Book;
import Materials.Material;
import Materials.Material.Status;
import Storage.BookStorage;
import Storage.UserStorage;
import Storage.UserStorage.Identifier;
import Users.Customer;
import Users.User;

public class SearchPage extends JPanel {
	private JTextField textField;
	private JButton btnSearch;
	private String username;
	private User user;
	private JPanel bookInfo;
	private JPanel usersList;
	private String keyWord;
	private JButton btnLogout;
	private String filePath = "Resources/Books/";
	private ArrayList<Book> bookList = new ArrayList<Book>();

	/**
	 * Create the frame.
	 * @param name of user who login
	 */
	public SearchPage(String name) {
		username = name;
		UserStorage userStorage = new UserStorage();
		this.user = userStorage.retrieve(name, UserStorage.Identifier.USERNAME);
		setBounds(100, 100, 800, 510);
		initComponents();
	}

	/**
	 * Create and initialize components that are common for any user account
	 */
	private void initComponents() {

		setBackground(new Color(119, 136, 153));
		setLayout(null);

		textField = new JTextField("Search");
		textField.setBounds(25, 25, 250, 30);
		add(textField);
		textField.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(300, 25, 125, 30);
		btnSearch.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				research();
			}
		});

		bookInfo = new DetailedInformation("1984");
		usersList = new UsersList("1984");

		btnLogout = new JButton("Log out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.logout();
			}
		});
		btnLogout.setBounds(597, 22, 104, 29);
		btnLogout.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnLogout);
	}

	/**
	 * Search in the filePath, find the books whose name or author name contain the
	 * keyWord and store the books in the bookList
	 */
	private void searchBooks() {
		BookStorage bookStorage = new BookStorage();
		for (File file : new File(filePath).listFiles()) {
			if (file.isFile() && file.getName().contains(".txt")) {
				String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
				Book book = bookStorage.retrieve(fileName);

				if (book.getName().toLowerCase().contains(keyWord.toLowerCase())) {
					bookList.add(book);
				} else if (book.getAuthor().toLowerCase().contains(keyWord.toLowerCase())) {
					bookList.add(book);
				}
			}
		}

	}

	/**
	 * Show the search result with a Detail button and a Borrow button for each
	 * material in the result If user clicks the Borrow button, let user borrow the
	 * book if the user is holding less than 5 materials and the book is not
	 * borrowed If user clicks the Detail button, show the material detaiils
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void searchResult(SearchPage panel) {
		int labelYPos = 50;
		int btnDetailXPos = 140;
		for (Book book : bookList) {
			JLabel lblBookInfo = new JLabel(book.getName() + "\t" + book.getAuthor());
			lblBookInfo.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblBookInfo.setBounds(37, labelYPos, 500, 50);
			add(lblBookInfo);

			addDetailButton(panel, book, btnDetailXPos, labelYPos+40);

			if (book.getStatus() == Material.Status.AVAILABLE && user.getType().equals("C")
					&& (book.getHoldPersonId().equals("") || book.getHoldPersonId().equals(user.getIDNumber()))) {
				addBorrowButton(panel, book, btnDetailXPos + 100, labelYPos+40);
			} else if (user.getType().equals("L")) {
				addReturnButton(panel, book, btnDetailXPos + 100, labelYPos+40);
			} else if (user.getType().equals("A")){
				addHoldButton(panel, book, btnDetailXPos + 100, labelYPos+40);
			}
			labelYPos += 60;
		}
	}

	/**
	 * Add hold button for admin account
	 * @param panel -- add button to the panel
	 * @param book -- add hold button for the book
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addHoldButton(SearchPage panel, Book book, int xPos, int yPos) {
		JButton btnHold = new JButton("Hold");
		btnHold.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHold.setBounds(xPos, yPos, 90, 30);
		add(btnHold);
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(usersList);
				remove(bookInfo);
				usersList = new UsersList(book.getName());
				usersList.setBounds(487, 80, 276, 413);
				usersList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				add(usersList);
				panel.revalidate();
				panel.repaint();
			}
		});
	}

	/**
	 * Add borrow button for customer account.
	 * Show borrow button only when a book can be borrowed
	 * @param panel -- add button to the panel
	 * @param book -- add borrow button for the book
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addBorrowButton(SearchPage panel, Book book, int xPos, int yPos) {
		JButton btnBorrow = new JButton("Borrow");
		btnBorrow.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnBorrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrow.setBounds(xPos, yPos, 90, 30);
		add(btnBorrow);
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completionMessage = "";
				UserStorage uS = new UserStorage();
				Customer user = (Customer) uS.retrieve(username, UserStorage.Identifier.USERNAME);
				if (user.getMaterial().size() < 5 && (book.getStatus() == Material.Status.AVAILABLE
						|| book.getStatus() == Material.Status.DAMAGED)) {
					book.changeStatus(Material.Status.CHECKOUT);
					book.setHoldPersonId("");
					BookStorage bS = new BookStorage();
					bS.save(book);
					user.checkOut(book);
					user.addFee(book.getBookFee());
					uS.save(user);
					completionMessage = "Borrowed book successfully";
					research();
				} else {
					completionMessage = "Cannot borrow the book";
				}
				JOptionPane.showMessageDialog(panel, completionMessage);

			}
		});
	}

	/**
	 * Add detail button for any account
	 * @param panel -- add button to the panel
	 * @param book -- add detail button for the book
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addDetailButton(SearchPage panel, Book book, int xPos, int yPos) {
		JButton btnDetail = new JButton("Detail");
		btnDetail.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnDetail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDetail.setBounds(xPos, yPos, 90, 30);
		add(btnDetail);
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(bookInfo);
				remove(usersList);
				bookInfo = new DetailedInformation(book.getName());
				bookInfo.setBounds(487, 80, 276, 413);
				bookInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				add(bookInfo);
				panel.revalidate();
				panel.repaint();
			}
		});
	}

	/**
	 * Add return button for librarian.
	 * Show return button only when a book can be returned
	 * @param panel -- add button to the panel
	 * @param book -- add return button for the book
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addReturnButton(SearchPage panel, Book book, int xPos, int yPos) {
		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnReturn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReturn.setBounds(xPos, yPos, 90, 30);
		if (book.getStatus() != Material.Status.AVAILABLE)
			add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completionMessage = "";
				book.changeStatus(Material.Status.AVAILABLE);
				BookStorage bS = new BookStorage();
				bS.save(book);
				changeUserBookList(book);
				completionMessage = "Returned book successfully";
				research();
				JOptionPane.showMessageDialog(panel, completionMessage);
			}
		});
	}

	/**
	 * Traverse all customer accounts and find the user who borrows the book, then
	 * remove the book from the materials list of the user
	 * @param book - book to remove 
	 */
	private void changeUserBookList(Book book) {
		String userFilePath = "Resources/Users/";
		UserStorage uS = new UserStorage();
		for (File file : new File(userFilePath).listFiles()) {
			if (file.isFile() && file.getName().contains(".txt") && !file.getName().equals("idlookup.txt")) {
				String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
				User userRetrieved = uS.retrieve(fileName, UserStorage.Identifier.ID_NUMBER);
				if (userRetrieved.getType().equals("C")) {
					Customer c = (Customer) uS.retrieve(fileName, UserStorage.Identifier.ID_NUMBER);
					for (Material m : c.getMaterial()) {
						Book b = (Book) m;
						if (book.name.equals(b.name)) {
							c.checkIn(b);
							uS.save(c);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * research and refresh the page
	 */
	private void research() {
		this.removeAll();
		add(textField);
		add(btnSearch);
		add(btnLogout);
		keyWord = textField.getText();
		bookList.clear();
		searchBooks();
		searchResult(this);
		this.revalidate();
		this.repaint();
	}

	/**
	 * DetailedInformation displays book information to a user. bookName: title of
	 * the book. Should correspond with the file name of the book.
	 * 
	 * shows book cover, author, and title.
	 */
	class DetailedInformation extends JPanel {
		private String bookName;
		private String storageLocation = "Resources/Books/";
		private Book book;

		public DetailedInformation(String bookName) {
			this();
			this.bookName = bookName;
			this.book = new BookStorage().retrieve(bookName);
			setLayout(null);
			initComponents();
		}

		public DetailedInformation() {
			setBackground(new Color(130, 155, 175));
		}

		/**
		 * Loads an image as the cover of a book if it exists. Otherwise, it loads a
		 * default image. Default is currently 1984.png myPicturePath is a bit of a
		 * misnomer; It should just be the title of the book.
		 */
		private JLabel genImage(String myPicturePath) {
			BufferedImage myPicture;
			JLabel picLabel = new JLabel();
			try {
				if (new File(storageLocation + myPicturePath + ".png").exists()) {
					myPicture = ImageIO.read(new File(storageLocation + myPicturePath + ".png"));
				} else {
					myPicture = ImageIO.read(new File(storageLocation + "Default.png")); // Need a different default
																						// eventually.
				}
				ImageIcon asIcon = new ImageIcon(myPicture.getScaledInstance(150, 200, Image.SCALE_FAST));
				picLabel = new JLabel(asIcon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return picLabel;
		}

		/**
		 * Creates the different information fields.
		 */
		private void initComponents() {
			JLabel bookCover = genImage(bookName);
			bookCover.setBounds(75, 25, 150, 200);
			add(bookCover);

			JLabel lblName = new JLabel("Title: " + bookName);
			lblName.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblName.setBounds(25, 250, 150, 25);
			add(lblName);

			JLabel lblAuthor = new JLabel("Author: " + book.getAuthor());
			lblAuthor.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblAuthor.setBounds(25, 275, 200, 25);
			add(lblAuthor);

			JLabel lblStatus = new JLabel("Status: " + book.getStatus());
			lblStatus.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblStatus.setBounds(25, 300, 200, 25);
			add(lblStatus);

			JLabel lblFee = new JLabel("Fee: " + book.getBookFee());
			lblFee.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
			lblFee.setBounds(25, 325, 200, 25);
			add(lblFee);
		}

	}
}


/**
 * 
 * create panel containing a users list and a hold button,
 * so that admin can choose to put hold on the book for which user
 *
 */
class UsersList extends JPanel {
	private String bookFilePath = "Resources/Books/";
	private String userFilePath = "Resources/Users/";
	private String bookName;
	private Book book;
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private JTable userTable;

	/*
	 * constructor
	 * @param bookName - name of the book to put hold on
	 */
	public UsersList(String bookName) {
		this.bookName = bookName;
		this.book = new BookStorage().retrieve(bookName);
		setBackground(new Color(130, 155, 175));
		setLayout(null);
		initComponents();
	}

	/**
	 * create a scrollable table holding every customer'name and id,
	 * and add cancel hold button and hold button.
	 */
	private void initComponents() {
		UserStorage uS = new UserStorage();
		for (File file : new File(userFilePath).listFiles()) {
			if (file.isFile() && file.getName().contains(".txt") && !file.getName().equals("idlookup.txt")) {
				String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
				User userRetrieved = uS.retrieve(fileName, UserStorage.Identifier.ID_NUMBER);
				if (userRetrieved.getType().equals("C")) {
					Customer c = (Customer) uS.retrieve(fileName, UserStorage.Identifier.ID_NUMBER);
					customerList.add(c);
				}
			}
		}

		Object[] ColTitle = {"Name","ID"};
		Object[][] content = new Object[customerList.size()][2];
		for (int i =0; i < customerList.size();i++) {
			content[i][0] = customerList.get(i).getUsername();
			content[i][1] = customerList.get(i).getIDNumber();
		}
		userTable = new JTable(content, ColTitle);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(userTable.getTableHeader(), BorderLayout.NORTH);
		panel.add(userTable, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(25, 65, 200, 300);
		add(scrollPane);

		addHoldButton(this, userTable,25, 10);
		addCancelHoldButton(this, 115, 10);
	}
	
	/**
	 * add cancel hold button 
	 * @param panel -- add button to the panel
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addCancelHoldButton(UsersList panel, int xPos, int yPos) {
		JButton btnHold = new JButton("Cancel Hold");
		btnHold.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHold.setBounds(xPos, yPos, 140, 30);
		add(btnHold);
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completionMessage = "";
				try {
					book.setHoldPersonId("");
					BookStorage bS = new BookStorage();
					bS.save(book);
					completionMessage = "Successfully cancel hold for the book ";
				} catch (Exception ex) {
					completionMessage = "Cannot cancel hold for the book";
				}
				JOptionPane.showMessageDialog(panel, completionMessage);
			}
		});
	}

	/**
	 * add hold button to the panel
	 * @param panel -- add button to the panel
	 * @param table -- table holding users info
	 * @param xPos -- x position of the button
	 * @param yPos -- y position of the button
	 */
	private void addHoldButton(UsersList panel, JTable table, int xPos, int yPos) {
		JButton btnHold = new JButton("Hold");
		btnHold.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHold.setBounds(xPos, yPos, 90, 30);
		add(btnHold);
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completionMessage = "";
				Customer c;
				try {
					c = customerList.get(table.getSelectedRow());
					book.setHoldPersonId(c.getIDNumber());
					BookStorage bS = new BookStorage();
					bS.save(book);
					completionMessage = "Successfully hold the book for user " + c.getUsername();
				} catch (Exception ex) {
					completionMessage = "Cannot hold the book";
				}
				JOptionPane.showMessageDialog(panel, completionMessage);
			}
		});
	}
}
