package com.raf.clientapplication.restclient.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TrainingListDtos {
    private List<TrainingDto> content = new ArrayList<>();
}
