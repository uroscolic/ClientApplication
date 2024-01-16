package com.raf.clientapplication.restclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.raf.clientapplication.ClientApplication;
import com.raf.clientapplication.restclient.dto.*;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserServiceRestClient {

	public static final String URL = "http://localhost:8084/user-service/api";

	//TODO: change this to this thing upstairs
	//public static final String URL = "http://localhost:8080/api";

	public static final MediaType JSON
		= MediaType.get("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();
	ObjectMapper objectMapper = new ObjectMapper();


	public String login(String email, String password) throws IOException {

		TokenRequestDto tokenRequestDto = new TokenRequestDto(email, password);
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(tokenRequestDto));

		Request request = new Request.Builder()
			.url(URL + "/login")
			.post(body)
			.build();

		Call call = client.newCall(request);

		Response response = call.execute();
		System.out.println(response.code());
		if (response.code() >= 200 && response.code() <= 300) {
			String json = response.body().string();
			TokenResponseDto dto = objectMapper.readValue(json, TokenResponseDto.class);

			return dto.getToken();
		}

		throw new RuntimeException("Invalid email or password");
	}
	public void registerClient(ClientCreateDto clientCreateDto) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(clientCreateDto));
		System.out.println(objectMapper.writeValueAsString(clientCreateDto));
		Request request = new Request.Builder()
				.url(URL + "/client/register")
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
	public void registerManager(ManagerCreateDto managerCreateDto) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(managerCreateDto));
		System.out.println(objectMapper.writeValueAsString(managerCreateDto));
		Request request = new Request.Builder()
				.url(URL + "/manager/register")
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
	public List<UserDto> getUsers() throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		int page = 0;
		UserListDtos dtos = new UserListDtos();
		List<UserDto> userDtos = new ArrayList<>();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		while(true) {
			Request request = new Request.Builder()
					.url(URL + "/admin/all-users?page=" + page + "&size=20")
					.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
					.get()
					.build();

			Call call = client.newCall(request);
			Response response = call.execute();
			if (response.code() == 200) {
				String json = response.body().string();

				dtos = objectMapper.readValue(json, UserListDtos.class);

				userDtos.addAll((dtos.getContent()));
				page++;
				if (dtos.getContent().isEmpty()) {
					break;
				}
			}
			else
				throw new RuntimeException();
		}
		return userDtos;
	}
	public void banUser(UserBanDto userBanDto, String role) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(userBanDto));
		System.out.println(objectMapper.writeValueAsString(userBanDto));
		Request request = new Request.Builder()
				.url(URL + "/" + role + "/ban")
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
	public UserDto getUserById(Long id, String role) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		System.out.println("ID: "+id);
		System.out.println("ROLE: "+role);
		Request request = new Request.Builder()
				.url(URL + "/" + role + "/" + id)
				.header("Authorization", "Bearer " + ClientApplication.getInstance().getToken())
				.get()
				.build();

		Call call = client.newCall(request);
		Response response = call.execute();
		System.out.println(response.code());
		if (response.code() == 200) {
			String json = response.body().string();
			return objectMapper.readValue(json, UserDto.class);
		}
		else
			throw new RuntimeException();

	}
	public void updateUser(UserUpdateDto userUpdateDto, String role) throws IOException {
		objectMapper.registerModule(new JavaTimeModule());
		RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(userUpdateDto));
		System.out.println(objectMapper.writeValueAsString(userUpdateDto));
		Request request = new Request.Builder()
				.url(URL + "/" + role + "/modify")
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


}
