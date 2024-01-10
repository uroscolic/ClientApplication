package com.raf.clientapplication.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.model.ReservationTableModel;
import com.raf.clientapplication.restclient.ReservationServiceRestClient;
import com.raf.clientapplication.restclient.dto.ReservationCreateDto;
import com.raf.clientapplication.restclient.dto.TrainingCreateDto;
import com.raf.clientapplication.restclient.dto.TrainingDto;
import com.raf.clientapplication.restclient.dto.TrainingTypeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationView extends JPanel {
	private String role = "a";
	private ReservationTableModel reservationTableModel;
	private JTable reservationTable;
	private ReservationServiceRestClient reservationServiceRestClient;
	private JButton book;
	private JButton searchButton;
	private JButton cancelManagerButton;
	private JCheckBox checkBoxGroup;
	private JCheckBox checkBoxIndividual;
	private JTextField weekdayTextField;
	private JComboBox<String> typeComboBox;
	private JComboBox<String> bookTypeComboBox;
	private JButton adminButton;
	private JButton editProfileButton;

	private boolean group = false;
	private boolean individual = false;
	private int weekday = 0;
	private String type;
	private JPanel panel;


	public ReservationView() throws IllegalAccessException, NoSuchMethodException, IOException {
		super();
		this.setSize(1200, 1200);
		reservationTableModel = new ReservationTableModel();
		reservationServiceRestClient = new ReservationServiceRestClient();
		reservationTable = new JTable(reservationTableModel);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(reservationTable);
		this.add(scrollPane, BorderLayout.NORTH);

		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		book = new JButton("Book");
		panel.add(book, BorderLayout.CENTER);
		searchButton = new JButton("Search");
		panel.add(searchButton);
		cancelManagerButton = new JButton("Cancel Manager");

		checkBoxGroup = new JCheckBox("Group");
		panel.add(checkBoxGroup);
		checkBoxIndividual = new JCheckBox("Individual");
		panel.add(checkBoxIndividual);
		weekdayTextField = createPlaceholderTextField("Weekday (1-7)");
		panel.add(weekdayTextField);
		typeComboBox = new JComboBox<>();
		panel.add(typeComboBox);
		bookTypeComboBox = new JComboBox<>();
		adminButton = new JButton("Admin");
		editProfileButton = new JButton("Edit Profile");
		this.add(panel);
		init();
		if(role.equals("ROLE_CLIENT"))
			panel.add(bookTypeComboBox);
		if(!role.equals("ROLE_ADMIN"))
			panel.add(editProfileButton);

		book.addActionListener((event) -> {
			String text;
			TrainingDto trainingDto;
			if(role.equals("ROLE_CLIENT")) {
				 text = (String) bookTypeComboBox.getSelectedItem();
				 trainingDto = reservationTableModel.getTrainingListDtos().getContent().get(reservationTable.getSelectedRow());
				ReservationCreateDto reservationCreateDto;

				reservationCreateDto = new ReservationCreateDto(ClientApplication.getInstance().getId(), trainingDto.getId(),
						!trainingDto.getType().getType().isEmpty() ? trainingDto.getType().getType() : text);
				try {
					reservationServiceRestClient.bookTraining(reservationCreateDto);
					refreshTable(reservationServiceRestClient.getTrainings("/available"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			else if (role.equals("ROLE_MANAGER"))
			{
				text = (String) typeComboBox.getSelectedItem();
				trainingDto = reservationTableModel.getTrainingListDtos().getContent().get(reservationTable.getSelectedRow());
				TrainingCreateDto trainingCreateDto = new TrainingCreateDto(text,
						trainingDto.getStartTime(),trainingDto.getStartDate(),1L);
				try {
					reservationServiceRestClient.addGroupTraining(trainingCreateDto);
					refreshTable(reservationServiceRestClient.getTrainings("/available"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}


		});
		searchButton.addActionListener((event) -> {
			String text = weekdayTextField.getText();
			if(!text.equals("Weekday (1-7)") && text.matches("\\d") && Integer.parseInt(text) > 0 && Integer.parseInt(text) < 8)
				weekday = Integer.parseInt(text);
			else
				weekday = 0;

			group = checkBoxGroup.isSelected();
			individual = checkBoxIndividual.isSelected();
			type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
            try {
                init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
		adminButton.addActionListener((event) -> {
			this.setVisible(false);
			AdminFrame.getInstance().setVisible(true);
			ClientApplication.getInstance().setVisible(false);
		});
		editProfileButton.addActionListener((event) -> {
			this.setVisible(false);
			EditFrame.getInstance().setVisible(true);
			ClientApplication.getInstance().setVisible(false);
		});
		setVisible(false);
	}
	private void refreshTable(List<TrainingDto> trainings)
	{
		reservationTableModel.setNumRows(0);
		reservationTableModel.fireTableDataChanged();
		for (TrainingDto trainingDto : trainings) {
			reservationTableModel.addRow(new Object[]{trainingDto.getId(), trainingDto.isAvailable(), trainingDto.getNumberOfParticipants(),
					trainingDto.getStartDate(), trainingDto.getStartTime(),trainingDto.getType().isGroup(),trainingDto.getType().getPrice(),
					trainingDto.getType().getType(),trainingDto.getStartDate().getDayOfWeek().getValue()});
		}
	}

	public void init() throws IOException {
		this.setVisible(true);
		boolean flag = false;
		typeComboBox.removeAllItems();
		if(!role.equals("a") && role.equals("ROLE_MANAGER"))
		{
			typeComboBox.addItem("Filter/Add training by group type");
			flag = true;
		}

		else if(!role.equals("a"))
		{
			typeComboBox.addItem("Filter by type");
			flag = true;

		}
		bookTypeComboBox.removeAllItems();
		bookTypeComboBox.addItem("Select a type for booking");
		List<TrainingTypeDto> types = reservationServiceRestClient.getGym().getTrainingTypes();
		types.forEach((type) -> {
			if(type.isGroup())
				typeComboBox.addItem(type.getType());
			else
				bookTypeComboBox.addItem(type.getType());
		});


		List<TrainingDto> trainings;
		String path = "";
		if(weekday != 0)
			path += "/w-"+weekday;
		if(group && !individual)
			path += "/g-true";
		else if(!group && individual)
			path += "/g-false";
		if(!Objects.equals(type, "Filter by type") && type != null)
			path += "/t-"+type;
		System.out.println("PATH: "+path);
		System.out.println("weekday: "+weekday);
		System.out.println("group: "+group);
		System.out.println("individual: "+individual);
		System.out.println("type: "+type);

		if(path.isEmpty())
			path = "/available";
		trainings = reservationServiceRestClient.getTrainings(path);
		role = ClientApplication.getInstance().getRole();
		if(!flag) {
			typeComboBox.removeAllItems();
			if (role.equals("ROLE_MANAGER"))
				typeComboBox.addItem("Filter/Add training by group type");
			else
				typeComboBox.addItem("Filter by type");
			bookTypeComboBox.removeAllItems();
			bookTypeComboBox.addItem("Select a type for booking");
			types = reservationServiceRestClient.getGym().getTrainingTypes();
			types.forEach((type) -> {
				if (type.isGroup())
					typeComboBox.addItem(type.getType());
				else
					bookTypeComboBox.addItem(type.getType());
			});
		}
		if(!role.equals("ROLE_CLIENT"))
			panel.add(cancelManagerButton);
		if(role.equals("ROLE_ADMIN"))
			panel.add(adminButton);

//		if(group && !individual)
//			trainings = reservationServiceRestClient.getTrainings("/g-true");
//		else if(!group && individual)
//			trainings = reservationServiceRestClient.getTrainings("/g-false");
//		else
//			trainings = reservationServiceRestClient.getTrainings("/available");


		/*
				super(new String[]{"ID", "AVAILABLE", "NUMBER OF PARTICIPANTS", "START DATE","START_TIME" ,"IS GROUP",
		"PRICE","TYPE","WEEKDAY"}, 0);
		 */
		refreshTable(trainings);

	}



	private static JTextField createPlaceholderTextField(String placeholder) {
		JTextField textField = new JTextField(20);
		textField.setText(placeholder);
		textField.setForeground(Color.GRAY);

		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholder);
					textField.setForeground(Color.GRAY);
				}
			}
		});

		return textField;
	}
}
