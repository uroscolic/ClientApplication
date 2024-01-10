package com.raf.clientapplication.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTypeDto {
    private String type;
    private Long price;
    private boolean isGroup;
}
