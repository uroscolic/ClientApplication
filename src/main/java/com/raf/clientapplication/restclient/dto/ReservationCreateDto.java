package com.raf.clientapplication.restclient.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateDto {

    private Long userId;
    private Long trainingId;
    private String trainingType;
}
