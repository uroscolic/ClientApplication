package com.raf.clientapplication.restclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String email;
    private NotificationTypeDto notificationTypeDto;
    private List<String> parameters;

    public NotificationDto(NotificationTypeDto notificationTypeDto, List<String> parameters) {
        this.notificationTypeDto = notificationTypeDto;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", notificationTypeDto=" + notificationTypeDto +
                ", parameters=" + parameters +
                '}';
    }
}
