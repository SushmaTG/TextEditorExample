package com.architectblogs;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener {

	// creat fields for login : username, password
	JLabel userLabel = new JLabel("USERNAME: ");
	JTextField userTextField = new JTextField();
	JLabel passwordLabel = new JLabel("PASSWORD: ");
	JPasswordField passTextField = new JPasswordField();
	JPanel loginPanel = new JPanel(new GridLayout(3, 2));
	JPanel panel = new JPanel();
	// Add login panel, and login page level panel, add all of these to the cardLayout, buttons for login, register
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	CardLayout cl;

	// add these attributes to the cardlayout
	Login(){
		setLayout(new CardLayout());
		loginPanel.add(userLabel);
		loginPanel.add(userTextField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passTextField);
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		panel.add(loginPanel);
		add("login", panel);
		loginPanel.add(loginButton);
		loginPanel.add(registerButton);
		cl = (CardLayout) getLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		add(new Register(), "Register");
		// show the register class instance when pressed
		cl.show(this, "Register");
		add(new Login(), "login");
		if(e.getSource() == loginButton && passTextField.getPassword().length > 0 
				&& userTextField.getText().length() > 0){
//			 String pass = new String(passTextField.getPassword());
//			 String confirm = new String(confirmPasswordField.getPassword());
//			 // check if the pass == confirm 
//			 if(pass.equals(confirm)){
				 // export password to text file
			cl.show(this, "login");
			// in register page we first check if pass and confirm are equal and then proceed towards saving the username and password
			// in login page we already have username and password saved in document we just have to compare if they match and allow to access the filebrowser
			
				 try {
					BufferedReader input = new BufferedReader(new FileReader("password.txt"));
					String pass = null;
					String line = input.readLine();
						while(line != null){
							StringTokenizer st = new StringTokenizer(line);
							if(userTextField.getText().equals(st.nextToken())){
								pass = st.nextToken();
							}
							line = input.readLine();
						}
						input.close();
						// Encrypt the password with SHA-256 using MessageDigest
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(new String (passTextField.getPassword()).getBytes());
						byte digest[] = md.digest();
						// hashing algorithm
						StringBuffer sb = new StringBuffer();
						for(int i = 0; i < digest.length; i++){
							sb.append(Integer.toString((digest[i] & 0xFF) + 0x100, 16).substring(1)); // hashing algorithm
						}
						if(pass.equals(sb.toString())){
							add(new FileBrowser(userTextField.getText()), "fb");
							cl.show(this, "fb");
						}
//						// write the hash coded password onto the file along with username
//						BufferedWriter output = new BufferedWriter(new FileWriter("password.txt", true	));
//						output.write(userTextField.getText() + " " + sb.toString() + "\n");
//						output.close();
//						Login login = (Login) getParent();
//						login.cl.show(login, "login");
					} 
				 catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}else{
			cl.show(new Register(), "Register");
		}
	}

	public static void main(String[] args) {
		// create frame and add the login instance
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}

}










