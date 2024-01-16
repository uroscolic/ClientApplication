package com.raf.clientapplication.model;

import com.raf.clientapplication.restclient.dto.NotificationDto;
import com.raf.clientapplication.restclient.dto.NotificationListDto;
import com.raf.clientapplication.restclient.dto.NotificationTypeDto;
import com.raf.clientapplication.restclient.dto.NotificationTypeListDto;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;

public class NotificationTypeTableModel extends DefaultTableModel {


    public NotificationTypeTableModel() throws IllegalAccessException, NoSuchMethodException {
        super(new String[]{"ID", "TEXT", "NAME"}, 0);
    }

    private NotificationTypeListDto notificationTypeListDto = new NotificationTypeListDto();

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        NotificationTypeDto dto = new NotificationTypeDto();
        dto.setId(Long.valueOf(row[0].toString()));
        dto.setText(String.valueOf(row[1]));
        dto.setName(String.valueOf(row[2]));

        notificationTypeListDto.getNotificationTypeDtos().add(dto);
    }
}