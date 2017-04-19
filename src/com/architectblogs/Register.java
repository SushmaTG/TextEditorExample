package com.architectblogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener {
	// create the page after the register button pressed
	//JLabel label = new JLabel("Register Page");
	JLabel userLabel = new JLabel("Choose your username");
	JTextField userField = new JTextField();
	JLabel passwordLabel = new JLabel("Choose a password");
	JPasswordField passwordField = new JPasswordField();
	JLabel confirmPasswordLabel = new JLabel("Confirm your password");
	JPasswordField confirmPasswordField = new JPasswordField();
	JButton registerButton = new JButton("Register");
	JButton backButton = new JButton("Back");
	
	public Register(){
		// once the register button is pressed create a register panel and add all the components to the panel
		JPanel register = new JPanel();
		register.setLayout(new GridLayout(5, 4));
		//register.add(label);
		register.add(userLabel);
		register.add(userField);
		register.add(passwordLabel);
		register.add(passwordField);
		register.add(confirmPasswordLabel);
		register.add(confirmPasswordField);
		register.add(registerButton);
		register.add(backButton);
		registerButton.addActionListener(this);
		backButton.addActionListener(this);
		add(register);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check if the username and password are > 0 and get the password string and compare with saved password
		if(e.getSource() == registerButton && passwordField.getPassword().length > 0 
				&& userField.getText().length() > 0){
			 String pass = new String(passwordField.getPassword());
			 String confirm = new String(confirmPasswordField.getPassword());
			 // check if the pass == confirm 
			 if(pass.equals(confirm)){
				 // export password to text file
				 try {
					BufferedReader input = new BufferedReader(new FileReader("password.txt"));
					String line = input.readLine();
						while(line != null){
							StringTokenizer st = new StringTokenizer(line);
							if(userField.getText().equals(st.nextToken())){
								System.out.println("User already exists");
								return;
							}
							line = input.readLine();
						}
						input.close();
						// Encrypt the password with SHA-256 using MessageDigest
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(pass.getBytes());
						byte digest[] = md.digest();
						// hashing algorithm
						StringBuffer sb = new StringBuffer();
						for(int i = 0; i < digest.length; i++){
							sb.append(Integer.toString((digest[i] & 0xFF) + 0x100, 16).substring(1)); // hashing algorithm
						}
						// write the hash coded password onto the file along with username
						BufferedWriter output = new BufferedWriter(new FileWriter("password.txt", true	));
						output.write(userField.getText() + " " + sb.toString() + "\n");
						output.close();
						Login login = (Login) getParent();
						login.cl.show(login, "login");
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
			 }
		}
		if(e.getSource() == backButton){
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
	}
}
