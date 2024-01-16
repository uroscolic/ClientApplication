package com.raf.clientapplication.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.model.NotificationTableModel;
import com.raf.clientapplication.model.NotificationTypeTableModel;
import com.raf.clientapplication.restclient.NotificationServiceRestClient;
import com.raf.clientapplication.restclient.dto.NotificationDto;
import com.raf.clientapplication.restclient.dto.NotificationTypeDto;
import com.raf.clientapplication.restclient.dto.NotificationTypeUpdateDto;
import com.raf.clientapplication.restclient.dto.UserDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class NotificationView extends JPanel {

    private JTable notificationTable;
    private NotificationTableModel notificationTableModel;
    private NotificationServiceRestClient notificationServiceRestClient;
    private JTable notificationTypeTable;
    private NotificationTypeTableModel notificationTypeTableModel;
    private JTextField textField;
    private JLabel textLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JTextField oldNameField;
    private JLabel oldNameLabel;
    private JButton button;
    private JButton backButton;

    public NotificationView() throws IllegalAccessException, NoSuchMethodException, IOException {
        notificationServiceRestClient = new NotificationServiceRestClient();
        notificationTableModel = new NotificationTableModel();
        notificationTable = new JTable(notificationTableModel);
        notificationTypeTableModel = new NotificationTypeTableModel();
        notificationTypeTable = new JTable(notificationTypeTableModel);
        textField = new JTextField(20);
        nameField = new JTextField(20);
        textLabel = new JLabel("Text");
        nameLabel = new JLabel("Name");
        oldNameField = new JTextField(20);
        oldNameLabel = new JLabel("Old name");
        button = new JButton("Add/Update");
        backButton = new JButton("Back");
        JScrollPane scrollPane = new JScrollPane(notificationTable);
        JScrollPane scrollPane2 = new JScrollPane(notificationTypeTable);
        this.add(scrollPane);

        if(ClientApplication.getInstance().getRole().equals("ROLE_ADMIN")) {
            this.add(scrollPane2);

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(oldNameLabel);
            panel.add(oldNameField);
            panel.add(textLabel);
            panel.add(textField);
            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(button);
            panel.add(backButton);
            this.add(panel);

        }
        else
            this.add(backButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 1200); // Set your preferred size
        //setLocationRelativeTo(null); // Center the frame
        setVisible(true);
        init();

        button.addActionListener(e -> {

            NotificationTypeUpdateDto notificationTypeUpdateDto = new NotificationTypeUpdateDto();
            notificationTypeUpdateDto.setName(nameField.getText());
            notificationTypeUpdateDto.setText(textField.getText());
            notificationTypeUpdateDto.setOldName(oldNameField.getText());
            NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
            notificationTypeDto.setName(nameField.getText());
            notificationTypeDto.setText(textField.getText());
            if(!oldNameField.getText().equals("")) {
                try {
                    notificationServiceRestClient.updateNotificationType(notificationTypeUpdateDto);
                    refreshTable1(notificationServiceRestClient.getNotificationTypes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(!nameField.getText().equals("") && !textField.getText().equals("")) {
                try {
                    notificationServiceRestClient.addNotificationType(notificationTypeDto);
                    refreshTable1(notificationServiceRestClient.getNotificationTypes());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                init();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        backButton.addActionListener(e -> {
            NotificationFrame.getInstance().setVisible(false);
            ReservationFrame.getInstance().setVisible(true);
        });

    }

    private void refreshTable(List<NotificationDto> notifications) {
        notificationTableModel.setNumRows(0);
        notificationTableModel.fireTableDataChanged();
        for (NotificationDto notificationDto : notifications) {
            String text = notificationDto.getNotificationTypeDto().getText();
            for(int i = 0; i < notificationDto.getParameters().size(); i++)
                text = text.replace(i + 1 +"s", notificationDto.getParameters().get(i));
            notificationTableModel.addRow(new Object[]{notificationDto.getId(), notificationDto.getNotificationTypeDto().getName(),
                    text, notificationDto.getParameters(), notificationDto.getEmail()});
        }
    }
    private void refreshTable1(List<NotificationTypeDto> notificationTypes) {
        notificationTypeTableModel.setNumRows(0);
        notificationTypeTableModel.fireTableDataChanged();
        for (NotificationTypeDto notificationTypeDto : notificationTypes) {
            notificationTypeTableModel.addRow(new Object[]{notificationTypeDto.getId(), notificationTypeDto.getText(),
                    notificationTypeDto.getName()});
        }
    }

    private void init() throws IOException {
        List<NotificationDto> notificationDtos = notificationServiceRestClient.getNotifications("a");
        if(ClientApplication.getInstance().getRole().equals("ROLE_ADMIN")) {
            List<NotificationTypeDto> notificationTypeDtos = notificationServiceRestClient.getNotificationTypes();
            refreshTable1(notificationTypeDtos);
        }
        refreshTable(notificationDtos);
    }

}
