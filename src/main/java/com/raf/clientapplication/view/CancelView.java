package com.raf.clientapplication.view;

import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.model.ReservationTableModel;
import com.raf.clientapplication.restclient.ReservationServiceRestClient;
import com.raf.clientapplication.restclient.dto.ReservationCancelDto;
import com.raf.clientapplication.restclient.dto.TrainingDto;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class CancelView extends JPanel {
    private String role;
    private ReservationTableModel reservationTableModel;
    private JTable reservationTable;
    private ReservationServiceRestClient reservationServiceRestClient;
    private JButton cancelButton;
    private JPanel panel;
    private JButton backButton;

    public CancelView() throws IllegalAccessException, NoSuchMethodException, IOException {
        super();
        this.setSize(1200, 1200);
        role = ClientApplication.getInstance().getRole();
        reservationTableModel = new ReservationTableModel();
        reservationServiceRestClient = new ReservationServiceRestClient();
        reservationTable = new JTable(reservationTableModel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(reservationTable);

        cancelButton = new JButton("Cancel");
        backButton = new JButton("Back");

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(cancelButton);
        panel.add(backButton);
        this.add(panel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.NORTH);
        init();
        cancelButton.addActionListener((event)->{
            ReservationCancelDto reservationCancelDto = new ReservationCancelDto();
            reservationCancelDto.setTrainingId(reservationTableModel.getTrainingListDtos().getContent().get(reservationTable.getSelectedRow()).getId());
            if(role.equals("ROLE_CLIENT"))
                reservationCancelDto.setUserId(ClientApplication.getInstance().getId());
            try {
                reservationServiceRestClient.cancelReservation(reservationCancelDto);
                if(role.equals("ROLE_CLIENT"))
                    refreshTable(reservationServiceRestClient.getTrainingsForClient());
                else
                    refreshTable(reservationServiceRestClient.getTrainings("/booked"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.addActionListener((event)->{
            CancelFrame.getInstance().setVisible(false);
            try {
                ReservationFrame.getInstance().setVisible(true);
                ReservationFrame.reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
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
    private void init() throws IOException {
        this.setVisible(true);
        if(role.equals("ROLE_CLIENT"))
            refreshTable(reservationServiceRestClient.getTrainingsForClient());
        else
            refreshTable(reservationServiceRestClient.getTrainings("/booked"));
    }

}
