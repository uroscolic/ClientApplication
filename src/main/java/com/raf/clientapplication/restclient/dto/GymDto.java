package com.raf.clientapplication.restclient.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class GymDto {
    private Long id;
    private String name;
    private String description;
    private int freeTrainingPerXBooked;
    private int numberOfPersonalTrainers;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Duration trainingDuration;
    private List<TrainingDto> trainings = new ArrayList<>();
    private List<TrainingTypeDto> trainingTypes = new ArrayList<>();


}
