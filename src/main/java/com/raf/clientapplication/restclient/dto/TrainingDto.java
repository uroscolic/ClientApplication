package com.raf.clientapplication.restclient.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {

	private Long id;
	private TrainingTypeDto type;
	private int numberOfParticipants;
	private boolean available;
	private LocalTime startTime;
	private LocalDate startDate;


	

}
