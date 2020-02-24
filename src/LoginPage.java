import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Storage.UserStorage;
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
import java.io.IOException;
import java.util.Scanner;
//import com.jgoodies.forms.factories.DefaultComponentFactory;


public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField passwordField;
    private JButton btnLogin;
    JButton btnSignUp;
    private String NAME;
    private String PASSWORD;
    private int login_status = 1;
    private JLabel lblNeedAnAccount;
    private JButton btnCancel;


	
	/**
	 * Create the frame.
	 */
	public LoginPage() {
		initComponents();
		this.setLocationRelativeTo(null);			//set screen to center
		createEvents(this);
	}

	
	/**
	 * Create and initialize components
	 */
	private void initComponents() {
		setTitle("Library Management System");			//set title for the window
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 492);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(119, 136, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Username");
		lblName.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		lblName.setBounds(93, 121, 120, 29);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		lblPassword.setBounds(93, 203, 133, 29);
		contentPane.add(lblPassword);
		
		nameField = new JTextField("");	
		nameField.setBounds(93, 146, 400, 48);
		contentPane.add(nameField);
		//nameField.setText("");
		nameField.setColumns(10);
		
		btnLogin = new JButton("Log In");
		btnLogin.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setActionCommand("Log in");
		btnLogin.setBounds(134, 306, 133, 36);
		contentPane.add(btnLogin);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setForeground(Color.RED);
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.setFont(new Font("Lantinghei SC", Font.BOLD | Font.ITALIC, 17));
		btnSignUp.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSignUp.setBounds(319, 374, 102, 36);
		contentPane.add(btnSignUp);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(93, 228, 400, 48);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		lblNeedAnAccount = new JLabel("Need an account?");
		lblNeedAnAccount.setFont(new Font("Lantinghei SC", Font.BOLD, 17));
		lblNeedAnAccount.setBounds(177, 381, 156, 23);
		contentPane.add(lblNeedAnAccount);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancel.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnCancel.setBounds(319, 306, 133, 36);
		contentPane.add(btnCancel);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Hiragino Mincho Pro", Font.BOLD, 40));
		lblLogin.setBounds(229, 44, 160, 55);
		contentPane.add(lblLogin);
	}
	
	
	/**
	 * Create the events
	 */
	private void createEvents(LoginPage frame) {
		
		//go to signup page when clicking Signup button
	    btnSignUp.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
				Main.showSignupPage("C");
				Main.hideLoginPage();
		    }
		});
	    
	    //close windows when clicking Cancel button
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.hideLoginPage();
				frame.dispose();
				System.exit(0);
			}
		});
	    
	    //show corrsponding message when clicking login button
	    btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String completionMessage = "";
			    login_status = parse(nameField.getText(),passwordField.getText());
			    String user = nameField.getText();
			    nameField.setText("");
			    passwordField.setText("");
			    
			    if (login_status == 0) {
			    	completionMessage = "Welcome, " + user;
			    }else if(login_status == 1) {
			    	completionMessage = "Incorrect username or password";
			    }else if(login_status == 2) {
			    	completionMessage = "Account has been disabled, please contact library staff.";
			    }
			    
			    JOptionPane.showMessageDialog(contentPane, completionMessage);
			    if (login_status == 0) {
			    	Main.createUserPage(user);
			    	Main.hideLoginPage();
			    }
			}
		});
	}
   
	
	/**
	 * Read user info from file
	 * returns 0 if good login
	 * returns 1 if bad login
	 * returns 2 if acc disabled
	 */
    private int parse (String name, String pass) {
    	UserStorage userStorage = new UserStorage();
    	try {
    		User user = userStorage.retrieve(name, UserStorage.Identifier.USERNAME);
    		if(user.checkPassword(pass)) {
    			if(user.getAccDisabled()) {
    				return 2;
    			}
    			return 0;
    		} 
    		return 1;
    	} catch (Exception e) {
    		System.out.println("The user does not exist.");
        	return 1;
    	}
	}
}
