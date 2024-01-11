package com.raf.clientapplication.restclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.raf.clientapplication.ClientApplication;

import com.raf.clientapplication.model.User;
import com.raf.clientapplication.restclient.dto.*;
import okhttp3.*;

public class ReservationServiceRestClient {

	public static final String URL = "http://localhost:8081/api";

	public static final MediaType JSON
		= MediaType.get("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();
	ObjectMapper objectMapper = new ObjectMapper();


	public List<TrainingDto> getTrainings(String path) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		int page = 0;
		TrainingListDtos dtos = new TrainingListDtos();
		List<TrainingDto> trainingDtos = new ArrayList<>();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		while(true) {
			Request request = new Request.Builder()
					.url(URL + "/gym/1/trainings" + path + "?page=" + page + "&size=20")
					.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
					.get()
					.build();

			Call call = client.newCall(request);
			ClientApplication.getInstance().setId(getUser(ClientApplication.getInstance().getToken()).getId());
			ClientApplication.getInstance().setRole(getUser(ClientApplication.getInstance().getToken()).getRole());
			Response response = call.execute();
			if (response.code() == 200) {
				String json = response.body().string();

				dtos = objectMapper.readValue(json, TrainingListDtos.class);

				trainingDtos.addAll((dtos.getContent()));
				page++;
				if (dtos.getContent().isEmpty()) {
					break;
				}
			}
			else
				throw new RuntimeException();
		}
		return trainingDtos;
	}
	public GymDto getGym() throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Request request = new Request.Builder()
				.url(URL + "/gym")
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.get()
				.build();

		Call call = client.newCall(request);
		System.out.println(String.format("ID: %s, role: %s",getUser(ClientApplication.getInstance().getToken()).getId(),
				getUser(ClientApplication.getInstance().getToken()).getRole()));

		Response response = call.execute();

		System.out.println(response.code());
		GymDtoList gymDtoList = new GymDtoList();
		if (response.code() >= 200 && response.code() <= 300) {
			String json = response.body().string();
			System.out.println(json);
			gymDtoList = objectMapper.readValue(json, GymDtoList.class);
			return gymDtoList.getContent().get(0);
		}

		throw new RuntimeException();
	}
	public void bookTraining(ReservationCreateDto reservationCreateDto) throws IOException{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(reservationCreateDto));
		Request request = new Request.Builder()
				.url(URL + "/reservation")
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.post(body)
				.build();
		Call call = client.newCall(request);

		Response response = call.execute();

		System.out.println(response.code());
		if (response.code() >= 200 && response.code() <= 300) {
			String json = response.body().string();
		}
		else
			throw new RuntimeException();
	}
	public void addGroupTraining(TrainingCreateDto trainingCreateDto) throws IOException{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(trainingCreateDto));
		Request request = new Request.Builder()
				.url(URL + "/gym")
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.post(body)
				.build();
		Call call = client.newCall(request);

		Response response = call.execute();

		System.out.println(response.code());
		if (response.code() >= 200 && response.code() <= 300) {
            assert response.body() != null;
            String json = response.body().string();
		}
		else
			throw new RuntimeException();
	}
	public void updateGym(GymUpdateDto gymUpdateDto) throws JsonProcessingException {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		System.out.println(gymUpdateDto.toString());
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(gymUpdateDto));
		Request request = new Request.Builder()
				.url(URL + "/gym")
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.put(body)
				.build();
		Call call = client.newCall(request);

		Response response = null;
		try {
			response = call.execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println(response.code());
		if (response.code() >= 200 && response.code() <= 300) {
			String json = null;
			try {
                assert response.body() != null;
                json = response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else
			throw new RuntimeException();
	}
	public void cancelReservation(ReservationCancelDto reservationCancelDto) throws IOException{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(reservationCancelDto));
		Request request = new Request.Builder()
				.url(URL + "/reservation/cancel")
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.post(body)
				.build();
		Call call = client.newCall(request);

		Response response = call.execute();

		System.out.println(response.code());
		if (response.code() >= 200 && response.code() <= 300) {
			String json = response.body().string();
		}
		else
			throw new RuntimeException();
	}

	private User getUser(String token) throws JsonProcessingException {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(token.split("[.]")[1]));
		User userMapper = objectMapper.readValue(payload, User.class);
		return userMapper;
	}

	public List<TrainingDto> getTrainingsForClient() throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		TrainingListDtos dtos = new TrainingListDtos();
		TrainingDto[] trainingDtos = new TrainingDto[0];
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Request request = new Request.Builder()
				.url(URL + "/reservation/user/"+ ClientApplication.getInstance().getId())
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.get()
				.build();

		Call call = client.newCall(request);
		Response response = call.execute();
		if (response.code() == 200) {
			String json = response.body().string();

			trainingDtos = objectMapper.readValue(json, TrainingDto[].class);
			dtos.setContent(List.of(trainingDtos));
			System.out.println(dtos.getContent().size());
		}
		else
			throw new RuntimeException();

		return dtos.getContent();
	}
}
