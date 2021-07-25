package com.ssayed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.Async;

import com.ssayed.service.RandomNumberGameService;

@SpringBootApplication
@EnableRetry
public class RandomNumberGameApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomNumberGameApplication.class);

	@Value("${initiator: false}")
	private Boolean initiator;

	@Autowired
	private RandomNumberGameService randomNumberGameService;

	public static void main(String[] args) {
		SpringApplication.run(RandomNumberGameApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("am I the initiator of the game <{}>", initiator);

		if (initiator) {
			startTheGame();
		}
	}

	@Async
	private void startTheGame() throws Exception {
		int mainNumber = randomNumberGameService.getNumber();
		randomNumberGameService.callOtherPlayer(mainNumber);
	}
}
