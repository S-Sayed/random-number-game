package com.ssayed.service;

import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.ssayed.model.ApiResponse;

@Service
public class RandomNumberGameService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomNumberGameService.class);
	private static final int NO_OF_MAXIMUM_TRIALS = 5;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${server.port}")
	private Integer serverPort;

	@Value("${isManualTyping: false}")
	private Boolean isManualTyping;

	@Value("${player.no}")
	private Integer playerNumber;

	@Value("${player1.service.instance.port}")
	private Integer player1ServiceInstancePort;

	@Value("${player2.service.instance.port}")
	private Integer player2ServiceInstancePort;

	public int getNumber() {
		LOGGER.info("getNumber - isManualTyping <{}>", isManualTyping);

		int mainNumber = 0;

		if (isManualTyping) {
			try (Scanner scanner = new Scanner(System.in);) {
				while (true) {
					LOGGER.info("Please enter main number > 1 to start the game");
					mainNumber = scanner.nextInt();

					if (mainNumber > 1)
						break;
				}
			} catch (Exception e) {
				LOGGER.error("getNumber - Error occured while entering the main number", e);
			}
		} else {
			mainNumber = new Random().ints(2, 1000).findFirst().getAsInt();
		}

		LOGGER.info("getNumber - main number is <{}>", mainNumber);

		return mainNumber;
	}

	public void callOtherPlayer(int number) throws Exception {
		try {
			int port = playerNumber == 1 ? player2ServiceInstancePort : player1ServiceInstancePort;
			LOGGER.info("callOtherPlayer with number <{}> and  port <{}>", number, port);

			ResponseEntity<ApiResponse> response = restTemplate.exchange(
					"http://localhost:" + port + "/v1/random-number-game/" + number, HttpMethod.GET,
					new HttpEntity(fillHeaders()), ApiResponse.class);

			LOGGER.info("Reponse status <{}>, and body <{}>", response.getStatusCode(), response.getBody());
		} catch (ResourceAccessException e) {
			LOGGER.warn("The other player is not available now, waiting ... ");
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured while calling the other player", e);
			throw new RuntimeException("Something went wrong");
		}
	}

	private HttpHeaders fillHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "*/*");
		return headers;
	}

	public int getNumberToBeAdded(Integer number) {
		double result = number / 3.0;
		int numberToBeAdded = 0;

		if (result % 1 == 0.0)
			numberToBeAdded = 0;
		else if (result % 1 >= 0.5) {
			numberToBeAdded = 1;
		} else {
			numberToBeAdded = -1;
		}

		LOGGER.info("numberToBeAdded <{}>", numberToBeAdded);
		return numberToBeAdded;
	}
}
