package com.raf.clientapplication.model;

import com.raf.clientapplication.restclient.dto.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class NotificationTableModel extends DefaultTableModel {


    public NotificationTableModel() throws IllegalAccessException, NoSuchMethodException {
        super(new String[]{"ID", "TYPE", "TEXT", "PARAMETERS", "EMAIL"}, 0);
    }

    private NotificationListDto notificationListDto = new NotificationListDto();

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        NotificationDto dto = new NotificationDto();
        dto.setId(Long.valueOf(row[0].toString()));
        dto.setNotificationTypeDto(new NotificationTypeDto());
        dto.getNotificationTypeDto().setName(String.valueOf(row[1]));
        String text = String.valueOf(row[2]);
        dto.getNotificationTypeDto().setText(String.valueOf(row[2]));
        dto.setParameters(Collections.singletonList(String.valueOf(row[3])));
        dto.setEmail(String.valueOf(row[4]));

        notificationListDto.getNotificationDtos().add(dto);
    }
}