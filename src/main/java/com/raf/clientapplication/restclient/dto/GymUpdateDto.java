package com.raf.clientapplication.restclient.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymUpdateDto {

    private Long id;
    private String description;
    private int numberOfPersonalTrainers;
    private List<TrainingDto> trainings = new ArrayList<>();
    private List<TrainingTypeDto> trainingTypes = new ArrayList<>();
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Duration trainingDuration;
    private int freeTrainingPerXBooked;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrainingTypeDto{
        private String oldType;
        private String type;
        private Long price;
        private boolean isGroup;
    }

    @Override
    public String toString() {
        return "GymUpdateDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", numberOfPersonalTrainers=" + numberOfPersonalTrainers +
                ", trainingTypes=" + trainingTypes +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", trainingDuration=" + trainingDuration +
                ", freeTrainingPerXBooked=" + freeTrainingPerXBooked +
                '}';
    }


}
