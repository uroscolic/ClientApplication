package com.raf.clientapplication.view;

import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.model.User;
import com.raf.clientapplication.restclient.UserServiceRestClient;
import com.raf.clientapplication.restclient.dto.UserDto;
import com.raf.clientapplication.restclient.dto.UserUpdateDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class EditView extends JPanel {
    private JPanel panel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JLabel firstNameLabel;
    private JTextField firstNameInput;
    private JLabel lastNameLabel;
    private JTextField lastNameInput;
    private JLabel usernameLabel;
    private JTextField usernameInput;
    private JButton confirmButton;
    private UserServiceRestClient userServiceRestClient;

    public EditView() throws IOException {
        userServiceRestClient = new UserServiceRestClient();
        panel = createRegisterPanel();
        add(panel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(400, 300);
        setVisible(true);
        UserDto u = userServiceRestClient.getUserById(ClientApplication.getInstance().getId(),
                ClientApplication.getInstance().getRole().equals("ROLE_CLIENT") ? "client" : "manager");
        emailInput.setText(u.getEmail());
        usernameInput.setText(u.getUsername());
        firstNameInput.setText(u.getFirstName());
        lastNameInput.setText(u.getLastName());

        confirmButton.addActionListener((event) -> {
            UserUpdateDto u1 = new UserUpdateDto();
            u1.setOldUsername(u.getUsername());
            u1.setId(u.getId());
            u1.setEmail(emailInput.getText());
            u1.setUsername(usernameInput.getText());
            u1.setFirstName(firstNameInput.getText());
            u1.setLastName(lastNameInput.getText());
            u1.setPassword(String.valueOf(passwordInput.getPassword()));
            try {
                userServiceRestClient.updateUser(u1, ClientApplication.getInstance().getRole().equals("ROLE_CLIENT") ? "client" : "manager");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        emailLabel = new JLabel("Email: ");
        passwordLabel = new JLabel("Password: ");
        firstNameLabel = new JLabel("First name: ");
        lastNameLabel = new JLabel("Last name: ");
        usernameLabel = new JLabel("Username: ");


        firstNameInput = new JTextField(20);
        lastNameInput = new JTextField(20);
        usernameInput = new JTextField(20);
        passwordInput = new JPasswordField(20);
        emailInput = new JTextField(20);

        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(emailLabel);
        emailPanel.add(emailInput);
        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);

        JPanel firstNamePanel = new JPanel(new FlowLayout());
        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstNameInput);
        JPanel lastNamePanel = new JPanel(new FlowLayout());
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameInput);
        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameInput);

        confirmButton = new JButton("Confirm");
        panel.add(emailPanel);
        panel.add(passwordPanel);
        panel.add(firstNamePanel);
        panel.add(lastNamePanel);
        panel.add(usernamePanel);
        panel.add(confirmButton);


        return panel;
    }
}
