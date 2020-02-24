import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Storage.UserStorage;
import Users.User;

import javax.swing.JTextField;

public class ChangePassword extends JFrame{

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField currentPassField;
	private JTextField newPassField;
	private JTextField confirmNewPassField;
	private JLabel lblUsername;
	private JLabel lblCurrentPassword;
	private JLabel lblNewPassword;
	private JLabel lblConfirmPassword;
	private JButton btnCancel;
	private JButton btnSubmit;
	private String completionMessage;
	private boolean changePass_status = false;


	/**
	 * Create the application.
	 */
	public ChangePassword() {
		initialize();
		this.setLocationRelativeTo(null);
		createEvents(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 558, 492);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(119, 136, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(90, 113, 400, 41);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		currentPassField = new JPasswordField();
		currentPassField.setBounds(90, 178, 400, 41);
		contentPane.add(currentPassField);
		
		newPassField = new JPasswordField();
		newPassField.setBounds(90, 244, 400, 41);
		contentPane.add(newPassField);
		
		confirmNewPassField = new JPasswordField();
		confirmNewPassField.setBounds(90, 312, 400, 41);
		contentPane.add(confirmNewPassField);
		
		JLabel ChangePassword = new JLabel("Change Password");
		ChangePassword.setFont(new Font("Hiragino Mincho Pro", Font.BOLD, 40));
		ChangePassword.setBounds(120, 35, 349, 55);
		contentPane.add(ChangePassword);
		
		lblUsername = new JLabel("Current Username");
		lblUsername.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblUsername.setBounds(90, 97, 136, 16);
		contentPane.add(lblUsername);
		
		lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblCurrentPassword.setBounds(90, 160, 176, 18);
		contentPane.add(lblCurrentPassword);
		
		lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblNewPassword.setBounds(90, 227, 136, 16);
		contentPane.add(lblNewPassword);
		
		lblConfirmPassword = new JLabel("Confirm New Password");
		lblConfirmPassword.setFont(new Font("Lantinghei SC", Font.PLAIN, 15));
		lblConfirmPassword.setBounds(90, 296, 215, 16);
		contentPane.add(lblConfirmPassword);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setToolTipText("");
		btnSubmit.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnSubmit.setBounds(139, 385, 132, 35);
		btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnSubmit);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("");
		btnCancel.setFont(new Font("Lantinghei SC", Font.PLAIN, 16));
		btnCancel.setBounds(320, 385, 132, 35);
		btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnCancel);		
		
	}
	
	private void createEvents(ChangePassword frame) {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		//Change user password when clicking submit button
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				changePass_status = parse_signup(nameField.getText(), currentPassField.getText(), newPassField.getText(), confirmNewPassField.getText());
				
				UserStorage userStorage = new UserStorage();
				User user = userStorage.retrieve(nameField.getText(), UserStorage.Identifier.USERNAME);
				
					if (changePass_status){
						try{

							user.setPassword(newPassField.getText());
							userStorage.save(user);
												
							completionMessage = "Your password is now reset.";
				
							
						} catch (Exception e){
							System.out.println("Error!!");
						}
						JOptionPane.showMessageDialog(contentPane, completionMessage);
						frame.setVisible(false);
						frame.dispose();
						
						
					} else {
						JOptionPane.showMessageDialog(contentPane, completionMessage);
					}
					
			}
			
		});
	}
	
	
	
	/**
	 * Parse user information from UserLoginInfo.txt
	 * Return ture: username and current password are correct, and newpasswords is different and correct
	 * Return false: if one of them is incorrect
	 */
	private boolean parse_signup(String name, String currentPass, String newPass, String confirmNewPass) {
		UserStorage userStorage = new UserStorage();
		
		User user = userStorage.retrieve(name, UserStorage.Identifier.USERNAME);
		
		if(name.isEmpty() || currentPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()){
			System.out.print("Somewhere is empty. Please enter all the informations.");
			completionMessage = "Somewhere is empty. Please enter all the informations."; 
			return false;
		}
		
		if(!user.checkPassword(currentPass)) {
			System.out.print("Your currrent password is incorrent.");
			completionMessage = "Your current password is incorrent."; 
			return false;
		} 
		
		if(newPass.equals(currentPass)) {
			System.out.print("Please enter a different password.");
			completionMessage = "Please enter a different password."; 
			return false;
		}
		
		if (!newPass.equals(confirmNewPass)) {
			System.out.print("New passwords don't match. Try again.");
			completionMessage = "New passwords don't match. Try again."; 
			return false;
		}

		return true;
		
	}
	
}
