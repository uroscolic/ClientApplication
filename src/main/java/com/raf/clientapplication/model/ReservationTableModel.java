package com.raf.clientapplication.model;

import javax.swing.table.DefaultTableModel;

import com.raf.clientapplication.restclient.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationTableModel extends DefaultTableModel {


	public ReservationTableModel() throws IllegalAccessException, NoSuchMethodException {
		super(new String[]{"ID", "AVAILABLE", "NUMBER OF PARTICIPANTS", "START DATE","START TIME" , "IS GROUP",
		"PRICE","TYPE","WEEKDAY"}, 0);
	}

	private TrainingListDtos trainingListDtos = new TrainingListDtos();

	@Override
	public void addRow(Object[] row) {
		super.addRow(row);
		TrainingDto dto = new TrainingDto();
		dto.setId(Long.valueOf(row[0].toString()));
		dto.setAvailable(Boolean.parseBoolean(String.valueOf(row[1])));
		dto.setNumberOfParticipants(Integer.parseInt(String.valueOf(row[2])));
		dto.setStartDate(LocalDate.parse(String.valueOf(row[3])));
		dto.setStartTime(LocalTime.parse(String.valueOf(row[4])));
		dto.setType(new TrainingTypeDto());
		dto.getType().setGroup(Boolean.parseBoolean(String.valueOf(row[5])));
		Long price = Long.valueOf(row[6] == null ? "0" : row[6].toString());
		dto.getType().setPrice(price);
		dto.getType().setType(String.valueOf(row[7] == null ? "" : row[7]));
		trainingListDtos.getContent().add(dto);
	}


}
