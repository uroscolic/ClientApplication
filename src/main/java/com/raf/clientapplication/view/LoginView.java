package com.raf.clientapplication.view;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.restclient.UserServiceRestClient;
import com.raf.clientapplication.restclient.dto.ClientCreateDto;

public class LoginView extends JPanel {

	private JPanel loginPanel;
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JTextField emailInput;
	private JPasswordField passwordInput;

	private JPanel registerPanel;
	private JTextField emailInput1;
	private JPasswordField passwordInput1;
	private JLabel dateOfBirthLabel;
	private JTextField dateOfBirthInput;
	private JLabel firstNameLabel;
	private JTextField firstNameInput;
	private JLabel lastNameLabel;
	private JTextField lastNameInput;
	private JLabel usernameLabel;
	private JTextField usernameInput;
	private JButton loginButton;
	private JButton registerButton;

	private UserServiceRestClient userServiceRestClient = new UserServiceRestClient();

	private ObjectMapper objectMapper = new ObjectMapper();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public LoginView() {

		loginPanel = createLoginPanel();
		registerPanel = createRegisterPanel();

		add(loginPanel);
		add(registerPanel);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(400, 300);
		setVisible(true);
		loginButton.addActionListener((event) -> {

			try {
				String token = userServiceRestClient
					.login(emailInput.getText(), String.valueOf(passwordInput.getPassword()));
				this.setVisible(false);

				ClientApplication.getInstance().setToken(token);
				System.out.println(token);
				ReservationFrame.getInstance().setVisible(true);
				ClientApplication.getInstance().setVisible(false);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		registerButton.addActionListener((event) -> {
			System.out.println(dateOfBirthInput.getText());
			try {
				userServiceRestClient.registerClient(
					new ClientCreateDto(
						emailInput1.getText(),
						firstNameInput.getText(),
						lastNameInput.getText(),
						usernameInput.getText(),
						String.valueOf(passwordInput1.getPassword()),
						LocalDate.parse(dateOfBirthInput.getText(), formatter)
					)
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private JPanel createLoginPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		emailLabel = new JLabel("Email: ");
		passwordLabel = new JLabel("Password: ");

		emailInput = new JTextField(20);
		passwordInput = new JPasswordField(20);

		panel.add(emailLabel);
		panel.add(emailInput);
		panel.add(passwordLabel);
		panel.add(passwordInput);

		loginButton = new JButton("Login");
		panel.add(loginButton);

		return panel;
	}

	private JPanel createRegisterPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		emailLabel = new JLabel("Email: ");
		passwordLabel = new JLabel("Password: ");
		dateOfBirthLabel = new JLabel("Date of birth: ");
		firstNameLabel = new JLabel("First name: ");
		lastNameLabel = new JLabel("Last name: ");
		usernameLabel = new JLabel("Username: ");

		emailInput1 = new JTextField(20);
		passwordInput1 = new JPasswordField(20);
		dateOfBirthInput = new JTextField(20);
		firstNameInput = new JTextField(20);
		lastNameInput = new JTextField(20);
		usernameInput = new JTextField(20);

		panel.add(emailLabel);
		panel.add(emailInput1);
		panel.add(passwordLabel);
		panel.add(passwordInput1);

		JPanel dateOfBirthPanel = new JPanel(new FlowLayout());
		dateOfBirthPanel.add(dateOfBirthLabel);
		dateOfBirthPanel.add(dateOfBirthInput);
		JPanel firstNamePanel = new JPanel(new FlowLayout());
		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameInput);
		JPanel lastNamePanel = new JPanel(new FlowLayout());
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameInput);
		JPanel usernamePanel = new JPanel(new FlowLayout());
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameInput);

		registerButton = new JButton("Register");
		panel.add(dateOfBirthPanel);
		panel.add(firstNamePanel);
		panel.add(lastNamePanel);
		panel.add(usernamePanel);
		panel.add(registerButton);

		return panel;
	}

}
