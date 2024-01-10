package com.raf.clientapplication.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.clientapplication.model.User;
import com.raf.clientapplication.model.UserTableModel;
import com.raf.clientapplication.restclient.UserServiceRestClient;
import com.raf.clientapplication.restclient.dto.ManagerCreateDto;
import com.raf.clientapplication.restclient.dto.TrainingDto;
import com.raf.clientapplication.restclient.dto.UserBanDto;
import com.raf.clientapplication.restclient.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class AdminView extends JPanel {

    private JLabel emailLabel;
    private JLabel passwordLabel;
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
    private JButton registerButton;
    private JButton banButton;
    private JTable userTable;


    private UserServiceRestClient userServiceRestClient;
    private UserTableModel userTableModel;

    private ObjectMapper objectMapper = new ObjectMapper();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AdminView() throws IllegalAccessException, NoSuchMethodException, IOException {

        userServiceRestClient = new UserServiceRestClient();
        userTableModel = new UserTableModel();
        userTable = new JTable(userTableModel);

        banButton = new JButton("Ban/Unban");
        userTable = new JTable(userTableModel);
        registerPanel = createRegisterPanel();
        JScrollPane scrollPane = new JScrollPane(userTable);
        this.add(scrollPane, BorderLayout.NORTH);
        add(banButton);
        add(registerPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Set your preferred size
        //setLocationRelativeTo(null); // Center the frame
        setVisible(true);
        init();

        banButton.addActionListener((event) -> {
            UserDto u = userTableModel.getUserListDtos().getContent().get(userTable.getSelectedRow());
            System.out.println(u);
            try {
                if(u.getRole().equals("ROLE_ADMIN"))
                    return;
                userServiceRestClient.banUser(new UserBanDto(u.getUsername(),!u.isBanned()),
                        u.getRole().equals("ROLE_MANAGER") ? "manager" : "client");
                refreshTable(userServiceRestClient.getUsers());
                u.setBanned(!u.isBanned());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

        registerButton.addActionListener((event) -> {
            System.out.println(dateOfBirthInput.getText());
            try {
                userServiceRestClient.registerManager(
                        new ManagerCreateDto(
                                "Gym1",
                                LocalDate.parse(dateOfBirthInput.getText(), formatter),
                                emailInput1.getText(),
                                firstNameInput.getText(),
                                lastNameInput.getText(),
                                usernameInput.getText(),
                                String.valueOf(passwordInput1.getPassword()),
                                LocalDate.now()

                        )
                );
                refreshTable(userServiceRestClient.getUsers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void refreshTable(List<UserDto> users) {
        userTableModel.setNumRows(0);
        userTableModel.fireTableDataChanged();
        for (UserDto userDto : users) {
            userTableModel.addRow(new Object[]{userDto.getId(), userDto.getEmail(), userDto.getFirstName(),
                    userDto.getLastName(), userDto.getUsername(), userDto.getRole(), userDto.isBanned()});
        }
    }

    private void init() throws IOException {
        List<UserDto> userDtos = userServiceRestClient.getUsers();
        refreshTable(userDtos);
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
