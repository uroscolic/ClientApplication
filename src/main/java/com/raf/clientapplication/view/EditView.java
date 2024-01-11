package com.raf.clientapplication.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.restclient.ReservationServiceRestClient;
import com.raf.clientapplication.restclient.UserServiceRestClient;
import com.raf.clientapplication.restclient.dto.GymDto;
import com.raf.clientapplication.restclient.dto.GymUpdateDto;
import com.raf.clientapplication.restclient.dto.UserDto;
import com.raf.clientapplication.restclient.dto.UserUpdateDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
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
    private JButton confirmGymChangesButton;
    private JButton backButton;
    private JLabel descriptionLabel;
    private JTextArea descriptionInput;
    private JLabel numberOfPersonalTrainersLabel;
    private JTextField numberOfPersonalTrainersInput;
    private JLabel openingTimeLabel;
    private JTextField openingTimeInput;
    private JLabel closingTimeLabel;
    private JTextField closingTimeInput;
    private JLabel trainingDurationLabel;
    private JTextField trainingDurationInput;
    private JLabel freeTrainingPerXBookedLabel;
    private JTextField freeTrainingPerXBookedInput;
    private JLabel trainingTypesLabel;
    private JTextField trainingTypesInput;
    private JLabel oldTrainingsTypeLabel;
    private JTextField oldTrainingsTypeInput;
    private JRadioButton isGroupRadioButton;
    private JLabel price;
    private JTextField priceInput;
    private UserServiceRestClient userServiceRestClient;
    private ReservationServiceRestClient reservationServiceRestClient;

    public EditView() throws IOException {
        userServiceRestClient = new UserServiceRestClient();
        reservationServiceRestClient = new ReservationServiceRestClient();
        panel = createRegisterPanel();
        add(panel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(400, 1000);
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
            if(passwordInput.getPassword().length == 0)
                u1.setPassword(u.getPassword());
            else
                u1.setPassword(String.valueOf(passwordInput.getPassword()));
            try {
                userServiceRestClient.updateUser(u1, ClientApplication.getInstance().getRole().equals("ROLE_CLIENT") ? "client" : "manager");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        backButton.addActionListener((event)->{
                EditFrame.getInstance().setVisible(false);
                ReservationFrame.getInstance().setVisible(true);
            try {
                ReservationFrame.reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        GymDto gymDto = reservationServiceRestClient.getGym();
        descriptionInput.setText(gymDto.getDescription());
        numberOfPersonalTrainersInput.setText(String.valueOf(gymDto.getNumberOfPersonalTrainers()));
        openingTimeInput.setText(String.valueOf(gymDto.getOpeningTime()));
        closingTimeInput.setText(String.valueOf(gymDto.getClosingTime()));
        trainingDurationInput.setText(String.valueOf(gymDto.getTrainingDuration()));
        freeTrainingPerXBookedInput.setText(String.valueOf(gymDto.getFreeTrainingPerXBooked()));

        confirmGymChangesButton.addActionListener((event)->{
            GymUpdateDto gymUpdateDto = new GymUpdateDto();
            GymUpdateDto.TrainingTypeDto trainingTypeDto = new GymUpdateDto.TrainingTypeDto();
            try {
                gymUpdateDto.setId(gymDto.getId());
                gymUpdateDto.setDescription(descriptionInput.getText());
                gymUpdateDto.setNumberOfPersonalTrainers(Integer.parseInt(numberOfPersonalTrainersInput.getText()));
                gymUpdateDto.setOpeningTime(LocalTime.parse(openingTimeInput.getText()));
                gymUpdateDto.setClosingTime(LocalTime.parse(closingTimeInput.getText()));
                gymUpdateDto.setTrainingDuration(Duration.parse(trainingDurationInput.getText()));
                gymUpdateDto.setFreeTrainingPerXBooked(Integer.parseInt(freeTrainingPerXBookedInput.getText()));
                if(oldTrainingsTypeInput.getText().isEmpty())
                    trainingTypeDto.setOldType("GDuaduabubs119f16FASf8haFoiaof9GF*wGF9wafwbyaWFv7a(FWba8f");
                else
                    trainingTypeDto.setOldType(oldTrainingsTypeInput.getText());
                trainingTypeDto.setType(trainingTypesInput.getText());
                if(priceInput.getText().isEmpty())
                    trainingTypeDto.setPrice(-1L);
                else
                    trainingTypeDto.setPrice(Long.valueOf(priceInput.getText()));
                trainingTypeDto.setGroup(isGroupRadioButton.isSelected());
                if(!(trainingTypeDto.getType().isEmpty() && priceInput.getText().isEmpty()))
                    gymUpdateDto.getTrainingTypes().add(trainingTypeDto);
                reservationServiceRestClient.updateGym(gymUpdateDto);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }


        });
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        backButton = new JButton("Back");
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


        descriptionLabel = new JLabel("Description: ");
        descriptionInput = new JTextArea(5, 20);
        numberOfPersonalTrainersLabel = new JLabel("Number of personal trainers: ");
        numberOfPersonalTrainersInput = new JTextField(20);
        openingTimeLabel = new JLabel("Opening time: ");
        openingTimeInput = new JTextField(20);
        closingTimeLabel = new JLabel("Closing time: ");
        closingTimeInput = new JTextField(20);
        trainingDurationLabel = new JLabel("Training duration: ");
        trainingDurationInput = new JTextField(20);
        freeTrainingPerXBookedLabel = new JLabel("Free training per x booked: ");
        freeTrainingPerXBookedInput = new JTextField(20);
        trainingTypesLabel = new JLabel("New Training Type: ");
        trainingTypesInput = new JTextField(20);
        oldTrainingsTypeLabel = new JLabel("Old Training Type: ");
        oldTrainingsTypeInput = new JTextField(20);
        isGroupRadioButton = new JRadioButton("Is group");
        price = new JLabel("Price: ");
        priceInput = new JTextField(20);
        confirmGymChangesButton = new JButton("Confirm gym changes");

        JPanel descriptionPanel = new JPanel(new FlowLayout());
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionInput);
        JPanel numberOfPersonalTrainersPanel = new JPanel(new FlowLayout());
        numberOfPersonalTrainersPanel.add(numberOfPersonalTrainersLabel);
        numberOfPersonalTrainersPanel.add(numberOfPersonalTrainersInput);
        JPanel openingTimePanel = new JPanel(new FlowLayout());
        openingTimePanel.add(openingTimeLabel);
        openingTimePanel.add(openingTimeInput);
        JPanel closingTimePanel = new JPanel(new FlowLayout());
        closingTimePanel.add(closingTimeLabel);
        closingTimePanel.add(closingTimeInput);
        JPanel trainingDurationPanel = new JPanel(new FlowLayout());
        trainingDurationPanel.add(trainingDurationLabel);
        trainingDurationPanel.add(trainingDurationInput);
        JPanel freeTrainingPerXBookedPanel = new JPanel(new FlowLayout());
        freeTrainingPerXBookedPanel.add(freeTrainingPerXBookedLabel);
        freeTrainingPerXBookedPanel.add(freeTrainingPerXBookedInput);
        JPanel trainingTypesPanel = new JPanel(new FlowLayout());
        trainingTypesPanel.add(trainingTypesLabel);
        trainingTypesPanel.add(trainingTypesInput);
        JPanel oldTrainingsTypePanel = new JPanel(new FlowLayout());
        oldTrainingsTypePanel.add(oldTrainingsTypeLabel);
        oldTrainingsTypePanel.add(oldTrainingsTypeInput);
        JPanel isGroupPanel = new JPanel(new FlowLayout());
        isGroupPanel.add(isGroupRadioButton);
        JPanel pricePanel = new JPanel(new FlowLayout());
        pricePanel.add(price);
        pricePanel.add(priceInput);
        if(ClientApplication.getInstance().getRole().equals("ROLE_MANAGER")) {
            panel.add(descriptionPanel);
            panel.add(numberOfPersonalTrainersPanel);
            panel.add(openingTimePanel);
            panel.add(closingTimePanel);
            panel.add(trainingDurationPanel);
            panel.add(freeTrainingPerXBookedPanel);
            panel.add(trainingTypesPanel);
            panel.add(oldTrainingsTypePanel);
            panel.add(isGroupPanel);
            panel.add(pricePanel);
        }

        panel.add(confirmGymChangesButton);
        panel.add(backButton);


        return panel;
    }
}
