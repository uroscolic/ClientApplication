package com.raf.clientapplication.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.restclient.dto.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceRestClient {

    //public static final String URL = "http://localhost:8082/api";
      public static final String URL = "http://localhost:8084/notification-service/api";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public List<NotificationDto> getNotifications(String path) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        int page = 0;
        NotificationListDto dtos = new NotificationListDto();
        List<NotificationDto> notificationDtos = new ArrayList<>();
        NotificationDto[] niz = new NotificationDto[100];
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        while(true) {
            Request request = new Request.Builder()
                    .url(URL + "/notification?page=" + page + "&size=20")
                    .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                    .get()
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                String json = response.body().string();
                //System.out.println(json);
                niz = objectMapper.readValue(json, NotificationDto[].class);
                //System.out.println(niz.length);
                //System.out.println(niz[0]);
                //System.out.println(dtos.getNotificationDtos().size());
                //notificationDtos.addAll((dtos.getNotificationDtos()));
                //System.out.println(notificationDtos.get(0));
                //System.out.println(notificationDtos.get(0).getNotificationTypeDto().getName());
                dtos.setNotificationDtos(List.of(niz));
                /*for(NotificationDto notificationDto : dtos.getNotificationDtos()){
                    System.out.println(notificationDto.getNotificationTypeDto().getName());
                }*/
                notificationDtos.addAll((dtos.getNotificationDtos()));
                page++;
                if (dtos.getNotificationDtos().isEmpty()) {
                    break;
                }
            }
            else
                throw new RuntimeException();
        }
        return notificationDtos;
    }
    public List<NotificationTypeDto> getNotificationTypes() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        int page = 0;
        NotificationTypeListDto dtos = new NotificationTypeListDto();
        List<NotificationTypeDto> notificationTypeDtos = new ArrayList<>();
        NotificationTypeDto[] niz = new NotificationTypeDto[100];
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        while(true) {
            Request request = new Request.Builder()
                    .url(URL + "/notification/type?page=" + page + "&size=20")
                    .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                    .get()
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                String json = response.body().string();

                niz = objectMapper.readValue(json, NotificationTypeDto[].class);
                dtos.setNotificationTypeDtos(List.of(niz));
                notificationTypeDtos.addAll((dtos.getNotificationTypeDtos()));
                page++;
                if (dtos.getNotificationTypeDtos().isEmpty()) {
                    break;
                }
            }
            else
                throw new RuntimeException();
        }

        return notificationTypeDtos;
    }

    public void updateNotificationType(NotificationTypeUpdateDto notificationTypeUpdateDto) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(notificationTypeUpdateDto));
        System.out.println(objectMapper.writeValueAsString(notificationTypeUpdateDto));
        Request request = new Request.Builder()
                .url(URL + "/notification/type")
                .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                .put(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();
        System.out.println(response.code());
        if (response.code() >= 200 && response.code() < 300) {
            String json = response.body().string();

        }
        else
            throw new RuntimeException("Something went wrong");
    }

    public void addNotificationType(NotificationTypeDto notificationTypeDto) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(notificationTypeDto));
        System.out.println(objectMapper.writeValueAsString(notificationTypeDto));
        Request request = new Request.Builder()
                .url(URL + "/notification/type")
                .header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();
        System.out.println(response.code());
        if (response.code() >= 200 && response.code() < 300) {
            String json = response.body().string();

        }
        else
            throw new RuntimeException("Something went wrong");
    }
}
