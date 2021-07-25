package com.ssayed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssayed.model.ApiResponse;
import com.ssayed.service.RandomNumberGameService;

@RestController
@RequestMapping("/v1/random-number-game")
public class RandomNumberGameController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomNumberGameController.class);

	@Value("${playerName}")
	private String playerName;

	@Autowired
	private RandomNumberGameService randomNumberGameService;

	@GetMapping(value = "/{number}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ApiResponse play(@PathVariable(name = "number", required = true) Integer receivedNumber) {
		LOGGER.info("Received number <{}>", receivedNumber);

		try {
			if (receivedNumber == 1) {
				LOGGER.info("Player <{}> lost, Good luck next time", playerName);
				return new ApiResponse();
			}

			int numberToBeAdded = randomNumberGameService.getNumberToBeAdded(receivedNumber);
			int sum = receivedNumber + numberToBeAdded;
			LOGGER.info("Sum <{}>", sum);

			int resultingNumber = sum / 3;
			LOGGER.info("resultingNumber after dividing by three <{}>", resultingNumber);

			if (resultingNumber == 1) {
				LOGGER.info("Player <{}> won, Congrats", playerName);
			}

			randomNumberGameService.callOtherPlayer(resultingNumber);
			return new ApiResponse(numberToBeAdded, resultingNumber);
		} catch (Exception e) {
			LOGGER.error("Error occured while playing the game", e);
		}

		return new ApiResponse();
	}
}