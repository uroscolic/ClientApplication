package com.raf.clientapplication.model;

import com.raf.clientapplication.restclient.dto.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.DefaultTableModel;
@Getter
@Setter
public class UserTableModel extends DefaultTableModel {


    public UserTableModel() throws IllegalAccessException, NoSuchMethodException {
        super(new String[]{"ID","EMAIL", "FIRST NAME", "LAST NAME","USERNAME","ROLE","BANNED"}, 0);
    }

    private UserListDtos userListDtos = new UserListDtos();

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        UserDto dto = new UserDto();
        dto.setId(Long.valueOf(row[0].toString()));
        dto.setEmail(String.valueOf(row[1]));
        dto.setFirstName(String.valueOf(row[2]));
        dto.setLastName(String.valueOf(row[3]));
        dto.setUsername(String.valueOf(row[4]));
        dto.setRole(String.valueOf(row[5]));
        dto.setBanned(Boolean.parseBoolean(row[6].toString()));
        userListDtos.getContent().add(dto);
    }


}