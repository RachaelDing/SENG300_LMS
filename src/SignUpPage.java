import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Storage.UserStorage;
import Users.Admin;
import Users.Customer;
import Users.Librarian;
import Users.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class SignUpPage extends JFrame {

	private JPanel contentPane;
	private JTextField uidField, usernameField;
	private JTextField passwordField, passwordField_1;
	private JButton btnCreate;
	private JButton btnBack;
	private boolean signup_status = false;
	private String completionMessage;
	private String userType;
	private JTextField typeField;
	private JLabel typeLabel;
	

	
	/**
	 * Create the frame.
	 */

	public SignUpPage(String type) {
		super("LoginPage"); // super page
		userType = type;
		initComponents();
		this.setLocationRelativeTo(null);			//set screen to center
		createEvents(this);
	}
	
	/**
	 * Create and initilize components
	 */
	private void initComponents() {
		setTitle("Library Management System");			// set title for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 492);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(119, 136, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStudentId = new JLabel("U of T ID");
		lblStudentId.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblStudentId.setBounds(88, 30, 83, 16);
		contentPane.add(lblStudentId);

		JLabel lblUserName = new JLabel("User name");
		lblUserName.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblUserName.setBounds(88, 100, 105, 18);
		contentPane.add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblPassword.setBounds(88, 170, 105, 16);
		contentPane.add(lblPassword);

		JLabel lblComfirePassword = new JLabel("Confirm Password");
		lblComfirePassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblComfirePassword.setBounds(88, 240, 161, 16);
		contentPane.add(lblComfirePassword);
		
		typeLabel = new JLabel("Account Type (Admin, Librarian or Customer)");
		typeLabel.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		typeLabel.setBounds(89, 310, 400, 18);

		uidField = new JTextField();
		uidField.setBounds(88, 46, 400, 42);
		contentPane.add(uidField);
		uidField.setColumns(10);

		usernameField = new JTextField();
		usernameField.setBounds(88, 116, 400, 42);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(88, 186, 400, 42);
		contentPane.add(passwordField);
		passwordField.setColumns(10);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(88, 256, 400, 42);
		contentPane.add(passwordField_1);
		passwordField_1.setColumns(10);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(89, 326, 400, 42);

		btnCreate = new JButton("Create");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnCreate.setBounds(140, 402, 132, 35);
		contentPane.add(btnCreate);

		btnBack = new JButton("Back");
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnBack.setBounds(319, 402, 132, 35);
		contentPane.add(btnBack);
		
		
	}

	
	/**
	 * Create the events
	 */
	private void createEvents(SignUpPage frame) {
		
		//go back to login page when clicking cancel button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userType.equals("C")) {
					Main.showLoginPage();
				}
				Main.hideSignupPage();
			}
		});
		
		//Create account when clicking Create button
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup_status = parse_signup(usernameField.getText(), passwordField.getText(),
						passwordField_1.getText(), uidField.getText());

				if (signup_status) {
					try {
						//create new user according to the type field content
						User user;
						if(typeField.getText().equals("Admin")) {
							user= new Admin(usernameField.getText(), passwordField.getText(),
									uidField.getText());
						}else if(typeField.getText().equals("Librarian")) {
							user= new Librarian(usernameField.getText(), passwordField.getText(),
									uidField.getText());
						}else {
							user= new Customer(usernameField.getText(), passwordField.getText(),
								uidField.getText());
						}
						UserStorage userStorage = new UserStorage();
						userStorage.save(user);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//If user is an admin or librarian, change completion message accordingly
					if(userType.equals("A") || userType.equals("L")) {
						completionMessage = "Add account successfully.";
					}else {
						completionMessage = "Sign up successfully.";
					}
					JOptionPane.showMessageDialog(contentPane, completionMessage);
					//If user is an customer, show login page, otherwise just hide sign up page
					if(userType.equals("C") ) {
						Main.showLoginPage();
					}
					Main.hideSignupPage();
				} else {
					JOptionPane.showMessageDialog(contentPane, completionMessage);
					uidField.setText("");
					usernameField.setText("");
					passwordField.setText("");
					passwordField_1.setText("");
					typeField.setText("");
				}
			}
		});
	}
	
	/**
	 * Parse user information from UserLoginInfo.txt
	 * Return ture: uid, username, passwords are not empty, uid and username doesn't exist and passwords are same
	 * Return false: if one of them is incorrect
	 */
	private boolean parse_signup(String name, String password_1, String password_2, String uid) {
		UserStorage userStorage = new UserStorage();
		if(userStorage.userIDExists(uid)){
			System.out.print("You already have an account.");
			completionMessage = "You already have an account.";
			return false;
		}else if(userStorage.userNameExists(name)){
			System.out.print("Username: " + name + " have been registered. Try another user name.");
			completionMessage = "Username: " + name + " have been registered. Try another user name.";
			return false;
		}
		
		if(name.isEmpty()){
			System.out.print("Username can not be empty.");
			completionMessage = "Username can not be empty."; 
			return false;
		}else if(uid.isEmpty()){
			System.out.print("UID can not be empty.");
			completionMessage = "UID can not be empty."; 
			return false;
		}else if(password_1.isEmpty()){
			System.out.print("Password can not be empty.");
			completionMessage = "Password can not be empty."; 
			return false;
		}
		
		if (!password_1.equals(password_2)) {
			System.out.print("Passwords don't match. Try again.");
			completionMessage = "Passwords don't match. Try again."; 
			return false;
		}
		
		if(userType.equals("A")) {
			if((!typeField.getText().equals("Admin"))&&(!typeField.getText().equals("Librarian"))&&(!typeField.getText().equals("Customer"))) {
				System.out.print("Wrong type. Try again.");
				completionMessage = "Wrong type. Try again."; 
				return false;
			}
		}
		return true;
		
	}
	
	public void setUserType(String type) {
		userType = type;
	}
	
	/*
	 * adjust page content according to the user type 
	 */
	public void adjustPageContent() {
		if(userType.equals("A")) {
			contentPane.add(typeLabel);
			contentPane.add(typeField);
		} else {
			contentPane.remove(typeLabel);
			contentPane.remove(typeField);
		}
		uidField.setText("");
		usernameField.setText("");
		passwordField.setText("");
		passwordField_1.setText("");
		typeField.setText("");
	}
}
