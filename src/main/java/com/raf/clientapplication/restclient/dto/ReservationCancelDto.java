package com.raf.clientapplication.restclient.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCancelDto {

    private Long userId;
    private Long trainingId;
}
