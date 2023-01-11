package com.dolearci.javacodechallenges.LinkedInLevelUpJava;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import lombok.Getter;
import lombok.Setter;

public class JokeGenerator {

	public static void main(String[] args) throws UnsupportedEncodingException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://icanhazdadjoke.com/"))
				.header("accept", "application/json")
				.build();

		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		assert response != null;
		JokeResponse joke = new Gson().fromJson(response.body(), JokeResponse.class);
		System.out.println(joke.getJoke());
	}

	@Getter
	@Setter
	public static class JokeResponse {
		private String id;
		private String joke;
		private int status;
	}
}
