package com.shahs.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionsApplication {

	public static final String UPLOAD_DIR = "/Users/Darshan/work/trading-project/trade-data-splitter/receivedFiles/";

	public static void main(String[] args) {
		SpringApplication.run(TransactionsApplication.class, args);
	}

}
